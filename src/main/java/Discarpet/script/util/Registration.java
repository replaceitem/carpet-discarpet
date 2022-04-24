package Discarpet.script.util;

import Discarpet.script.functions.Channels;
import Discarpet.script.functions.Messages;
import Discarpet.script.functions.Self;
import Discarpet.script.functions.Users;
import Discarpet.script.functions.ValueFromId;
import Discarpet.script.parsable.Parser;
import Discarpet.script.parsable.parsables.AllowedMentionsParsable;
import Discarpet.script.parsable.parsables.AttachmentParsable;
import Discarpet.script.parsable.parsables.ButtonParsable;
import Discarpet.script.parsable.parsables.ColorParsable;
import Discarpet.script.parsable.parsables.ComponentParsable;
import Discarpet.script.parsable.parsables.EmbedAuthorParsable;
import Discarpet.script.parsable.parsables.EmbedFieldParsable;
import Discarpet.script.parsable.parsables.EmbedFooterParsable;
import Discarpet.script.parsable.parsables.EmbedParsable;
import Discarpet.script.parsable.parsables.InstantParsable;
import Discarpet.script.parsable.parsables.MessageContentParsable;
import Discarpet.script.parsable.parsables.SelectMenuOptionParsable;
import Discarpet.script.parsable.parsables.SelectMenuParsable;
import Discarpet.script.parsable.parsables.SlashCommandOptionChoiceParsable;
import Discarpet.script.parsable.parsables.SlashCommandOptionParsable;
import Discarpet.script.parsable.parsables.WebhookMessageProfileParsable;
import Discarpet.script.parsable.parsables.WebhookProfileParsable;
import Discarpet.script.parsable.parsables.WebhookProfileUpdaterParsable;
import Discarpet.script.values.AttachmentValue;
import Discarpet.script.values.ButtonInteractionValue;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.ReactionValue;
import Discarpet.script.values.RoleValue;
import Discarpet.script.values.SelectMenuInteractionValue;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.SlashCommandInteractionValue;
import Discarpet.script.values.UserValue;
import Discarpet.script.values.WebhookValue;
import carpet.script.annotation.AnnotationParser;
import carpet.script.annotation.OutputConverter;
import carpet.script.annotation.SimpleTypeConverter;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.webhook.Webhook;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;

public class Registration {
    public static void registerInputTypes() {
        SimpleTypeConverter.registerType(AttachmentValue.class, MessageAttachment.class, AttachmentValue::getAttachment, "attachment");
        SimpleTypeConverter.registerType(ButtonInteractionValue.class, ButtonInteraction.class, ButtonInteractionValue::getButtonInteraction, "button_interaction");
        SimpleTypeConverter.registerType(ChannelValue.class, Channel.class, ChannelValue::getChannel, "channel");
        SimpleTypeConverter.registerType(EmojiValue.class, Emoji.class, EmojiValue::getEmoji, "emoji");
        SimpleTypeConverter.registerType(MessageValue.class, Message.class, MessageValue::getMessage, "message");
        SimpleTypeConverter.registerType(ReactionValue.class, Reaction.class, ReactionValue::getReaction, "message");
        SimpleTypeConverter.registerType(RoleValue.class, Role.class, RoleValue::getRole, "role");
        SimpleTypeConverter.registerType(SelectMenuInteractionValue.class, SelectMenuInteraction.class, SelectMenuInteractionValue::getSelectMenuInteraction, "message");
        SimpleTypeConverter.registerType(ServerValue.class, Server.class, ServerValue::getServer, "server");
        SimpleTypeConverter.registerType(SlashCommandInteractionValue.class, SlashCommandInteraction.class, SlashCommandInteractionValue::getSlashCommandInteraction, "slash_command_interaction");
        SimpleTypeConverter.registerType(UserValue.class, User.class, UserValue::getUser, "user");
        SimpleTypeConverter.registerType(WebhookValue.class, Webhook.class, WebhookValue::getWebhook, "webhook");
    }
    
    public static void registerOutputTypes() {
        OutputConverter.registerToValue(MessageAttachment.class, AttachmentValue::new);
        OutputConverter.registerToValue(ButtonInteraction.class, ButtonInteractionValue::new);
        OutputConverter.registerToValue(Channel.class, ChannelValue::new);
        OutputConverter.registerToValue(Emoji.class, EmojiValue::new);
        OutputConverter.registerToValue(Message.class, MessageValue::new);
        OutputConverter.registerToValue(Reaction.class, ReactionValue::new);
        OutputConverter.registerToValue(Role.class, RoleValue::new);
        OutputConverter.registerToValue(SelectMenuInteraction.class, SelectMenuInteractionValue::new);
        OutputConverter.registerToValue(Server.class, ServerValue::new);
        OutputConverter.registerToValue(SlashCommandInteraction.class, SlashCommandInteractionValue::new);
        OutputConverter.registerToValue(User.class, UserValue::new);
        OutputConverter.registerToValue(Webhook.class, WebhookValue::new);
    }
    
    public static void registerFunctions() {
        AnnotationParser.parseFunctionClass(Channels.class);
        AnnotationParser.parseFunctionClass(Messages.class);
        AnnotationParser.parseFunctionClass(Self.class);
        AnnotationParser.parseFunctionClass(Users.class);
        AnnotationParser.parseFunctionClass(ValueFromId.class);
    }
    
    public static void registerParsables() {
        Parser.registerParsable(AllowedMentionsParsable.class);
        Parser.registerParsable(AttachmentParsable.class);
        Parser.registerParsable(ButtonParsable.class);
        Parser.registerParsable(ColorParsable.class);
        Parser.registerParsable(ComponentParsable.class);
        Parser.registerParsable(EmbedAuthorParsable.class);
        Parser.registerParsable(EmbedFieldParsable.class);
        Parser.registerParsable(EmbedFooterParsable.class);
        Parser.registerParsable(EmbedParsable.class);
        Parser.registerParsable(InstantParsable.class);
        Parser.registerParsable(MessageContentParsable.class);
        Parser.registerParsable(SelectMenuOptionParsable.class);
        Parser.registerParsable(SelectMenuParsable.class);
        Parser.registerParsable(SlashCommandOptionChoiceParsable.class);
        Parser.registerParsable(SlashCommandOptionParsable.class);
        Parser.registerParsable(WebhookMessageProfileParsable.class);
        Parser.registerParsable(WebhookProfileParsable.class);
        Parser.registerParsable(WebhookProfileUpdaterParsable.class);
    }
}
