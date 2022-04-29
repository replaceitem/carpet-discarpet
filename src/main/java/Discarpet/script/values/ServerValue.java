package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.server.Server;

import java.util.Optional;

public class ServerValue extends Value {

    private final Server server;

    public ServerValue(Server server) {
        this.server = server;
    }

    public static Value of(Server server) {
        if(server == null) return Value.NULL;
        return new ServerValue(server);
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Value of(Optional<Server> optionalServer){
        return of(ValueUtil.unpackOptional(optionalServer));
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(server.getName());
            case "id" -> StringValue.of(server.getIdAsString());
            case "users" -> ListValue.wrap(server.getMembers().stream().map(UserValue::new));
            case "channels" -> ListValue.wrap(server.getChannels().stream().map(ChannelValue::new));
            case "roles" -> ListValue.wrap(server.getRoles().stream().map(RoleValue::new));
            case "webhooks" -> ListValue.wrap(server.getWebhooks().join().stream().map(WebhookValue::of));
            case "slash_commands" -> ListValue.wrap(server.getSlashCommands().join().stream().map(SlashCommandValue::new));
            default -> Value.NULL;
        };
    }


    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_server";
    }

    @Override
    public String getString() {
        return server.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }

	public Server getServer() {
		return server;
	}
}
