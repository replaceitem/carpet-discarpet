package Discarpet.script.functions;

import Discarpet.config.Bot;
import Discarpet.Discarpet;
import Discarpet.script.util.MessageContentParser;
import Discarpet.script.util.SlashCommandParser;
import Discarpet.script.util.ValueUtil;
import Discarpet.script.util.content.InteractionFollowupMessageContentApplier;
import Discarpet.script.util.content.InteractionImmediateResponseContentApplier;
import Discarpet.script.values.InteractionValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.ServerValue;
import carpet.script.Expression;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.BooleanValue;
import carpet.script.value.Value;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static Discarpet.Discarpet.getBotInContext;

public class Interactions {
    public static void apply(Expression expr) {
        expr.addContextFunction("dc_create_slash_command",-1, (c, t, lv) -> {
            if(lv.size() < 3) throw new InternalExpressionException("'dc_create_slash_command' requires at least a name, description and server/null");
            if(lv.size() > 4) throw new InternalExpressionException("'dc_create_slash_command' requires 3 or 4 parameters");
            String name = lv.get(0).getString();
            String description = lv.get(1).getString();

            SlashCommandBuilder builder = new SlashCommandBuilder();
            builder.setName(name);
            builder.setDescription(description);

            if(lv.size() == 4) builder.setOptions(SlashCommandParser.parseSlashCommandOptions(lv.get(3)));

            Value serverValue = lv.get(2);
            CompletableFuture<SlashCommand> slashCommandCompletableFuture;
            if(serverValue instanceof ServerValue) {
                slashCommandCompletableFuture = builder.createForServer(((ServerValue) serverValue).getServer());
            } else if(serverValue.isNull()) {
                slashCommandCompletableFuture = builder.createGlobal(Discarpet.getBotInContext(c,"dc_create_slash_command").getApi());
            } else throw new InternalExpressionException("'dc_create_slash_command' requires a server or null as the third parameter");

            return BooleanValue.of(ValueUtil.awaitFutureBoolean(slashCommandCompletableFuture,"Error creating slash command"));
        });

        expr.addContextFunction("dc_delete_slash_command",-1, (c, t, lv) -> {
            Bot bot = getBotInContext(c,"dc_delete_slash_command");
            DiscordApi api = bot.getApi();

            if(lv.size() == 0) { //delete all
                return BooleanValue.of(ValueUtil.awaitFutureBoolean(CompletableFuture.allOf(deleteGlobalSlashCommands(api),deleteAllServerSlashCommands(api)),"Error deleting all slash commands"));
            }

            Value serverValue = lv.get(0);

            if(lv.size()==1) { //delete all in server / global
                if(serverValue.isNull()) {
                    return BooleanValue.of(ValueUtil.awaitFutureBoolean(deleteGlobalSlashCommands(api),"Error deleting all global slash commands"));
                } else if(serverValue instanceof ServerValue server) {
                    return BooleanValue.of(ValueUtil.awaitFutureBoolean(deleteServerSlashCommands(api, server.getServer()),"Error deleting server slash commands"));
                } else throw new InternalExpressionException("'dc_delete_slash_command' requires a server or null as the third parameter");
            }

            String name = lv.get(1).getString();

            if(serverValue.isNull()) {
                return BooleanValue.of(ValueUtil.awaitFutureBoolean(deleteGlobalSlashCommandsByName(api,name),"Error deleting global slash command"));
            } else if (serverValue instanceof ServerValue server) {
                return BooleanValue.of(ValueUtil.awaitFutureBoolean(deleteServerSlashCommandsByName(api,server.getServer(),name),"Error deleting server slash command"));
            } else throw new InternalExpressionException("'dc_delete_slash_command' requires a server or null as the second parameter");
        });


        expr.addContextFunction("dc_respond_interaction",-1, (c, t, lv) -> {
            if(lv.size() < 2 || lv.size() > 3) throw new InternalExpressionException("'dc_respond_interaction' expected 2 or 3 parameters");
            if(!(lv.get(0) instanceof InteractionValue interactionValue)) throw new InternalExpressionException("'dc_respond_interaction' expected a message interaction or slash command value as the first argument");
            InteractionBase interactionBase = interactionValue.getInteractionBase();
            String type = lv.get(1).getString();

            if(type.equalsIgnoreCase("RESPOND_LATER"))
                return BooleanValue.of(ValueUtil.awaitFutureBoolean(interactionBase.respondLater(),"Error sending 'respond_later' response to interaction"));

            if (type.equalsIgnoreCase("RESPOND_IMMEDIATELY") || type.equalsIgnoreCase("RESPOND_FOLLOWUP")) {
                if(lv.size() != 3) throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + type);
                Value content = lv.get(2);
                if(type.equalsIgnoreCase("RESPOND_IMMEDIATELY")) {
                    InteractionImmediateResponseContentApplier contentApplier = new InteractionImmediateResponseContentApplier(interactionBase.createImmediateResponder());
                    MessageContentParser.parseMessageContent(contentApplier,content);
                    return BooleanValue.of(ValueUtil.awaitFutureBoolean(contentApplier.get().respond(),"Error sending 'respond_immediately' response to interaction"));
                } else {
                    InteractionFollowupMessageContentApplier contentApplier = new InteractionFollowupMessageContentApplier(interactionBase.createFollowupMessageBuilder());
                    MessageContentParser.parseMessageContent(contentApplier,content);
                    return MessageValue.of(ValueUtil.awaitFuture(contentApplier.get().send(),"Error sending 'respond_followup' response to interaction"));
                }
            } else throw new InternalExpressionException("invalid response type for 'dc_respond_interaction', expected RESPOND_LATER, RESPOND_IMMEDIATELY or RESPOND_FOLLOWUP");
        });
    }

    public static CompletableFuture<Void> deleteGlobalSlashCommands(DiscordApi api) {
        return api.getGlobalSlashCommands().thenAccept(slashCommands -> {
            futureStreamAllOf(slashCommands.stream().map(SlashCommand::deleteGlobal));
        });
    }

    public static CompletableFuture<Void> deleteGlobalSlashCommandsByName(DiscordApi api, String name) {
        return api.getGlobalSlashCommands().thenAccept(slashCommands -> futureStreamAllOf(slashCommands.stream()
                .filter(slashCommand -> slashCommand.getName().equals(name))
                .map(SlashCommand::deleteGlobal)));
    }

    public static CompletableFuture<Void> deleteAllServerSlashCommands(DiscordApi api) {
        return futureStreamAllOf(api.getServers().stream().map(server -> deleteServerSlashCommands(api,server)));
    }

    public static CompletableFuture<Void> deleteServerSlashCommands(DiscordApi api, Server server) {
        return api.getServerSlashCommands(server).thenAccept(slashCommands -> {
            futureStreamAllOf(slashCommands.stream().map(slashCommand -> slashCommand.deleteForServer(server)));
        });
    }

    public static CompletableFuture<Void> deleteServerSlashCommandsByName(DiscordApi api, Server server, String name) {
        return api.getServerSlashCommands(server).thenAccept(slashCommands -> futureStreamAllOf(slashCommands.stream()
                        .filter(slashCommand -> slashCommand.getName().equals(name))
                        .map(slashCommand -> slashCommand.deleteForServer(server))));
    }

    private static CompletableFuture<Void> futureStreamAllOf(Stream<CompletableFuture<?>> completableFutureStream) {
        CompletableFuture<?>[] futures = completableFutureStream.toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures);
    }
}
