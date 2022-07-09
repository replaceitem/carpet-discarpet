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
import Discarpet.script.parsable.parsables.components.ButtonParsable;
import Discarpet.script.parsable.parsables.ColorParsable;
import Discarpet.script.parsable.parsables.components.ComponentParsable;
import Discarpet.script.parsable.parsables.embeds.EmbedAuthorParsable;
import Discarpet.script.parsable.parsables.embeds.EmbedFieldParsable;
import Discarpet.script.parsable.parsables.embeds.EmbedFooterParsable;
import Discarpet.script.parsable.parsables.embeds.EmbedParsable;
import Discarpet.script.parsable.parsables.commands.MessageContextMenuBuilderParsable;
import Discarpet.script.parsable.parsables.TimestampParsable;
import Discarpet.script.parsable.parsables.MessageContentParsable;
import Discarpet.script.parsable.parsables.components.ModalParsable;
import Discarpet.script.parsable.parsables.components.SelectMenuOptionParsable;
import Discarpet.script.parsable.parsables.components.SelectMenuParsable;
import Discarpet.script.parsable.parsables.commands.SlashCommandBuilderParsable;
import Discarpet.script.parsable.parsables.commands.SlashCommandOptionChoiceParsable;
import Discarpet.script.parsable.parsables.commands.SlashCommandOptionParsable;
import Discarpet.script.parsable.parsables.components.TextInputParsable;
import Discarpet.script.parsable.parsables.commands.UserContextMenuBuilderParsable;
import Discarpet.script.parsable.parsables.webhooks.WebhookMessageProfileParsable;
import Discarpet.script.parsable.parsables.webhooks.WebhookProfileParsable;
import Discarpet.script.parsable.parsables.webhooks.WebhookProfileUpdaterParsable;
import Discarpet.script.values.AttachmentValue;
import Discarpet.script.values.commands.MessageContextMenuValue;
import Discarpet.script.values.commands.SlashCommandValue;
import Discarpet.script.values.commands.UserContextMenuValue;
import Discarpet.script.values.interactions.ButtonInteractionValue;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.interactions.MessageContextMenuInteractionValue;
import Discarpet.script.values.interactions.ModalInteractionValue;
import Discarpet.script.values.ReactionValue;
import Discarpet.script.values.RoleValue;
import Discarpet.script.values.interactions.SelectMenuInteractionValue;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.interactions.SlashCommandInteractionOptionValue;
import Discarpet.script.values.interactions.SlashCommandInteractionValue;
import Discarpet.script.values.interactions.ApplicationCommandInteractionValue;
import Discarpet.script.values.UserValue;
import Discarpet.script.values.WebhookValue;
import Discarpet.script.values.common.DiscordValue;
import Discarpet.script.values.interactions.InteractionValue;
import Discarpet.script.values.common.MessageableValue;
import Discarpet.script.values.interactions.UserContextMenuInteractionValue;
import carpet.script.annotation.AnnotationParser;
import carpet.script.annotation.OutputConverter;
import carpet.script.annotation.SimpleTypeConverter;
import carpet.script.annotation.ValueCaster;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.webhook.Webhook;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.MessageContextMenu;
import org.javacord.api.interaction.MessageContextMenuInteraction;
import org.javacord.api.interaction.ModalInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.UserContextMenu;
import org.javacord.api.interaction.UserContextMenuInteraction;

import java.util.List;
import java.util.function.Function;

public class Registration {
    public static void registerDiscordValues() {
        // command
        registerDiscordValue(MessageContextMenuValue.class, MessageContextMenu.class, MessageContextMenuValue::new);
        registerDiscordValue(SlashCommandValue.class, SlashCommand.class, SlashCommandValue::new);
        registerDiscordValue(UserContextMenuValue.class, UserContextMenu.class, UserContextMenuValue::new);

        // interaction
        registerDiscordValue(ButtonInteractionValue.class, ButtonInteraction.class, ButtonInteractionValue::new);
        registerDiscordValue(MessageContextMenuInteractionValue.class, MessageContextMenuInteraction.class, MessageContextMenuInteractionValue::new);
        registerDiscordValue(ModalInteractionValue.class, ModalInteraction.class, ModalInteractionValue::new);
        registerDiscordValue(SelectMenuInteractionValue.class, SelectMenuInteraction.class, SelectMenuInteractionValue::new);
        registerDiscordValue(SlashCommandInteractionOptionValue.class, SlashCommandInteractionOption.class, SlashCommandInteractionOptionValue::new);
        registerDiscordValue(SlashCommandInteractionValue.class, SlashCommandInteraction.class, SlashCommandInteractionValue::new);
        registerDiscordValue(UserContextMenuInteractionValue.class, UserContextMenuInteraction.class, UserContextMenuInteractionValue::new);

        registerDiscordValue(AttachmentValue.class, Attachment.class, AttachmentValue::new);
        registerDiscordValue(ChannelValue.class, Channel.class, ChannelValue::new);
        registerDiscordValue(EmojiValue.class, Emoji.class, EmojiValue::new);
        registerDiscordValue(MessageValue.class, Message.class, MessageValue::new);
        registerDiscordValue(ReactionValue.class, Reaction.class, ReactionValue::new);
        registerDiscordValue(RoleValue.class, Role.class, RoleValue::new);
        registerDiscordValue(ServerValue.class, Server.class, ServerValue::new);
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
        Parser.registerParsable(ComponentParsable.class);
        
        Parser.registerParsable(AllowedMentionsParsable.class);
        Parser.registerParsable(AttachmentParsable.class);
        Parser.registerParsable(ButtonParsable.class);
        Parser.registerParsable(ColorParsable.class);
        Parser.registerParsable(EmbedAuthorParsable.class);
        Parser.registerParsable(EmbedFieldParsable.class);
        Parser.registerParsable(EmbedFooterParsable.class);
        Parser.registerParsable(EmbedParsable.class);
        Parser.registerParsable(MessageContentParsable.class);
        Parser.registerParsable(MessageContextMenuBuilderParsable.class);
        Parser.registerParsable(ModalParsable.class);
        Parser.registerParsable(SelectMenuOptionParsable.class);
        Parser.registerParsable(SelectMenuParsable.class);
        Parser.registerParsable(SlashCommandOptionChoiceParsable.class);
        Parser.registerParsable(SlashCommandOptionParsable.class);
        Parser.registerParsable(SlashCommandBuilderParsable.class);
        Parser.registerParsable(TextInputParsable.class);
        Parser.registerParsable(TimestampParsable.class);
        Parser.registerParsable(UserContextMenuBuilderParsable.class);
        Parser.registerParsable(WebhookMessageProfileParsable.class);
        Parser.registerParsable(WebhookProfileParsable.class);
        Parser.registerParsable(WebhookProfileUpdaterParsable.class);
    }
    
    public static void registerValueCasters() {
        ValueCaster.register(ApplicationCommandInteractionValue.class, "application_command_interaction");
        ValueCaster.register(DiscordValue.class, "discord");
        ValueCaster.register(InteractionValue.class, "interaction");
        ValueCaster.register(MessageableValue.class, "messageable");
    }
    
    public static void registerMisc() {
        // allows to return lists of values or other objects with a registered output converter
        OutputConverter.registerToValue(List.class, Registration::listOutputConverter);
    }

    private static Value listOutputConverter(List<?> list) {
        return ListValue.wrap(list.stream().map(o -> (o instanceof Value) ? (Value) o : DiscordValue.of(o)));
    }

    public static <T> void registerDiscordValue(Class<? extends DiscordValue<T>> valueClass, Class<T> internalClass, Function<T, Value> constructor) {
        String typeName = constructor.apply(null).getTypeString();
        SimpleTypeConverter.registerType(valueClass, internalClass, DiscordValue::getDelegate, typeName);
        OutputConverter.registerToValue(internalClass, constructor);
    }
}
