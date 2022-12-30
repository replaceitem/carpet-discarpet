package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.Redirector;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.TextInput;

@ParsableClass(name = "component")
public class ComponentParsable implements Redirector<LowLevelComponent> {
    
    String component;

    @Override
    public Class<? extends LowLevelComponent> redirect() {
        if(component.equalsIgnoreCase("button")) {
            return Button.class;
        } else if(component.equalsIgnoreCase("select_menu")) {
            return SelectMenu.class;
        } else if(component.equalsIgnoreCase("text_input")) {
            return TextInput.class;
        } else throw new InternalExpressionException("'component' needs to be either 'button', 'select_menu' or 'text_input'");
    }
}
