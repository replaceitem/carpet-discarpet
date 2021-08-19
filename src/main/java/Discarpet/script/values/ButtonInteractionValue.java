package Discarpet.script.values;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.interaction.ButtonInteraction;

public class ButtonInteractionValue extends Value {

    public ButtonInteraction buttonInteraction;

    public ButtonInteractionValue(ButtonInteraction buttonInteraction) {
        this.buttonInteraction = buttonInteraction;
    }

    public Value getProperty(String property) {

        switch (property) {
            case "id":
                return StringValue.of(buttonInteraction.getCustomId());
            case "channel":
                if(buttonInteraction.getChannel().isEmpty()) return Value.NULL;
                return new ChannelValue(buttonInteraction.getChannel().get());
            case "user":
                return new UserValue(buttonInteraction.getUser());
            case "message":
                if(buttonInteraction.getMessage().isEmpty()) return Value.NULL;
                return new MessageValue(buttonInteraction.getMessage().get());
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
        return "dc_button_interaction";
    }

    @Override
    public String getString() {
        return buttonInteraction.getCustomId();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }

    public ButtonInteraction getButtonInteraction() {
        return buttonInteraction;
    }
}
