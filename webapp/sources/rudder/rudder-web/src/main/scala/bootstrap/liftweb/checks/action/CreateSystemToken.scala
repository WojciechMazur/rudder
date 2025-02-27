/*
 *************************************************************************************
 * Copyright 2017 Normation SAS
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

package bootstrap.liftweb.checks.action

import bootstrap.liftweb.BootstrapChecks
import bootstrap.liftweb.BootstrapLogger
import com.normation.rudder.api.ApiAccount
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.PosixFilePermissions
import net.liftweb.common.EmptyBox
import net.liftweb.common.Full
import net.liftweb.util.ControlHelpers.tryo

/**
 * Create at webapp startup an api token to use for intern use
 */
class CreateSystemToken(systemAccount: ApiAccount) extends BootstrapChecks {

  override val description = "Create system api token"

  override def checks(): Unit = {
    val tokenPath = "/var/rudder/run/api-token"

    (for {
      path <- tryo {
                Paths.get(tokenPath)
              } ?~! "An error occured while getting system api token path"

      file <- tryo {
                Files.deleteIfExists(path)
                Files.createFile(path)
                Files.write(path, systemAccount.token.value.getBytes(StandardCharsets.UTF_8))
              } ?~! "An error occured while creating system api token file"

      perms <- tryo {
                 Files.setPosixFilePermissions(path, PosixFilePermissions.fromString("rw-------"))
               } ?~! "An error occured while setting permissions on system api token file"
    } yield {}) match {
      case Full(_) =>
        BootstrapLogger.logEffect.info(s"System api token file created in ${tokenPath}")
      case eb: EmptyBox =>
        val fail = eb ?~! s"An error occured while creating system api token file in ${tokenPath}"
        BootstrapLogger.logEffect.error(fail.messageChain)
    }

  }

}
