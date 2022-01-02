package Discarpet.script.functions;

import Discarpet.Bot;
import Discarpet.Discarpet;
import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.EmbedBuilderValue;
import Discarpet.script.values.InteractionValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.ServerValue;
import carpet.script.Expression;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.javacord.api.interaction.callback.ExtendedInteractionMessageBuilderBase;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.interaction.callback.InteractionMessageBuilderBase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Discarpet.Discarpet.getBotInContext;
import static Discarpet.Discarpet.scarpetNoBotException;
import static Discarpet.script.util.ScarpetMapValueUtil.*;

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

            Value optionsValue;

            if(lv.size() == 4) {
                optionsValue = lv.get(3);
            } else {
                optionsValue = null;
            }

            if(optionsValue != null) {
                builder.setOptions(Interactions.parseCommandOptions(optionsValue));
            }

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

            if(type.equalsIgnoreCase("RESPOND_LATER")) {
                return BooleanValue.of(ValueUtil.awaitFutureBoolean(interactionBase.respondLater(),"Error sending 'respond_later' response to interaction"));
            } else if (type.equalsIgnoreCase("RESPOND_IMMEDIATELY") || type.equalsIgnoreCase("RESPOND_FOLLOWUP")) {
                if(lv.size() != 3) throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + type);
                Value content = lv.get(2);
                if(type.equalsIgnoreCase("RESPOND_IMMEDIATELY")) {
                    InteractionImmediateResponseBuilder immediateResponder = interactionBase.createImmediateResponder();
                    applyValueToInteractionMessage(content,immediateResponder);
                    return BooleanValue.of(ValueUtil.awaitFutureBoolean(immediateResponder.respond(),"Error sending 'respond_immediately' response to interaction"));
                } else {
                    InteractionFollowupMessageBuilder followupMessageBuilder = interactionBase.createFollowupMessageBuilder();
                    applyValueToInteractionMessage(content,followupMessageBuilder);
                    return MessageValue.of(ValueUtil.awaitFuture(followupMessageBuilder.send(),"Error sending 'respond_followup' response to interaction"));
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





    public static List<SlashCommandOption> parseCommandOptions(Value value) {
        if(!(value instanceof ListValue)) throw new InternalExpressionException("'options' parameter in slash command needs to be a list");
        return ((ListValue) value).getItems().stream().map(Interactions::parseCommandOption).collect(Collectors.toList());
    }

    public static List<SlashCommandOptionChoice> parseCommandOptionChoices(Value value) {
        if(!(value instanceof ListValue)) throw new InternalExpressionException("'choices' parameter in slash command needs to be a list");
        return ((ListValue) value).getItems().stream().map(Interactions::parseCommandOptionChoice).collect(Collectors.toList());
    }

    public static SlashCommandOption parseCommandOption(Value value) {
        SlashCommandOptionBuilder builder = new SlashCommandOptionBuilder();

        if(!(value instanceof MapValue)) throw new InternalExpressionException("values inside slash command 'options' list need to be map values");
        Map<Value,Value> valueMap = ((MapValue) value).getMap();

        Value typeValue = valueMap.get(new StringValue("type"));
        if(typeValue == null) throw new InternalExpressionException("missing 'type' value for slash command option");
        SlashCommandOptionType type = SlashCommandOptionType.valueOf(typeValue.getString());
        builder.setType(type);

        Value name = valueMap.get(new StringValue("name"));
        if(name == null) throw new InternalExpressionException("missing 'name' value for slash command option");
        builder.setName(name.getString());

        Value description = valueMap.get(new StringValue("description"));
        if(description == null) throw new InternalExpressionException("missing 'description' value for slash command option");
        builder.setDescription(description.getString());

        Value required = valueMap.get(new StringValue("required"));
        if(required != null) builder.setRequired(required.getBoolean());

        Value optionsValue = valueMap.get(new StringValue("options"));
        if(optionsValue != null)
            builder.setOptions(parseCommandOptions(optionsValue));

        Value choicesValue = valueMap.get(new StringValue("choices"));
        if(choicesValue != null)
            builder.setChoices(parseCommandOptionChoices(choicesValue));

        return builder.build();
    }

    public static SlashCommandOptionChoice parseCommandOptionChoice(Value value) {
        if(!(value instanceof MapValue)) throw new InternalExpressionException("values inside slash command 'choices' list need to be map values");
        Map<Value,Value> valueMap = ((MapValue) value).getMap();

        Value nameValue = valueMap.get(new StringValue("name"));
        if(nameValue == null) throw new InternalExpressionException("missing 'name' value for slash command choice");
        String name = nameValue.getString();

        Value valueValue = valueMap.get(new StringValue("value"));
        if(valueValue == null) throw new InternalExpressionException("missing 'value' value for slash command choice");

        if(valueValue instanceof NumericValue) {
            return SlashCommandOptionChoice.create(name, ((NumericValue)valueValue).getInt());
        } else {
            return SlashCommandOptionChoice.create(name, valueValue.getString());
        }
    }

// ALMOST SAME AS Sending.messageBuilderFromValue but for some reason they dont share a common interface, even though same methods -_-

    public static void applyValueToInteractionMessage(Value value, InteractionMessageBuilderBase<?> interactionMessageBuilderBase) {

        if(value instanceof EmbedBuilderValue) {
            interactionMessageBuilderBase.addEmbed(((EmbedBuilderValue) value).embedBuilder);
        } else if(value instanceof MapValue) {
            Map<Value, Value> map = ((MapValue) value).getMap();

            if(mapHasKey(map,"content")) {
                interactionMessageBuilderBase.setContent(getStringInMap(map,"content"));
            }

            if(mapHasKey(map,"attachments")) {
                List<Value> attachments = getListInMap(map,"attachments");
                for (Value v : attachments) {
                    if(!(interactionMessageBuilderBase instanceof ExtendedInteractionMessageBuilderBase)) throw new InternalExpressionException("Attachments can only be added to RESPOND_FOLLOWUP");
                    addAttachmentFromValue(((ExtendedInteractionMessageBuilderBase<?>) interactionMessageBuilderBase), v);
                }
            }

            if(mapHasKey(map,"embeds")) {
                List<Value> embeds = getListInMap(map,"embeds");
                for (Value v : embeds) {
                    if(v instanceof EmbedBuilderValue) {
                        interactionMessageBuilderBase.addEmbed(((EmbedBuilderValue) v).embedBuilder);
                    } else throw new InternalExpressionException("'embeds' list expected only embed builder values");
                }
            }

            if(mapHasKey(map,"components")) {
                List<Value> components = getListInMap(map,"components");
                for (Value v : components) {
                    interactionMessageBuilderBase.addComponents(Messages.getActionRowFromValue(v));
                }
            }
        } else {
            interactionMessageBuilderBase.setContent(value.getString());
        }
    }

    public static void addAttachmentFromValue(ExtendedInteractionMessageBuilderBase<?> interactionMessageBuilderBase, Value value) {
        if(!(value instanceof MapValue)) throw new InternalExpressionException("Expected a map as entry in 'attachments'");
        Map<Value, Value> map = ((MapValue) value).getMap();

        boolean spoiler = map.getOrDefault(new StringValue("spoiler"),Value.FALSE).getBoolean();

        Value fileValue = map.get(new StringValue("file"));
        if(fileValue != null) {
            File file = new File(fileValue.getString());
            if(!file.exists()) throw new InternalExpressionException("Invalid path for attachment file '" + fileValue.getString() + "'");
            if(spoiler) interactionMessageBuilderBase.addAttachmentAsSpoiler(file);
            else interactionMessageBuilderBase.addAttachment(file);
            return;
        }

        Value urlValue = map.get(new StringValue("url"));
        if(urlValue != null) {
            URL url;
            try {
                url = new URL(urlValue.getString());
            } catch (MalformedURLException e) {
                throw new InternalExpressionException("Invalid URL for attachment file '" + urlValue.getString() + "': " + e);
            }
            if(spoiler) interactionMessageBuilderBase.addAttachmentAsSpoiler(url);
            else interactionMessageBuilderBase.addAttachment(url);
            return;
        }


        Value bytesValue = map.get(new StringValue("bytes"));
        if(bytesValue != null) {
            Value nameValue = map.get(new StringValue("name"));
            if(nameValue == null) throw new InternalExpressionException("Missing 'name' for 'bytes' attachment type");
            String name = nameValue.getString();
            byte[] data = bytesValue.getString().getBytes();
            if(spoiler) interactionMessageBuilderBase.addAttachmentAsSpoiler(data,name);
            else interactionMessageBuilderBase.addAttachment(data,name);
        }
    }


    private static CompletableFuture<Void> futureStreamAllOf(Stream<CompletableFuture<?>> completableFutureStream) {
        CompletableFuture<?>[] futures = completableFutureStream.toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures);
    }
}
