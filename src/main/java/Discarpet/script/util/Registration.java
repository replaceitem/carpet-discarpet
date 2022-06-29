package Discarpet.script.util;

import Discarpet.script.functions.Channels;
import Discarpet.script.functions.Interactions;
import Discarpet.script.functions.Messages;
import Discarpet.script.functions.Self;
import Discarpet.script.functions.Users;
import Discarpet.script.functions.Util;
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
import Discarpet.script.parsable.parsables.ModalParsable;
import Discarpet.script.parsable.parsables.SelectMenuOptionParsable;
import Discarpet.script.parsable.parsables.SelectMenuParsable;
import Discarpet.script.parsable.parsables.SlashCommandBuilderParsable;
import Discarpet.script.parsable.parsables.SlashCommandOptionChoiceParsable;
import Discarpet.script.parsable.parsables.SlashCommandOptionParsable;
import Discarpet.script.parsable.parsables.TextInputParsable;
import Discarpet.script.parsable.parsables.WebhookMessageProfileParsable;
import Discarpet.script.parsable.parsables.WebhookProfileParsable;
import Discarpet.script.parsable.parsables.WebhookProfileUpdaterParsable;
import Discarpet.script.values.AttachmentValue;
import Discarpet.script.values.ButtonInteractionValue;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.ModalInteractionValue;
import Discarpet.script.values.ReactionValue;
import Discarpet.script.values.RoleValue;
import Discarpet.script.values.SelectMenuInteractionValue;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.SlashCommandInteractionOptionValue;
import Discarpet.script.values.SlashCommandInteractionValue;
import Discarpet.script.values.SlashCommandValue;
import Discarpet.script.values.UserValue;
import Discarpet.script.values.WebhookValue;
import Discarpet.script.values.common.DiscordValue;
import Discarpet.script.values.common.InteractionValue;
import Discarpet.script.values.common.MessageableValue;
import carpet.script.annotation.AnnotationParser;
import carpet.script.annotation.OutputConverter;
import carpet.script.annotation.SimpleTypeConverter;
import carpet.script.annotation.ValueCaster;
import carpet.script.value.Value;
import org.javacord.api.entity.Attachment;
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
import org.javacord.api.interaction.ModalInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.util.function.Function;

public class Registration {
    public static void registerDiscordValue() {
        registerDiscordValue(AttachmentValue.class, Attachment.class, AttachmentValue::new);
        registerDiscordValue(ButtonInteractionValue.class, ButtonInteraction.class, ButtonInteractionValue::new);
        registerDiscordValue(ChannelValue.class, Channel.class, ChannelValue::new);
        registerDiscordValue(EmojiValue.class, Emoji.class, EmojiValue::new);
        registerDiscordValue(MessageValue.class, Message.class, MessageValue::new);
        registerDiscordValue(ModalInteractionValue.class, ModalInteraction.class, ModalInteractionValue::new);
        registerDiscordValue(ReactionValue.class, Reaction.class, ReactionValue::new);
        registerDiscordValue(RoleValue.class, Role.class, RoleValue::new);
        registerDiscordValue(SelectMenuInteractionValue.class, SelectMenuInteraction.class, SelectMenuInteractionValue::new);
        registerDiscordValue(ServerValue.class, Server.class, ServerValue::new);
        registerDiscordValue(SlashCommandInteractionOptionValue.class, SlashCommandInteractionOption.class, SlashCommandInteractionOptionValue::new);
        registerDiscordValue(SlashCommandInteractionValue.class, SlashCommandInteraction.class, SlashCommandInteractionValue::new);
        registerDiscordValue(SlashCommandValue.class, SlashCommand.class, SlashCommandValue::new);
        registerDiscordValue(UserValue.class, User.class, UserValue::new);
        registerDiscordValue(WebhookValue.class, Webhook.class, WebhookValue::new);
    }
    
    public static void registerFunctions() {
        AnnotationParser.parseFunctionClass(Channels.class);
        AnnotationParser.parseFunctionClass(Interactions.class);
        AnnotationParser.parseFunctionClass(Messages.class);
        AnnotationParser.parseFunctionClass(Self.class);
        AnnotationParser.parseFunctionClass(Users.class);
        AnnotationParser.parseFunctionClass(Util.class);
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
        Parser.registerParsable(ModalParsable.class);
        Parser.registerParsable(SelectMenuOptionParsable.class);
        Parser.registerParsable(SelectMenuParsable.class);
        Parser.registerParsable(SlashCommandOptionChoiceParsable.class);
        Parser.registerParsable(SlashCommandOptionParsable.class);
        Parser.registerParsable(SlashCommandBuilderParsable.class);
        Parser.registerParsable(TextInputParsable.class);
        Parser.registerParsable(WebhookMessageProfileParsable.class);
        Parser.registerParsable(WebhookProfileParsable.class);
        Parser.registerParsable(WebhookProfileUpdaterParsable.class);
    }
    
    public static void registerValueCasters() {
        ValueCaster.register(InteractionValue.class, "interaction");
        ValueCaster.register(MessageableValue.class, "messageable");
        ValueCaster.register(DiscordValue.class, "discord");
    }
    
    public static <T> void registerDiscordValue(Class<? extends DiscordValue<T>> valueClass, Class<T> internalClass, Function<T, Value> constructor) {
        String typeName = constructor.apply(null).getTypeString();
        SimpleTypeConverter.registerType(valueClass, internalClass, DiscordValue::getInternal, typeName);
        OutputConverter.registerToValue(internalClass, constructor);
    }
}
