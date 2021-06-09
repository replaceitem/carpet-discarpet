package Discarpet.script.values;

import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.server.Server;

import java.util.stream.Collectors;

public class ServerValue extends Value {

    private final Server server;

    public ServerValue(Server server) {
        this.server = server;
    }

    public Value getProperty(String property) {
        switch (property) {
            case "name":
                return StringValue.of(server.getName());
            case "id":
                return StringValue.of(server.getIdAsString());
            case "users":
                return ListValue.wrap(server.getMembers().stream().map(UserValue::new).collect(Collectors.toList()));
            case "channels":
                return ListValue.wrap(server.getMembers().stream().map(UserValue::new).collect(Collectors.toList()));
            default:
                return Value.NULL;
        }
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
        return server.getName();
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }

	public Server getServer() {
		return server;
	}
}
