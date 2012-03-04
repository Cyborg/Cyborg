/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of Cyborg
 *
 * Cyborg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cyborg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alta189.cyborg;

import com.alta189.cyborg.api.event.channel.UserJoinEvent;
import com.alta189.cyborg.api.event.channel.UserPartEvent;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.ChannelInfoEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.FileTransferFinishedEvent;
import org.pircbotx.hooks.events.FingerEvent;
import org.pircbotx.hooks.events.HalfOpEvent;
import org.pircbotx.hooks.events.IncomingChatRequestEvent;
import org.pircbotx.hooks.events.IncomingFileTransferEvent;
import org.pircbotx.hooks.events.InviteEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.ModeEvent;
import org.pircbotx.hooks.events.MotdEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.OpEvent;
import org.pircbotx.hooks.events.OwnerEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.PingEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.pircbotx.hooks.events.RemoveChannelBanEvent;
import org.pircbotx.hooks.events.RemoveChannelKeyEvent;
import org.pircbotx.hooks.events.RemoveChannelLimitEvent;
import org.pircbotx.hooks.events.RemoveInviteOnlyEvent;
import org.pircbotx.hooks.events.RemoveModeratedEvent;
import org.pircbotx.hooks.events.RemoveNoExternalMessagesEvent;
import org.pircbotx.hooks.events.RemovePrivateEvent;
import org.pircbotx.hooks.events.RemoveSecretEvent;
import org.pircbotx.hooks.events.RemoveTopicProtectionEvent;
import org.pircbotx.hooks.events.ServerPingEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;
import org.pircbotx.hooks.events.SetChannelBanEvent;
import org.pircbotx.hooks.events.SetChannelKeyEvent;
import org.pircbotx.hooks.events.SetChannelLimitEvent;
import org.pircbotx.hooks.events.SetInviteOnlyEvent;
import org.pircbotx.hooks.events.SetModeratedEvent;
import org.pircbotx.hooks.events.SetNoExternalMessagesEvent;
import org.pircbotx.hooks.events.SetPrivateEvent;
import org.pircbotx.hooks.events.SetSecretEvent;
import org.pircbotx.hooks.events.SetTopicProtectionEvent;
import org.pircbotx.hooks.events.SuperOpEvent;
import org.pircbotx.hooks.events.TimeEvent;
import org.pircbotx.hooks.events.TopicEvent;
import org.pircbotx.hooks.events.UnknownEvent;
import org.pircbotx.hooks.events.UserListEvent;
import org.pircbotx.hooks.events.UserModeEvent;
import org.pircbotx.hooks.events.VersionEvent;
import org.pircbotx.hooks.events.VoiceEvent;

public class PircBotXListener extends ListenerAdapter {

	@Override
	public void onAction(ActionEvent actionEvent) throws Exception {
		Cyborg.getInstance().getEventManager().callEvent(new com.alta189.cyborg.api.event.channel.ActionEvent(actionEvent));
	}

	@Override
	public void onChannelInfo(ChannelInfoEvent channelInfoEvent) throws Exception {
		Cyborg.getInstance().getEventManager().callEvent(new com.alta189.cyborg.api.event.channel.ChannelInfoEvent(channelInfoEvent));
	}

	@Override
	public void onConnect(ConnectEvent connectEvent) throws Exception {
		Cyborg.getInstance().getEventManager().callEvent(new com.alta189.cyborg.api.event.bot.ConnectEvent(connectEvent));
	}

	@Override
	public void onDisconnect(DisconnectEvent disconnectEvent) throws Exception {
		super.onDisconnect(disconnectEvent);
	}

	@Override
	public void onFileTransferFinished(FileTransferFinishedEvent fileTransferFinishedEvent) throws Exception {
		super.onFileTransferFinished(fileTransferFinishedEvent);
	}

	@Override
	public void onFinger(FingerEvent fingerEvent) throws Exception {
		super.onFinger(fingerEvent);
	}

	@Override
	public void onHalfOp(HalfOpEvent halfOpEvent) throws Exception {
		super.onHalfOp(halfOpEvent);
	}

	@Override
	public void onIncomingChatRequest(IncomingChatRequestEvent incomingChatRequestEvent) throws Exception {
		super.onIncomingChatRequest(incomingChatRequestEvent);
	}

	@Override
	public void onIncomingFileTransfer(IncomingFileTransferEvent incomingFileTransferEvent) throws Exception {
		super.onIncomingFileTransfer(incomingFileTransferEvent);
	}

	@Override
	public void onInvite(InviteEvent inviteEvent) throws Exception {
		super.onInvite(inviteEvent);
	}

	@Override
	public void onJoin(JoinEvent joinEvent) throws Exception {
		Cyborg.getInstance().getEventManager().callEvent(new UserJoinEvent(joinEvent));
	}

	@Override
	public void onKick(KickEvent kickEvent) throws Exception {
		super.onKick(kickEvent);
	}

	@Override
	public void onMessage(MessageEvent messageEvent) throws Exception {
		Cyborg.getInstance().getEventManager().callEvent(new com.alta189.cyborg.api.event.channel.MessageEvent(messageEvent));
	}

	@Override
	public void onMode(ModeEvent modeEvent) throws Exception {
		super.onMode(modeEvent);
	}

	@Override
	public void onMotd(MotdEvent motdEvent) throws Exception {
		super.onMotd(motdEvent);
	}

	@Override
	public void onNickChange(NickChangeEvent nickChangeEvent) throws Exception {
		super.onNickChange(nickChangeEvent);
	}

	@Override
	public void onNotice(NoticeEvent noticeEvent) throws Exception {
		super.onNotice(noticeEvent);
	}

	@Override
	public void onOp(OpEvent opEvent) throws Exception {
		super.onOp(opEvent);
	}

	@Override
	public void onOwner(OwnerEvent ownerEvent) throws Exception {
		super.onOwner(ownerEvent);
	}

	@Override
	public void onPart(PartEvent partEvent) throws Exception {
		Cyborg.getInstance().getEventManager().callEvent(new UserPartEvent(partEvent));
	}

	@Override
	public void onPing(PingEvent pingEvent) throws Exception {
		super.onPing(pingEvent);
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent privateMessageEvent) throws Exception {
		Cyborg.getInstance().getEventManager().callEvent(new com.alta189.cyborg.api.event.bot.PrivateMessageEvent(privateMessageEvent));
	}

	@Override
	public void onQuit(QuitEvent quitEvent) throws Exception {
		super.onQuit(quitEvent);
	}

	@Override
	public void onRemoveChannelBan(RemoveChannelBanEvent removeChannelBanEvent) throws Exception {
		super.onRemoveChannelBan(removeChannelBanEvent);
	}

	@Override
	public void onRemoveChannelKey(RemoveChannelKeyEvent removeChannelKeyEvent) throws Exception {
		super.onRemoveChannelKey(removeChannelKeyEvent);
	}

	@Override
	public void onRemoveChannelLimit(RemoveChannelLimitEvent removeChannelLimitEvent) throws Exception {
		super.onRemoveChannelLimit(removeChannelLimitEvent);
	}

	@Override
	public void onRemoveInviteOnly(RemoveInviteOnlyEvent removeInviteOnlyEvent) throws Exception {
		super.onRemoveInviteOnly(removeInviteOnlyEvent);
	}

	@Override
	public void onRemoveModerated(RemoveModeratedEvent removeModeratedEvent) throws Exception {
		super.onRemoveModerated(removeModeratedEvent);
	}

	@Override
	public void onRemoveNoExternalMessages(RemoveNoExternalMessagesEvent removeNoExternalMessagesEvent) throws Exception {
		super.onRemoveNoExternalMessages(removeNoExternalMessagesEvent);
	}

	@Override
	public void onRemovePrivate(RemovePrivateEvent removePrivateEvent) throws Exception {
		super.onRemovePrivate(removePrivateEvent);
	}

	@Override
	public void onRemoveSecret(RemoveSecretEvent removeSecretEvent) throws Exception {
		super.onRemoveSecret(removeSecretEvent);
	}

	@Override
	public void onRemoveTopicProtection(RemoveTopicProtectionEvent removeTopicProtectionEvent) throws Exception {
		super.onRemoveTopicProtection(removeTopicProtectionEvent);
	}

	@Override
	public void onServerPing(ServerPingEvent serverPingEvent) throws Exception {
		super.onServerPing(serverPingEvent);
	}

	@Override
	public void onServerResponse(ServerResponseEvent serverResponseEvent) throws Exception {
		super.onServerResponse(serverResponseEvent);
	}

	@Override
	public void onSetChannelBan(SetChannelBanEvent setChannelBanEvent) throws Exception {
		super.onSetChannelBan(setChannelBanEvent);
	}

	@Override
	public void onSetChannelKey(SetChannelKeyEvent setChannelKeyEvent) throws Exception {
		super.onSetChannelKey(setChannelKeyEvent);
	}

	@Override
	public void onSetChannelLimit(SetChannelLimitEvent setChannelLimitEvent) throws Exception {
		super.onSetChannelLimit(setChannelLimitEvent);
	}

	@Override
	public void onSetInviteOnly(SetInviteOnlyEvent setInviteOnlyEvent) throws Exception {
		super.onSetInviteOnly(setInviteOnlyEvent);
	}

	@Override
	public void onSetModerated(SetModeratedEvent setModeratedEvent) throws Exception {
		super.onSetModerated(setModeratedEvent);
	}

	@Override
	public void onSetNoExternalMessages(SetNoExternalMessagesEvent setNoExternalMessagesEvent) throws Exception {
		super.onSetNoExternalMessages(setNoExternalMessagesEvent);
	}

	@Override
	public void onSetPrivate(SetPrivateEvent setPrivateEvent) throws Exception {
		super.onSetPrivate(setPrivateEvent);
	}

	@Override
	public void onSetSecret(SetSecretEvent setSecretEvent) throws Exception {
		super.onSetSecret(setSecretEvent);
	}

	@Override
	public void onSetTopicProtection(SetTopicProtectionEvent setTopicProtectionEvent) throws Exception {
		super.onSetTopicProtection(setTopicProtectionEvent);
	}

	@Override
	public void onSuperOp(SuperOpEvent superOpEvent) throws Exception {
		super.onSuperOp(superOpEvent);
	}

	@Override
	public void onTime(TimeEvent timeEvent) throws Exception {
		super.onTime(timeEvent);
	}

	@Override
	public void onTopic(TopicEvent topicEvent) throws Exception {
		super.onTopic(topicEvent);
	}

	@Override
	public void onUnknown(UnknownEvent unknownEvent) throws Exception {
		super.onUnknown(unknownEvent);
	}

	@Override
	public void onUserList(UserListEvent userListEvent) throws Exception {
		super.onUserList(userListEvent);
	}

	@Override
	public void onUserMode(UserModeEvent userModeEvent) throws Exception {
		super.onUserMode(userModeEvent);
	}

	@Override
	public void onVersion(VersionEvent versionEvent) throws Exception {
		super.onVersion(versionEvent);
	}

	@Override
	public void onVoice(VoiceEvent voiceEvent) throws Exception {
		super.onVoice(voiceEvent);
	}
}
