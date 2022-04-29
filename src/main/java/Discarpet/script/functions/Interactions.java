package Discarpet.script.functions;

import Discarpet.config.Bot;
import Discarpet.script.parsable.Parser;
import Discarpet.script.parsable.parsables.MessageContentParsable;
import Discarpet.script.parsable.parsables.SlashCommandBuilderParsable;
import Discarpet.script.util.ValueUtil;
import Discarpet.script.util.content.InteractionFollowupMessageContentApplier;
import Discarpet.script.util.content.InteractionImmediateResponseContentApplier;
import Discarpet.script.values.common.InteractionValue;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.SlashCommandValue;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;

import java.util.concurrent.CompletableFuture;

import static Discarpet.Discarpet.getBotInContext;

@SuppressWarnings("unused")
public class Interactions {
    @ScarpetFunction
    public boolean dc_create_slash_command(Context context, Value command, Value server) {
        SlashCommandBuilder slashCommandBuilder = Parser.parseType(command, SlashCommandBuilderParsable.class).construct();
        CompletableFuture<SlashCommand> cf;
        if(server.isNull()) {
            Bot bot = getBotInContext(context, "dc_create_slash_command");
            cf = slashCommandBuilder.createGlobal(bot.getApi());
        } else  if (server instanceof ServerValue serverValue){
            cf = slashCommandBuilder.createForServer(serverValue.getInternal());
        } else throw new InternalExpressionException("Expected a server or null");
        return ValueUtil.awaitFutureBoolean(cf, "Error creating slash command");        
    }

    @ScarpetFunction
    public ListValue dc_get_global_slash_commands(Context context) {
        Bot bot = getBotInContext(context,"dc_delete_slash_command");
        DiscordApi api = bot.getApi();
        return ListValue.wrap(api.getGlobalSlashCommands().join().stream().map(SlashCommandValue::new));
    }
    
    @ScarpetFunction(maxParams = 3)
    public Message dc_respond_interaction(InteractionValue<?> interactionValue, String responseType, Value... response) {
        InteractionBase interactionBase = interactionValue.getBase();
        if(responseType.equalsIgnoreCase("RESPOND_LATER")) {
            ValueUtil.awaitFuture(interactionBase.respondLater(),"Error sending 'respond_later' response to interaction");
            return null;
        }

        if (responseType.equalsIgnoreCase("RESPOND_IMMEDIATELY") || responseType.equalsIgnoreCase("RESPOND_FOLLOWUP")) {
            if(response.length == 0) throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + responseType);
            MessageContentParsable messageContentParsable = Parser.parseType(response[0], MessageContentParsable.class);
            if(responseType.equalsIgnoreCase("RESPOND_IMMEDIATELY")) {
                InteractionImmediateResponseContentApplier contentApplier = new InteractionImmediateResponseContentApplier(interactionBase.createImmediateResponder());
                messageContentParsable.apply(contentApplier);
                ValueUtil.awaitFuture(contentApplier.get().respond(),"Error sending 'respond_immediately' response to interaction");
                return null;
            } else {
                InteractionFollowupMessageContentApplier contentApplier = new InteractionFollowupMessageContentApplier(interactionBase.createFollowupMessageBuilder());
                messageContentParsable.apply(contentApplier);
                return ValueUtil.awaitFuture(contentApplier.get().send(),"Error sending 'respond_followup' response to interaction");
            }
        } else throw new InternalExpressionException("invalid response type for 'dc_respond_interaction', expected RESPOND_LATER, RESPOND_IMMEDIATELY or RESPOND_FOLLOWUP");
    }
}
