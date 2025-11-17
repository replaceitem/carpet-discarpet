package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SchemaClass(name = "button")
public class ButtonSchema implements SchemaConstructor<Button> {
    @OptionalField @Nullable
    String id;
    String label;
    @OptionalField
    Boolean disabled = false;
    @OptionalField
    ButtonStyle style = ButtonStyle.SECONDARY;
    @OptionalField @Nullable
    Emoji emoji;
    @OptionalField @Nullable
    String url;
    
    @Override
    public Button construct(Context context) {
        return Button.of(
                style,
                style == ButtonStyle.LINK ?
                        Objects.requireNonNull(url, "Link style requires an url") :
                        Objects.requireNonNull(id, "Non-link style requires an id"),
                label,
                emoji
        ).withDisabled(disabled);
    }
}
