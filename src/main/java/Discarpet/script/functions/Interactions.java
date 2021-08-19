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
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

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


        expr.addContextFunction("dc_respond_slash_command",-1, (c, t, lv) -> {
            if(lv.size() < 2 || lv.size() > 3) throw new InternalExpressionException("'dc_respond_slash_command' expected 2 or 3 parameters");
            Value slashCommandInteractionValue = lv.get(0);
            if(!(slashCommandInteractionValue instanceof SlashCommandInteractionValue)) throw new InternalExpressionException("'dc_respond_slash_command' expected a dc_slash_command_interaction value as the first argument");
            SlashCommandInteraction slashCommandInteraction = ((SlashCommandInteractionValue) slashCommandInteractionValue).slashCommandInteraction;
            String type = lv.get(1).getString();

            if(type.equalsIgnoreCase("RESPOND_LATER")) {
                slashCommandInteraction.respondLater();
            } else if (type.equalsIgnoreCase("RESPOND_IMMEDIATELY") || type.equalsIgnoreCase("RESPOND_FOLLOWUP")) {
                if(lv.size() != 3) throw new InternalExpressionException("'dc_respond_slash_command' expected a third argument for " + type);
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
            } else throw new InternalExpressionException("invalid response type for 'dc_respond_slash_command', expected RESPOND_LATER, RESPOND_IMMEDIATELY or RESPOND_FOLLOWUP");
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
}
