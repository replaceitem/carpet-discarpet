package Discarpet.script.values;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.Tag;
import org.javacord.api.entity.server.Server;

public class ServerValue extends Value {

    public Server server;

    public ServerValue(Server server) {
        this.server = server;
    }

    public Value getProperty(String property) {
        switch (property) {
            case "name":
                return StringValue.of(server.getName());
            case "id":
                return StringValue.of(server.getIdAsString());
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
    public Tag toTag(boolean b) {
        return null;
    }
}
