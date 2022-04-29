package Discarpet.script.values;

import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.server.Server;

public class ServerValue extends DiscordValue<Server> {
    public ServerValue(Server server) {
        super("server",server);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(value.getName());
            case "id" -> StringValue.of(value.getIdAsString());
            case "users" -> ListValue.wrap(value.getMembers().stream().map(UserValue::new));
            case "channels" -> ListValue.wrap(value.getChannels().stream().map(ChannelValue::new));
            case "roles" -> ListValue.wrap(value.getRoles().stream().map(RoleValue::new));
            case "webhooks" -> ListValue.wrap(value.getWebhooks().join().stream().map(WebhookValue::of));
            case "slash_commands" -> ListValue.wrap(value.getSlashCommands().join().stream().map(SlashCommandValue::new));
            default -> Value.NULL;
        };
    }
}
