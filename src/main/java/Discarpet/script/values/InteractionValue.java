package Discarpet.script.values;

import carpet.script.value.Value;
import org.javacord.api.interaction.InteractionBase;

public abstract class InteractionValue extends Value {
    public abstract InteractionBase getInteractionBase();
}
