package net.replaceitem.discarpet.script.values.commands;

import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.ServerValue;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.ApplicationCommand;

public abstract class ApplicationCommandValue<T extends ApplicationCommand> extends DiscordValue<T> implements Deletable {
    public ApplicationCommandValue(T applicationCommand) {
        super(applicationCommand);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "name" -> StringValue.of(delegate.getName());
            case "description" -> StringValue.of(delegate.getDescription());
            case "server" -> ServerValue.of(delegate.getServer());
            default -> super.getProperty(property);
        };
    }

    @Override
    public void delete(String reason) {
        ValueUtil.awaitFuture(delegate.delete(), "Failed to delete " + this.getTypeString());
    }
}
