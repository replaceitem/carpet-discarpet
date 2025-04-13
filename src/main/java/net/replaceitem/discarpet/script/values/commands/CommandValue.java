package net.replaceitem.discarpet.script.values.commands;

import carpet.script.value.ListValue;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.replaceitem.discarpet.mixins.CommandImplMixin;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.ServerValue;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;

public class CommandValue extends DiscordValue<Command> implements Deletable {
    public CommandValue(Command applicationCommand) {
        super(applicationCommand);
    }

    @Override
    protected String getDiscordTypeString() {
        return switch (delegate.getType()) {
            case UNKNOWN -> "command";
            case SLASH -> "slash_command_value";
            case USER -> "user_context_menu";
            case MESSAGE -> "message_context_menu";
        };
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getId());
            case "name" -> StringValue.of(delegate.getName());
            case "description" -> StringValue.of(delegate.getDescription());
            case "server" -> ServerValue.of(((CommandImplMixin) delegate).getGuild());
            case "options" -> ListValue.wrap(delegate.getOptions().stream().map(slashCommandOption -> StringValue.of(slashCommandOption.getName())));
            default -> super.getProperty(property);
        };
    }

    @Override
    public void delete(String reason) {
        ValueUtil.awaitRest(delegate.delete(), "Failed to delete " + this.getTypeString());
    }
}
