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

package com.normation.rudder.domain.eventlog

import com.normation.eventlog._

///// Define intersting categories /////
final case object UserLogCategory                extends EventLogCategory
final case object APIAccountCategory             extends EventLogCategory
final case object RudderApplicationLogCategory   extends EventLogCategory
final case object RuleLogCategory                extends EventLogCategory
final case object DirectiveLogCategory           extends EventLogCategory
final case object TechniqueLogCategory           extends EventLogCategory
final case object DeploymentLogCategory          extends EventLogCategory
final case object NodeGroupLogCategory           extends EventLogCategory
final case object AssetLogCategory               extends EventLogCategory
final case object RedButtonLogCategory           extends EventLogCategory
final case object ChangeRequestLogCategory       extends EventLogCategory
final case object SecretLogCategory              extends EventLogCategory
final case object WorkflowLogCategory            extends EventLogCategory
final case object PolicyServerLogCategory        extends EventLogCategory
final case object ImportExportItemsLogCategory   extends EventLogCategory
final case object ParameterLogCategory           extends EventLogCategory
final case object GlobalPropertyEventLogCategory extends EventLogCategory
final case object SettingsLogCategory            extends EventLogCategory
final case object NodeLogCategory                extends EventLogCategory

// the promises related event type
final case object AutomaticStartDeployementEventType extends NoRollbackEventLogType {
  def serialize = "AutomaticStartDeployement"
}
final case object ManualStartDeployementEventType    extends NoRollbackEventLogType {
  def serialize = "ManualStartDeployement"
}
final case object SuccessfulDeploymentEventType      extends NoRollbackEventLogType {
  def serialize = "SuccessfulDeployment"
}
final case object FailedDeploymentEventType          extends NoRollbackEventLogType {
  def serialize = "FailedDeployment"
}
// the login related event type
final case object LoginEventType                     extends NoRollbackEventLogType {
  def serialize = "UserLogin"
}
final case object BadCredentialsEventType            extends NoRollbackEventLogType {
  def serialize = "BadCredentials"
}
final case object LogoutEventType                    extends NoRollbackEventLogType {
  def serialize = "UserLogout"
}
final case object CreateAPIAccountEventType          extends NoRollbackEventLogType {
  def serialize = "CreateAPIAccount"
}
final case object DeleteAPIAccountEventType          extends NoRollbackEventLogType {
  def serialize = "DeleteAPIAccount"
}
final case object ModifyAPITokenEventType            extends NoRollbackEventLogType {
  def serialize = "ModifyAPIAccount"
}
// the node related event type
final case object AddNodeGroupEventType              extends RollbackEventLogType   {
  def serialize = "NodeGroupAdded"
}
final case object DeleteNodeGroupEventType           extends RollbackEventLogType   {
  def serialize = "NodeGroupDeleted"
}
final case object ModifyNodeGroupEventType           extends RollbackEventLogType   {
  def serialize = "NodeGroupModified"
}

// change request related
final case object AddChangeRequestEventType    extends NoRollbackEventLogType {
  def serialize = "ChangeRequestAdded"
}
final case object DeleteChangeRequestEventType extends NoRollbackEventLogType {
  def serialize = "ChangeRequestDeleted"
}
final case object ModifyChangeRequestEventType extends NoRollbackEventLogType {
  def serialize = "ChangeRequestModified"
}

// secret related
final case object AddSecretEventType extends NoRollbackEventLogType {
  def serialize = "SecretAdded"
}

final case object ModifySecretEventType extends NoRollbackEventLogType {
  def serialize = "SecretModified"
}

final case object DeleteSecretEventType extends NoRollbackEventLogType {
  def serialize = "SecretDeleted"
}

// directive related
final case object AddDirectiveEventType    extends RollbackEventLogType {
  def serialize = "DirectiveAdded"
}
final case object DeleteDirectiveEventType extends RollbackEventLogType {
  def serialize = "DirectiveDeleted"
}
final case object ModifyDirectiveEventType extends RollbackEventLogType {
  def serialize = "DirectiveModified"
}

// the generic event related event type
final case object ApplicationStartedEventType extends NoRollbackEventLogType {
  def serialize = "ApplicationStarted"
}
final case object ActivateRedButtonEventType  extends NoRollbackEventLogType {
  def serialize = "ActivateRedButton"
}
final case object ReleaseRedButtonEventType   extends NoRollbackEventLogType {
  def serialize = "ReleaseRedButton"
}

final case object ReloadTechniqueLibraryType extends NoRollbackEventLogType {
  def serialize = "ReloadTechniqueLibrary"
}

final case object AddTechniqueEventType extends NoRollbackEventLogType {
  def serialize = "TechniqueAdded"
}

final case object ModifyTechniqueEventType extends NoRollbackEventLogType {
  def serialize = "TechniqueModified"
}

final case object DeleteTechniqueEventType extends NoRollbackEventLogType {
  def serialize = "TechniqueDeleted"
}

// rule related event type
final case object AddRuleEventType    extends RollbackEventLogType   {
  def serialize = "RuleAdded"
}
final case object DeleteRuleEventType extends RollbackEventLogType   {
  def serialize = "RuleDeleted"
}
final case object ModifyRuleEventType extends RollbackEventLogType   {
  def serialize = "RuleModified"
}
// asset related event type
final case object AcceptNodeEventType extends NoRollbackEventLogType {
  def serialize = "AcceptNode"
}
final case object RefuseNodeEventType extends NoRollbackEventLogType {
  def serialize = "RefuseNode"
}
final case object DeleteNodeEventType extends NoRollbackEventLogType {
  def serialize = "DeleteNode"
}

// the system event type
final case object ClearCacheEventType         extends NoRollbackEventLogType {
  def serialize = "ClearCache"
}
final case object UpdatePolicyServerEventType extends NoRollbackEventLogType {
  def serialize = "UpdatePolicyServer"
}

// Import/export
final case object ExportGroupsEventType           extends NoRollbackEventLogType {
  def serialize = "ExportGroups"
}
final case object ImportGroupsEventType           extends RollbackEventLogType   {
  def serialize = "ImportGroups"
}
final case object ExportTechniqueLibraryEventType extends NoRollbackEventLogType {
  def serialize = "ExportTechniqueLibrary"
}
final case object ImportTechniqueLibraryEventType extends RollbackEventLogType   {
  def serialize = "ImportTechniqueLibrary"
}
final case object ExportRulesEventType            extends NoRollbackEventLogType {
  def serialize = "ExportRules"
}
final case object ImportRulesEventType            extends RollbackEventLogType   {
  def serialize = "ImportRules"
}
final case object ExportParametersEventType       extends NoRollbackEventLogType {
  def serialize = "ExportParameters"
}
final case object ImportParametersEventType       extends RollbackEventLogType   {
  def serialize = "ImportParameters"
}
final case object ExportFullArchiveEventType      extends NoRollbackEventLogType {
  def serialize = "ExportFullArchive"
}
final case object ImportFullArchiveEventType      extends RollbackEventLogType   {
  def serialize = "ImportFullArchive"
}
final case object RollbackEventType               extends RollbackEventLogType   {
  def serialize = "Rollback"
}
final case object WorkflowStepChangedEventType    extends NoRollbackEventLogType {
  def serialize = "WorkflowStepChanged"
}

// Parameter event type
final case object AddGlobalParameterEventType extends RollbackEventLogType {
  def serialize = "GlobalParameterAdded"
}

final case object DeleteGlobalParameterEventType extends RollbackEventLogType {
  def serialize = "GlobalParameterDeleted"
}

final case object ModifyGlobalParameterEventType extends RollbackEventLogType {
  def serialize = "GlobalParameterModified"
}

// node: only modify for now
final case object ModifyNodeEventType extends RollbackEventLogType {
  def serialize = "NodeModified"

  // for node, we have more "is defined at", because till 3.2,
  // we had several node events that we merged together in 4.0
  override def isDefinedAt(x: String): Boolean = {
    "NodeHeartbeatModified" == x || "NodePropertiesModified" == x ||
    "NodeAgentRunPeriodModified" == x || "NodeModified" == x
  }
}

final case object PromoteNodeToRelayEventType extends NoRollbackEventLogType {
  def serialize = "NodePromotedToRelay"
}

final case object DemoteRelayToNodeEventType extends NoRollbackEventLogType {
  def serialize = "RelayDemotedToNode"
}

sealed trait ModifyGlobalPropertyEventType extends NoRollbackEventLogType {
  def propertyName: String
}

final case object ModifySendServerMetricsEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "SendServerMetricsModified"
  val propertyName = "Send metrics"
}

final case object ModifyComplianceModeEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "ComplianceModeModified"
  val propertyName = "Compliance mode"
}

final case object ModifyHeartbeatPeriodEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "HeartbeatPeriodModified"
  val propertyName = "Heartbeat period"
}

final case object ModifyAgentRunIntervalEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "AgentRunIntervalModified"
  val propertyName = "Agent run interval"
}

final case object ModifyAgentRunSplaytimeEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "AgentRunSplaytimeModified"
  val propertyName = "Agent run splaytime"
}

final case object ModifyAgentRunStartHourEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "AgentRunStartHourModified"
  val propertyName = "Agent run start hour"
}

final case object ModifyAgentRunStartMinuteEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "AgentRunStartMinuteModified"
  val propertyName = "Agent run start minute"
}

final case object ModifyRudderSyslogProtocolEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "RudderSyslogProtocolModified"
  val propertyName = "Rudder syslog protocol"
}

final case object ModifyPolicyModeEventType extends ModifyGlobalPropertyEventType {
  def serialize    = "PolicyModeModified"
  val propertyName = "Global policy mode"
}

final case object ModifyRudderVerifyCertificates extends ModifyGlobalPropertyEventType {
  def serialize    = "RudderVerifyCertificates"
  val propertyName = "Verification of HTTPS certificates"
}

/**
 * List of event generating a modification of promises
 */
object ModificationWatchList {
  val events = Seq[EventLogType](
    AcceptNodeEventType,
    DeleteNodeEventType,
    AddRuleEventType,
    DeleteRuleEventType,
    ModifyRuleEventType,
    AddDirectiveEventType,
    DeleteDirectiveEventType,
    ModifyDirectiveEventType,
    AddNodeGroupEventType,
    DeleteNodeGroupEventType,
    ModifyNodeGroupEventType,
    ClearCacheEventType,
    UpdatePolicyServerEventType,
    ReloadTechniqueLibraryType,
    ModifyTechniqueEventType,
    DeleteTechniqueEventType,
    ImportGroupsEventType,
    ImportTechniqueLibraryEventType,
    ImportRulesEventType,
    ImportParametersEventType,
    ImportFullArchiveEventType,
    RollbackEventType,
    AddGlobalParameterEventType,
    DeleteGlobalParameterEventType,
    ModifyGlobalParameterEventType,
    ModifyNodeEventType,
    PromoteNodeToRelayEventType,
    DemoteRelayToNodeEventType
  ) ++ ModifyGlobalPropertyEventLogsFilter.eventTypes

}

object EventTypeFactory {
  val eventTypes = List[EventLogType](
    AutomaticStartDeployementEventType,
    ManualStartDeployementEventType,
    SuccessfulDeploymentEventType,
    FailedDeploymentEventType,
    LoginEventType,
    BadCredentialsEventType,
    LogoutEventType,
    CreateAPIAccountEventType,
    DeleteAPIAccountEventType,
    ModifyAPITokenEventType,
    AddNodeGroupEventType,
    DeleteNodeGroupEventType,
    ModifyNodeGroupEventType,
    AddDirectiveEventType,
    DeleteDirectiveEventType,
    ModifyDirectiveEventType,
    ApplicationStartedEventType,
    ActivateRedButtonEventType,
    ReleaseRedButtonEventType,
    ReloadTechniqueLibraryType,
    ModifyTechniqueEventType,
    DeleteTechniqueEventType,
    AddRuleEventType,
    DeleteRuleEventType,
    ModifyRuleEventType,
    AcceptNodeEventType,
    RefuseNodeEventType,
    DeleteNodeEventType,
    ClearCacheEventType,
    UpdatePolicyServerEventType,
    ExportGroupsEventType,
    ImportGroupsEventType,
    ExportTechniqueLibraryEventType,
    ImportTechniqueLibraryEventType,
    ExportRulesEventType,
    ImportRulesEventType,
    ExportParametersEventType,
    ImportParametersEventType,
    ExportFullArchiveEventType,
    ImportFullArchiveEventType,
    RollbackEventType,
    AddChangeRequestEventType,
    ModifyChangeRequestEventType,
    WorkflowStepChangedEventType,
    AddGlobalParameterEventType,
    DeleteGlobalParameterEventType,
    ModifyGlobalParameterEventType,
    ModifyNodeEventType,
    PromoteNodeToRelayEventType,
    DemoteRelayToNodeEventType,
    AddSecretEventType,
    ModifySecretEventType,
    DeleteSecretEventType
  ) ::: ModifyGlobalPropertyEventLogsFilter.eventTypes

  def apply(s: String): EventLogType = {
    eventTypes.find(pf => pf.isDefinedAt(s)).getOrElse(UnknownEventLogType)

  }
}
