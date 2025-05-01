package net.replaceitem.discarpet.script.functions;

import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.callbacks.IModalCallback;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.config.Bot;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.MessageContentParsable;
import net.replaceitem.discarpet.script.parsable.parsables.commands.MessageContextMenuBuilderParsable;
import net.replaceitem.discarpet.script.parsable.parsables.commands.SlashCommandBuilderParsable;
import net.replaceitem.discarpet.script.parsable.parsables.commands.UserContextMenuBuilderParsable;
import net.replaceitem.discarpet.script.parsable.parsables.components.ModalParsable;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.interactions.InteractionValue;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public class Interactions {
    @ScarpetFunction(maxParams = 3)
    public Value dc_create_application_command(Context context, String type, Value command, Guild... optionalServer) {
        Class<? extends ParsableConstructor<? extends CommandData>> commandType = switch (type) {
            case "slash_command" -> SlashCommandBuilderParsable.class;
            case "user_context_menu" -> UserContextMenuBuilderParsable.class;
            case "message_context_menu" -> MessageContextMenuBuilderParsable.class;
            default -> throw new InternalExpressionException("Invalid application command type");
        };
        ParsableConstructor<? extends CommandData> parsableConstructor = Parser.parseType(command, commandType);
        Guild server = ValueUtil.optionalArg(optionalServer);
        RestAction<Command> action;
        if(server == null) {
            Bot bot = Discarpet.getBotInContext(context, "dc_create_application_command");
            action = bot.getJda().upsertCommand(parsableConstructor.construct());
        } else {
            action = server.upsertCommand(parsableConstructor.construct());
        }
        return DiscordValue.of(ValueUtil.awaitRest(action, "Error creating application command"));        
    }

    @ScarpetFunction
    public List<Command> dc_get_global_application_commands(Context context) {
        Bot bot = Discarpet.getBotInContext(context,"dc_get_global_application_commands");
        JDA jda = bot.getJda();
        return ValueUtil.awaitRest(jda.retrieveCommands(), "Could not get global application commands");
    }
    
    @ScarpetFunction(maxParams = 3)
    @Nullable("Only returns a message when an actual message was created during the response")
    public Message dc_respond_interaction(InteractionValue<?> interactionValue, String responseType, Value... response) {
        GenericInteractionCreateEvent event = interactionValue.getDelegate();
        if(responseType.equalsIgnoreCase("RESPOND_LATER")) {
            if(!(event instanceof IReplyCallback replyCallback)) throw new InternalExpressionException("Interaction of type " + event.getType() + " cannot be replied to");
            ValueUtil.awaitRest(replyCallback.deferReply(),"Error sending 'respond_later' response to interaction");
            return null;
        }

        if(responseType.equalsIgnoreCase("RESPOND_MODAL")) {
            if(!(event instanceof IModalCallback modalCallback)) throw new InternalExpressionException("Interaction of type " + event.getType() + " cannot be replied to");
            if(response.length == 0) throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + responseType);
            ModalParsable modalParsable = Parser.parseType(response[0], ModalParsable.class);
            Modal modal = modalParsable.construct();
            ValueUtil.awaitRest(modalCallback.replyModal(modal), "Could not reply with modal");
            return null;
        }

        if (responseType.equalsIgnoreCase("RESPOND_IMMEDIATELY") || responseType.equalsIgnoreCase("RESPOND_FOLLOWUP")) {
            if(response.length == 0) throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + responseType);
            MessageContentParsable messageContentParsable = Parser.parseType(response[0], MessageContentParsable.class);
            if(!(event instanceof IReplyCallback replyCallback)) throw new InternalExpressionException("Interaction of type " + event.getType() + " cannot be replied to");
            if(responseType.equalsIgnoreCase("RESPOND_IMMEDIATELY")) {
                MessageCreateBuilder builder = new MessageCreateBuilder();
                messageContentParsable.apply(builder);
                ValueUtil.awaitRest(replyCallback.reply(builder.build()),"Error sending 'respond_immediately' response to interaction");
                return null;
            } else {
                MessageCreateBuilder builder = new MessageCreateBuilder();
                messageContentParsable.apply(builder);
                return ValueUtil.awaitRest(replyCallback.getHook().sendMessage(builder.build()),"Error sending 'respond_followup' response to interaction");
            }
        } else throw new InternalExpressionException("invalid response type for 'dc_respond_interaction', expected RESPOND_LATER, RESPOND_IMMEDIATELY or RESPOND_FOLLOWUP");
    }
}
