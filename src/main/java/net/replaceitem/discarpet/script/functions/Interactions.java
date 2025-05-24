package net.replaceitem.discarpet.script.functions;

import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.callbacks.IModalCallback;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.config.Bot;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.MessageContentParsable;
import net.replaceitem.discarpet.script.parsable.parsables.RespondLaterDataParsable;
import net.replaceitem.discarpet.script.parsable.parsables.commands.MessageContextMenuBuilderParsable;
import net.replaceitem.discarpet.script.parsable.parsables.commands.SlashCommandBuilderParsable;
import net.replaceitem.discarpet.script.parsable.parsables.commands.UserContextMenuBuilderParsable;
import net.replaceitem.discarpet.script.parsable.parsables.components.ModalParsable;
import net.replaceitem.discarpet.script.util.EnumUtil;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.CommandValue;
import net.replaceitem.discarpet.script.values.interactions.InteractionValue;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
public class Interactions {
    private enum ApplicationCommandType {
        SLASH_COMMAND(SlashCommandBuilderParsable.class),
        USER_CONTEXT_MENU(UserContextMenuBuilderParsable.class),
        MESSAGE_CONTEXT_MENU(MessageContextMenuBuilderParsable.class);

        private final Class<? extends ParsableConstructor<? extends CommandData>> parsableClass;

        ApplicationCommandType(Class<? extends ParsableConstructor<? extends CommandData>> parsableClass) {
            this.parsableClass = parsableClass;
        }
    }
    
    @ScarpetFunction(maxParams = 3)
    public Value dc_create_application_command(Context context, String typeString, Value command, Guild... optionalServer) {
        ApplicationCommandType type = EnumUtil.getEnumOrThrow(ApplicationCommandType.class, typeString, "Invalid application command type for the first parameter of 'dc_create_application_command'");
        Class<? extends ParsableConstructor<? extends CommandData>> parsableClass = type.parsableClass;
        ParsableConstructor<? extends CommandData> parsableConstructor = Parser.parseType(context, command, parsableClass);
        Guild server = ValueUtil.optionalArg(optionalServer);
        RestAction<Command> action;
        if(server == null) {
            Bot bot = Discarpet.getBotInContext(context, "dc_create_application_command");
            action = bot.getJda().upsertCommand(parsableConstructor.construct(context));
        } else {
            action = server.upsertCommand(parsableConstructor.construct(context));
        }
        return CommandValue.of(ValueUtil.awaitRest(action, "Error creating application command"));        
    }

    @ScarpetFunction
    public List<Command> dc_get_global_application_commands(Context context) {
        Bot bot = Discarpet.getBotInContext(context,"dc_get_global_application_commands");
        JDA jda = bot.getJda();
        return ValueUtil.awaitRest(jda.retrieveCommands(), "Could not get global application commands");
    }
    
    private enum ResponseType {
        RESPOND_LATER,
        RESPOND_MODAL,
        RESPOND_IMMEDIATELY,
        RESPOND_FOLLOWUP
    }
    
    @ScarpetFunction(maxParams = 3)
    @Nullable("Only returns a message when an actual message was created during the response")
    public Message dc_respond_interaction(Context context, InteractionValue<?> interactionValue, String responseTypeString, Optional<Value> data) {
        GenericInteractionCreateEvent event = interactionValue.getDelegate();
        ResponseType responseType = EnumUtil.getEnumOrThrow(ResponseType.class, responseTypeString, "Invalid response type for 'dc_respond_interaction'");
        switch (responseType) {
            case RESPOND_LATER -> {
                if (!(event instanceof IReplyCallback replyCallback))
                    throw DiscordThrowables.genericMessage("Interaction of type " + event.getType() + " cannot be replied to");
                RespondLaterDataParsable respondLaterParsable = data.map(d -> Parser.parseType(context, d, RespondLaterDataParsable.class)).orElseGet(RespondLaterDataParsable::new);
                ReplyCallbackAction replyCallbackAction = replyCallback.deferReply();
                respondLaterParsable.apply(replyCallbackAction);
                ValueUtil.awaitRest(replyCallbackAction, "Error sending 'respond_later' response to interaction");
                return null;
            }
            case RESPOND_MODAL -> {
                if (!(event instanceof IModalCallback modalCallback))
                    throw DiscordThrowables.genericMessage("Interaction of type " + event.getType() + " cannot be replied to");
                if (data.isEmpty())
                    throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + responseTypeString);
                ModalParsable modalParsable = Parser.parseType(context, data.get(), ModalParsable.class);
                Modal modal = modalParsable.construct(context);
                ValueUtil.awaitRest(modalCallback.replyModal(modal), "Could not reply with modal");
                return null;
            }
            case RESPOND_IMMEDIATELY, RESPOND_FOLLOWUP -> {
                if (data.isEmpty())
                    throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + responseTypeString);
                MessageContentParsable messageContentParsable = Parser.parseType(context, data.get(), MessageContentParsable.class);
                if (!(event instanceof IReplyCallback replyCallback))
                    throw DiscordThrowables.genericMessage("Interaction of type " + event.getType() + " cannot be replied to");
                if (responseType == ResponseType.RESPOND_IMMEDIATELY) {
                    ReplyCallbackAction action = messageContentParsable.apply(new MessageCreateBuilder(), MessageCreateBuilder::build, replyCallback::reply);
                    return ValueUtil.awaitRest(action.map(interactionHook -> interactionHook.getCallbackResponse().getMessage()), "Error sending 'respond_immediately' response to interaction");
                } else {
                    InteractionHook hook = replyCallback.getHook();
                    WebhookMessageCreateAction<Message> action = messageContentParsable.apply(new MessageCreateBuilder(), MessageCreateBuilder::build, hook::sendMessage);
                    return ValueUtil.awaitRest(action, "Error sending 'respond_followup' response to interaction");
                }
            }
            default -> throw new IllegalStateException();
        }
    }
}
