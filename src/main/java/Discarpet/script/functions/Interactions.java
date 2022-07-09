package Discarpet.script.functions;

import Discarpet.config.Bot;
import Discarpet.script.parsable.Parser;
import Discarpet.script.parsable.parsables.MessageContentParsable;
import Discarpet.script.parsable.parsables.components.ModalParsable;
import Discarpet.script.util.ValueUtil;
import Discarpet.script.util.content.InteractionFollowupMessageContentApplier;
import Discarpet.script.util.content.InteractionImmediateResponseContentApplier;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.common.DiscordValue;
import Discarpet.script.values.interactions.InteractionValue;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.ApplicationCommand;
import org.javacord.api.interaction.ApplicationCommandBuilder;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.MessageContextMenuBuilder;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.UserContextMenuBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static Discarpet.Discarpet.getBotInContext;
import static Discarpet.script.util.ValueUtil.awaitFuture;

@SuppressWarnings("unused")
public class Interactions {
    @ScarpetFunction
    public Value dc_create_slash_command(Context context, Value command, Value server) {
        context.host.issueDeprecation("dc_create_slash_command");
        return dc_create_application_command(context, "slash_command", command, server instanceof ServerValue serverValue ? serverValue.getDelegate() : null);
    }
    
    @ScarpetFunction(maxParams = 3)
    public Value dc_create_application_command(Context context, String type, Value command, Server... optionalServer) {
        Class<? extends ApplicationCommandBuilder<?,?,?>> commandType = switch (type) {
            case "slash_command" -> SlashCommandBuilder.class;
            case "user_context_menu" -> UserContextMenuBuilder.class;
            case "message_context_menu" -> MessageContextMenuBuilder.class;
            default -> throw new InternalExpressionException("Invalid application command type");
        };
        ApplicationCommandBuilder<?, ?, ?> applicationCommandBuilder = Parser.parseType(command, commandType);
        Server server = ValueUtil.optionalArg(optionalServer);
        CompletableFuture<? extends ApplicationCommand> cf;
        if(server == null) {
            Bot bot = getBotInContext(context, "dc_create_application_command");
            cf = applicationCommandBuilder.createGlobal(bot.getApi());
        } else {
            cf = applicationCommandBuilder.createForServer(server);
        }
        return DiscordValue.of(awaitFuture(cf, "Error creating application command"));        
    }

    @ScarpetFunction
    public List<ApplicationCommand> dc_get_global_application_commands(Context context) {
        Bot bot = getBotInContext(context,"dc_get_global_application_commands");
        DiscordApi api = bot.getApi();
        return awaitFuture(api.getGlobalApplicationCommands(), "Could not get global application commands");
    }
    
    @ScarpetFunction(maxParams = 3)
    public Message dc_respond_interaction(InteractionValue<?> interactionValue, String responseType, Value... response) {
        InteractionBase interactionBase = interactionValue.getBase();
        if(responseType.equalsIgnoreCase("RESPOND_LATER")) {
            awaitFuture(interactionBase.respondLater(),"Error sending 'respond_later' response to interaction");
            return null;
        }

        if(responseType.equalsIgnoreCase("RESPOND_MODAL")) {
            if(response.length == 0) throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + responseType);
            ModalParsable modalParsable = Parser.parseType(response[0], ModalParsable.class);
            modalParsable.apply(interactionBase);
            return null;
        }

        if (responseType.equalsIgnoreCase("RESPOND_IMMEDIATELY") || responseType.equalsIgnoreCase("RESPOND_FOLLOWUP")) {
            if(response.length == 0) throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + responseType);
            MessageContentParsable messageContentParsable = Parser.parseType(response[0], MessageContentParsable.class);
            if(responseType.equalsIgnoreCase("RESPOND_IMMEDIATELY")) {
                InteractionImmediateResponseContentApplier contentApplier = new InteractionImmediateResponseContentApplier(interactionBase.createImmediateResponder());
                messageContentParsable.apply(contentApplier);
                awaitFuture(contentApplier.get().respond(),"Error sending 'respond_immediately' response to interaction");
                return null;
            } else {
                InteractionFollowupMessageContentApplier contentApplier = new InteractionFollowupMessageContentApplier(interactionBase.createFollowupMessageBuilder());
                messageContentParsable.apply(contentApplier);
                return awaitFuture(contentApplier.get().send(),"Error sending 'respond_followup' response to interaction");
            }
        } else throw new InternalExpressionException("invalid response type for 'dc_respond_interaction', expected RESPOND_LATER, RESPOND_IMMEDIATELY or RESPOND_FOLLOWUP");
    }
}
