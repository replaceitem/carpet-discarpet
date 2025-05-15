package net.replaceitem.discarpet.script.util;

import carpet.script.annotation.AnnotationParser;
import carpet.script.annotation.OutputConverter;
import carpet.script.annotation.SimpleTypeConverter;
import carpet.script.annotation.ValueCaster;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.replaceitem.discarpet.script.functions.*;
import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.*;
import net.replaceitem.discarpet.script.parsable.parsables.commands.*;
import net.replaceitem.discarpet.script.parsable.parsables.components.*;
import net.replaceitem.discarpet.script.parsable.parsables.embeds.EmbedParsable;
import net.replaceitem.discarpet.script.values.*;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.MessageableValue;
import net.replaceitem.discarpet.script.values.interactions.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class  Registration {
    public static void registerDiscordValues() {
        // command
        registerDiscordValue(CommandValue.class, Command.class, CommandValue::new);

        // interaction
        registerDiscordValue(ButtonInteractionValue.class, ButtonInteractionEvent.class, ButtonInteractionValue::new);
        registerDiscordValue(MessageContextMenuInteractionValue.class, MessageContextInteractionEvent.class, MessageContextMenuInteractionValue::new);
        registerDiscordValue(ModalInteractionValue.class, ModalInteractionEvent.class, ModalInteractionValue::new);
        registerDiscordValue(SelectMenuInteractionValue.class, GenericSelectMenuInteractionEvent.class, SelectMenuInteractionValue::new);
        registerDiscordValue(SlashCommandInteractionOptionValue.class, OptionMapping.class, SlashCommandInteractionOptionValue::new);
        registerDiscordValue(SlashCommandInteractionValue.class, SlashCommandInteractionEvent.class, SlashCommandInteractionValue::new);
        registerDiscordValue(UserContextMenuInteractionValue.class, UserContextInteractionEvent.class, UserContextMenuInteractionValue::new);

        registerDiscordValue(AttachmentValue.class, Message.Attachment.class, AttachmentValue::new);
        registerDiscordValue(ChannelValue.class, Channel.class, ChannelValue::new);
        registerDiscordValue(EmojiValue.class, Emoji.class, EmojiValue::new);
        registerDiscordValue(MemberValue.class, Member.class, MemberValue::new);
        registerDiscordValue(MessageValue.class, Message.class, MessageValue::new);
        registerDiscordValue(ReactionValue.class, MessageReaction.class, ReactionValue::new);
        registerDiscordValue(RoleValue.class, Role.class, RoleValue::new);
        registerDiscordValue(ServerValue.class, Guild.class, ServerValue::new);
        registerDiscordValue(StickerValue.class, Sticker.class, StickerValue::new);
        registerDiscordValue(UserValue.class, User.class, UserValue::new);
        registerDiscordValue(WebhookValue.class, WebhookClient.class, WebhookValue::new);
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
        
        Parser.registerParsable(AttachmentParsable.class);
        Parser.registerParsable(ButtonParsable.class);
        Parser.registerParsable(ColorParsable.class);
        Parser.registerParsable(EmojiParsable.class);
        Parser.registerParsable(FileParsable.class);
        Parser.registerParsable(EmbedParsable.class);
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
    }
    
    public static void registerValueCasters() {
        ValueCaster.register(ApplicationCommandInteractionValue.class, "application_command_interaction");
        ValueCaster.register(DiscordValue.class, "discord");
        ValueCaster.register(InteractionValue.class, "interaction");
        ValueCaster.register(MessageableValue.class, "messageable");
    }
    
    public static void registerMisc() {
        // allows to return lists of values or other objects with a registered output converter
        OutputConverter.register(List.class, Registration::listOutputConverter);
        OutputConverter.register(Optional.class, Registration::optionalOutputConverter);
    }
    
    private static Value listOutputConverter(List<?> list) {
        return ListValue.wrap(list.stream().map(o -> (o instanceof Value) ? (Value) o : ValueConversions.toValue(o)));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static Value optionalOutputConverter(Optional<?> optional) {
        return ValueConversions.toValue(optional.orElse(null));
    }

    public static <T, U extends DiscordValue<? extends T>> void registerDiscordValue(Class<U> valueClass, Class<T> internalClass, Function<T, Value> constructor) {
        String typeName;
        try {
            typeName = constructor.apply(null).getTypeString();
        } catch (NullPointerException npe) {
            typeName = npe.getMessage();
        }
        SimpleTypeConverter.registerType(valueClass, internalClass, DiscordValue::getDelegate, typeName);
        OutputConverter.register(internalClass, constructor);
    }
}
