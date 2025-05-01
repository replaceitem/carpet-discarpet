package net.replaceitem.discarpet.script.values.commands;

import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.RestAction;
import net.replaceitem.discarpet.mixins.CommandImplMixin;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.ServerValue;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;

public class CommandValue extends DiscordValue<Command> implements Deletable {
    public CommandValue(Command applicationCommand) {
        super(applicationCommand);
    }

    @Override
    protected String getDiscordTypeString() {
        return "command";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getId());
            case "name" -> StringValue.of(delegate.getName());
            case "description" -> StringValue.of(delegate.getDescription());
            case "type" -> ValueUtil.ofEnum(delegate.getType());
            case "server" -> ServerValue.of(((CommandImplMixin) delegate).getGuild());
            case "options" -> ListValue.wrap(delegate.getOptions().stream().map(slashCommandOption -> StringValue.of(slashCommandOption.getName())));
            default -> super.getProperty(property);
        };
    }

    @Override
    public RestAction<?> delete(String reason) {
        return delegate.delete();
    }
}
