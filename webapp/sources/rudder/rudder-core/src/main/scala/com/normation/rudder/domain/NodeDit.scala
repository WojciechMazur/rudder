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

package com.normation.rudder.domain

import RudderLDAPConstants._
import com.normation.inventory.domain._
import com.normation.inventory.ldap.core._
import com.normation.inventory.ldap.core.LDAPConstants._
import com.normation.ldap.sdk._
import com.unboundid.ldap.sdk._

class NodeDit(val BASE_DN: DN) extends AbstractDit {
  dit =>
  implicit val DIT = dit

  dit.register(NODES.model)

  object NODES extends OU("Nodes", BASE_DN) {
    nodes =>
    object NODE extends ENTRY1(A_NODE_UUID) {
      node =>

      // get id from dn
      def idFromDn(dn: DN): Option[NodeId] = buildId(dn, nodes.dn, { x: String => NodeId(x) })

      // build the dn from an UUID
      def dn(uuid: String) = new DN(this.rdn(uuid), nodes.dn)

      def nodeModel(uuid: NodeId): LDAPEntry = {
        val mod = LDAPEntry(this.dn(uuid.value))
        mod.resetValuesTo(A_OC, OC.objectClassNames(OC_RUDDER_NODE).toSeq: _*)
        mod
      }

      def policyServerNodeModel(id: NodeId): LDAPEntry = {
        val mod = LDAPEntry(this.dn(id.value))
        mod.resetValuesTo(A_OC, OC.objectClassNames(OC_POLICY_SERVER_NODE).toSeq: _*)
        mod
      }
    }
  }
}
