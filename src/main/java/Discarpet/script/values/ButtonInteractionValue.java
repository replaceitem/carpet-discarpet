package Discarpet.script.values;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.InteractionBase;

public class ButtonInteractionValue extends Value implements InteractionValue {

    public ButtonInteraction buttonInteraction;

    public ButtonInteractionValue(ButtonInteraction buttonInteraction) {
        this.buttonInteraction = buttonInteraction;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(buttonInteraction.getCustomId());
            case "channel" -> ChannelValue.of(buttonInteraction.getChannel());
            case "user" -> new UserValue(buttonInteraction.getUser());
            case "message" -> MessageValue.of(buttonInteraction.getMessage());
            default -> Value.NULL;
        };
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
        return buttonInteraction.toString();
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

    @Override
    public InteractionBase getInteractionBase() {
        return buttonInteraction;
    }
}
