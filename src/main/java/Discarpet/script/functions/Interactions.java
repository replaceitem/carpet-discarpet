package Discarpet.script.functions;

import Discarpet.Discarpet;
import Discarpet.script.values.EmbedBuilderValue;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.SlashCommandInteractionValue;
import carpet.script.Expression;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
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
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

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
                slashCommandCompletableFuture = builder.createGlobal(Discarpet.getBotInContext(c).getApi());
            } else throw new InternalExpressionException("'dc_create_slash_command' requires a server or null as the third parameter");

            slashCommandCompletableFuture.exceptionally(throwable -> {
                Discarpet.LOGGER.error("Failed to create slash command:");
                throwable.printStackTrace();
                return null;
            });

            return Value.TRUE;
        });

        expr.addContextFunction("dc_delete_slash_command",-1, (c, t, lv) -> {
            DiscordApi api = Discarpet.getBotInContext(c).getApi();

            if(lv.size() == 0) { //delete all
                deleteGlobalSlashCommands(api);
                api.getServers().forEach(server -> Interactions.deleteServerSlashCommands(api,server));
                return Value.TRUE;
            }

            Value serverValue = lv.get(0);

            if(lv.size()==1) { //delete all in server / global
                if(serverValue.isNull()) {
                    deleteGlobalSlashCommands(api);
                } else if(serverValue instanceof ServerValue) {
                    deleteServerSlashCommands(api,((ServerValue) serverValue).getServer());
                } else throw new InternalExpressionException("'dc_delete_slash_command' requires a server or null as the third parameter");
            }

            String name = lv.get(1).getString();

            if(lv.size() == 2) { //delete in server / global with specific name
                if(serverValue.isNull()) {
                    api.getGlobalSlashCommands().join().forEach(slashCommand -> {
                        if(slashCommand.getName().equals(name)) slashCommand.deleteGlobal().join();
                    });
                } else if (serverValue instanceof ServerValue) {
                    api.getServerSlashCommands(((ServerValue) serverValue).getServer()).join().forEach(slashCommand -> {
                        if(slashCommand.getName().equals(name)) slashCommand.deleteForServer(((ServerValue) serverValue).getServer()).join();
                    });
                } else throw new InternalExpressionException("'dc_delete_slash_command' requires a server or null as the second parameter");
            }

            return Value.TRUE;
        });


        expr.addContextFunction("dc_respond_interaction",-1, (c, t, lv) -> {
            if(lv.size() < 2 || lv.size() > 3) throw new InternalExpressionException("'dc_respond_interaction' expected 2 or 3 parameters");
            Value slashCommandInteractionValue = lv.get(0);
            if(!(slashCommandInteractionValue instanceof SlashCommandInteractionValue)) throw new InternalExpressionException("'dc_respond_interaction' expected a message interaction or slash command value as the first argument");
            SlashCommandInteraction slashCommandInteraction = ((SlashCommandInteractionValue) slashCommandInteractionValue).slashCommandInteraction;
            String type = lv.get(1).getString();

            if(type.equalsIgnoreCase("RESPOND_LATER")) {
                slashCommandInteraction.respondLater();
            } else if (type.equalsIgnoreCase("RESPOND_IMMEDIATELY") || type.equalsIgnoreCase("RESPOND_FOLLOWUP")) {
                if(lv.size() != 3) throw new InternalExpressionException("'dc_respond_interaction' expected a third argument for " + type);
                Value content = lv.get(2);
                if(type.equalsIgnoreCase("RESPOND_IMMEDIATELY")) {
                    InteractionImmediateResponseBuilder immediateResponder = slashCommandInteraction.createImmediateResponder();
                    if(content instanceof EmbedBuilderValue) immediateResponder.addEmbed(((EmbedBuilderValue) content).embedBuilder);
                    else immediateResponder.setContent(content.getString());
                    immediateResponder.respond();
                } else {
                    InteractionFollowupMessageBuilder followupMessageBuilder = slashCommandInteraction.createFollowupMessageBuilder();
                    if(content instanceof EmbedBuilderValue) followupMessageBuilder.addEmbed(((EmbedBuilderValue) content).embedBuilder);
                    else followupMessageBuilder.setContent(content.getString());
                    followupMessageBuilder.send();
                }
            } else throw new InternalExpressionException("invalid response type for 'dc_respond_interaction', expected RESPOND_LATER, RESPOND_IMMEDIATELY or RESPOND_FOLLOWUP");
            return Value.TRUE;
        });
    }

    public static void deleteGlobalSlashCommands(DiscordApi api) {
        api.getGlobalSlashCommands().join().forEach(slashCommand -> slashCommand.deleteGlobal().join());
    }

    public static void deleteServerSlashCommands(DiscordApi api, Server server) {
        try {
            api.getServerSlashCommands(server).join().forEach(slashCommand -> {
                slashCommand.deleteForServer(server).join();
            });
        } catch (CompletionException ignored) {}
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

//SAME AS Sending.messageBuilderFromValue but for some reason they dont share a common interface, even though same methods -_-

    public static InteractionMessageBuilderBase<?> interactionMessageFromValue(Value value, InteractionMessageBuilderBase<?> interactionMessageBuilderBase) {
        if(value instanceof StringValue) {
            interactionMessageBuilderBase.setContent(value.getString());
            return interactionMessageBuilderBase;
        }

        if(value instanceof EmbedBuilderValue) {
            interactionMessageBuilderBase.addEmbed(((EmbedBuilderValue) value).embedBuilder);
            return interactionMessageBuilderBase;
        }

        if(value instanceof MapValue) {
            Map<Value, Value> map = ((MapValue) value).getMap();

            Value contentValue = map.get(new StringValue("content"));
            interactionMessageBuilderBase.setContent(contentValue.getString());


            Value attachmentsValue = map.get(new StringValue("attachments"));
            if(attachmentsValue != null) {
                if (attachmentsValue instanceof ListValue) {
                    if(!(interactionMessageBuilderBase instanceof ExtendedInteractionMessageBuilderBase)) throw new InternalExpressionException("Cannot add attachments for RESPOND_IMMEDIATELY");
                    for (Value v : ((ListValue) attachmentsValue).getItems()) {
                        addAttachmentFromValue((ExtendedInteractionMessageBuilderBase<?>) interactionMessageBuilderBase, v);
                    }
                } else throw new InternalExpressionException("Expected a list as 'attachments' value");
            }

            Value embedsValue = map.get(new StringValue("embeds"));
            if(embedsValue != null) {
                if (embedsValue instanceof ListValue) {
                    for (Value v : ((ListValue) embedsValue).getItems()) {
                        if(v instanceof EmbedBuilderValue) {
                            interactionMessageBuilderBase.addEmbed(((EmbedBuilderValue) v).embedBuilder);
                        } else throw new InternalExpressionException("'embeds' list expected only embed builder values");
                    }
                } else throw new InternalExpressionException("Expected a list as 'embeds' value");
            }


            Value componentsValue = map.get(new StringValue("components"));
            if(componentsValue != null) {
                if (componentsValue instanceof ListValue) {
                    for (Value v : ((ListValue) componentsValue).getItems()) {
                        interactionMessageBuilderBase.addComponents(Sending.getActionRowFromValue(v));
                    }
                } else throw new InternalExpressionException("Expected a list as 'components' value");
            }
        }

        return interactionMessageBuilderBase;
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
                throw new InternalExpressionException("Invalid URL for attachment file '" + urlValue.getString() + "': " + e.toString());
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
}
