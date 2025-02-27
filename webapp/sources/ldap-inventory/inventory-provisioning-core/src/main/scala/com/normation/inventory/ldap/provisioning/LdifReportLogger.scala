/*
 *************************************************************************************
 * Copyright 2011 Normation SAS
 *************************************************************************************
 *
 * This file is part of Rudder.
 *
 * Rudder is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In accordance with the terms of section 7 (7. Additional Terms.) of
 * the GNU General Public License version 3, the copyright holders add
 * the following Additional permissions:
 * Notwithstanding to the terms of section 5 (5. Conveying Modified Source
 * Versions) and 6 (6. Conveying Non-Source Forms.) of the GNU General
 * Public License version 3, when you create a Related Module, this
 * Related Module is not considered as a part of the work and may be
 * distributed under the license agreement of your choice.
 * A "Related Module" means a set of sources files including their
 * documentation that, without modification of the Source Code, enables
 * supplementary functions or services in addition to those offered by
 * the Software.
 *
 * Rudder is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Rudder.  If not, see <http://www.gnu.org/licenses/>.

 *
 *************************************************************************************
 */

package com.normation.inventory.ldap.provisioning

import com.normation.NamedZioLogger
import com.unboundid.ldap.sdk.Modification
import com.unboundid.ldap.sdk.ModificationType.REPLACE
import com.unboundid.ldif._
import org.joda.time.DateTime
import zio._
/*
 * Log given LDIF record in a file
 * with given name (a timestamp will be added)
 * File will be stored under a configured directory
 */
trait LDIFInventoryLogger extends Any {

  /**
   *
   * @param inventoryName
   *  a name from witch the log id will be derived
   * @param comments
   *  an optional comment to append at the top of the file
   * @param tag
   *  an optional tag to put in the file name, like REPORT, MODIFICATION, etc
   * @param LDIFRecords
   *  list of records to log
   * @return the generated id / path for the log
   */
  def log(
      inventoryName: String,
      comments:      Option[String],
      tag:           Option[String],
      LDIFRecords:   => Seq[LDIFRecord]
  ): Task[String]
}

object DefaultLDIFInventoryLogger {
  val logger        = NamedZioLogger("trace.ldif.in.file")
  val defaultLogDir = java.lang.System.getProperty("java.io.tmpdir") +
    java.lang.System.getProperty("file.separator") + "LDIFLogReport"
}

import com.normation.inventory.ldap.provisioning.DefaultLDIFInventoryLogger.logger
import java.io.File

class DefaultLDIFInventoryLogger(val LDIFLogDir: String = DefaultLDIFInventoryLogger.defaultLogDir) extends LDIFInventoryLogger {

  def rootDir = {
    val dir = new File(LDIFLogDir)
    if (!dir.exists()) dir.mkdirs
    dir
  }

  protected def fileFromName(name: String, opType: Option[String]): File = {
    val fileName = name.replaceAll(File.separator, "|")

    // time stamp are not that much readable, use a YYYYMMDD-HH.MM.SSS format
    new File(
      rootDir,
      fileName +
      "_" + DateTime.now().toString("YYYY-MM-dd_HH.mm.ss.SS") +
      (opType.map("_" + _).getOrElse("")) +
      ".LDIF"
    )
  }

  def log(
      inventoryName: String,
      comments:      Option[String],
      tag:           Option[String],
      LDIFRecords:   => Seq[LDIFRecord]
  ): Task[String] = {
    val LDIFFile = fileFromName(inventoryName, tag)
    ZIO.when(logger.logEffect.isTraceEnabled) {
      ZIO.acquireReleaseWith(ZIO.attempt(new LDIFWriter(LDIFFile)))(writer =>
        ZIO.attempt(writer.close).catchAll(ex => logger.debug("LDIF log for inventory processing: " + LDIFFile.getAbsolutePath))
      ) { writer =>
        ZIO.attempt {
          val ldif = LDIFRecords // that's important, else we evaluate again and again LDIFRecords

          if (ldif.nonEmpty) { // don't check it if logger trace is not enabled
            writer.writeLDIFRecord(ldif.head, comments.getOrElse(null))

            ldif.tail.foreach(LDIFRecord => writer.writeLDIFRecord(LDIFRecord))
          } else {
            // write a dummy record
            val c = comments.getOrElse("") + "(There was no record to log, a dummy modification is added in log as a placeholder)"
            writer.writeLDIFRecord(new LDIFModifyChangeRecord("cn=dummy", new Modification(REPLACE, "dummy", "dummy")), c)
          }
        }
      }
    } *> ZIO.succeed(LDIFFile.getAbsolutePath)
  }
}
