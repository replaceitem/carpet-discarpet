package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.Redirector;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.SelectMenu;

@ParsableClass(name = "component")
public class ComponentParsable implements Redirector<LowLevelComponent> {
    
    String component;

    @Override
    public Class<? extends LowLevelComponent> redirect() {
        if(component.equalsIgnoreCase("button")) {
            return Button.class;
        } else if(component.equalsIgnoreCase("select_menu")) {
            return SelectMenu.class;
        } else throw new InternalExpressionException("'component' needs to be either 'button' or 'select_menu'");
    }
}
