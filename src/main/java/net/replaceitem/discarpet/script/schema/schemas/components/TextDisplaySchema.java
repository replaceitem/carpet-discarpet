package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

@SchemaClass(name = "text_display")
public class TextDisplaySchema implements SchemaConstructor<TextDisplay> {
    
    String content;

    @Override
    public TextDisplay construct(Context context) {
        return TextDisplay.of(content);
    }
}
