package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.commands.SlashCommandValue;
import Discarpet.script.values.common.DiscordValue;
import Discarpet.script.values.common.Renamable;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.server.Server;

public class ServerValue extends DiscordValue<Server> implements Renamable {
    public ServerValue(Server server) {
        super("server",server);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "users" -> ListValue.wrap(delegate.getMembers().stream().map(UserValue::new));
            case "channels" -> ListValue.wrap(delegate.getChannels().stream().map(ChannelValue::new));
            case "roles" -> ListValue.wrap(delegate.getRoles().stream().map(RoleValue::new));
            case "webhooks" -> ListValue.wrap(delegate.getWebhooks().join().stream().map(WebhookValue::of));
            case "slash_commands" -> ListValue.wrap(delegate.getSlashCommands().join().stream().map(SlashCommandValue::of));
            default -> super.getProperty(property);
        };
    }

    @Override
    public boolean rename(String name) {
        return ValueUtil.awaitFutureBoolean(delegate.updateName(name), "Could not rename server");
    }
}
