package net.replaceitem.discarpet.script.parsable.parsables.commands;

import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@ParsableClass(name = "slash_command_option")
public class SlashCommandOptionParsable implements ParsableConstructor<SlashCommandOptionParsable.SlashCommandOptionUnion> {
    OptionType type;
    String name;
    String description;
    @OptionalField
    Boolean required = false;
    @OptionalField
    List<SlashCommandOptionParsable.SlashCommandOptionUnion> options = List.of();
    @OptionalField
    List<Command.Choice> choices = List.of();
    
    
    @Override
    public SlashCommandOptionUnion construct() {
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
            default -> SlashCommandOptionUnion.ofOption(new OptionData(type, name, description, required).addChoices(choices));
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
