package net.replaceitem.discarpet.script.schema.schemas.commands;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@SchemaClass(name = "slash_command_option")
public class SlashCommandOptionSchema implements SchemaConstructor<SlashCommandOptionSchema.SlashCommandOptionUnion> {
    OptionType type;
    String name;
    String description;
    @OptionalField
    Boolean required = false;
    @OptionalField
    Boolean autocomplete = false;
    @OptionalField
    List<SlashCommandOptionSchema.SlashCommandOptionUnion> options = List.of();
    @OptionalField
    List<Command.Choice> choices = List.of();
    
    
    @Override
    public SlashCommandOptionUnion construct(Context context) {
        return switch (type) {
            case SUB_COMMAND -> SlashCommandOptionUnion.ofSubcommand(
                    new SubcommandData(name, description)
                            .addOptions(options.stream()
                                    .map(slashCommandOptionUnion -> slashCommandOptionUnion
                                            .asOption()
                                            .orElseThrow(() -> new InternalExpressionException("'options' of a subcommand can not be subcommands or subcommand groups"))
                                    )
                                    .toList()
                            )
            );
            case SUB_COMMAND_GROUP -> SlashCommandOptionUnion.ofSubcommandGroup(
                    new SubcommandGroupData(name, description)
                            .addSubcommands(options.stream()
                                    .map(slashCommandOptionUnion -> slashCommandOptionUnion
                                            .asSubcommand()
                                            .orElseThrow(() -> new InternalExpressionException("'options' of a subcommand group must be subcommands"))
                                    )
                                    .toList()
                            )
            );
            default -> SlashCommandOptionUnion.ofOption(new OptionData(type, name, description, required, autocomplete).addChoices(choices));
        };
    }
    
    public static class SlashCommandOptionUnion {
        
        private final @Nullable OptionData optionData;
        private final @Nullable SubcommandData subcommandData;
        private final @Nullable SubcommandGroupData subcommandGroupData;

        private SlashCommandOptionUnion(@Nullable OptionData optionData, @Nullable SubcommandData subcommandData, @Nullable SubcommandGroupData subcommandGroupData) {
            this.optionData = optionData;
            this.subcommandData = subcommandData;
            this.subcommandGroupData = subcommandGroupData;
        }

        static SlashCommandOptionUnion ofOption(OptionData optionData) {
            return new SlashCommandOptionUnion(optionData, null, null);
        }
        static SlashCommandOptionUnion ofSubcommand(SubcommandData subcommandData) {
            return new SlashCommandOptionUnion(null, subcommandData, null);
        }
        static SlashCommandOptionUnion ofSubcommandGroup(SubcommandGroupData subcommandGroupData) {
            return new SlashCommandOptionUnion(null, null, subcommandGroupData);
        }
        
        public Optional<OptionData> asOption() {
            return Optional.ofNullable(optionData);
        }
        
        public Optional<SubcommandData> asSubcommand() {
            return Optional.ofNullable(subcommandData);
        }
        
        public Optional<SubcommandGroupData> asSubcommandGroup() {
            return Optional.ofNullable(subcommandGroupData);
        }
    }
}
