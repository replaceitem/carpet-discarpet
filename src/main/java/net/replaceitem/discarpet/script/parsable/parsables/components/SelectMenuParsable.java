package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.entity.message.component.SelectMenuOption;

import java.util.List;
import java.util.Locale;

@ParsableClass(name = "select_menu")
public class SelectMenuParsable implements ParsableConstructor<SelectMenu> {
    String id;
    ComponentType component;
    @Optional Boolean disabled = false;
    @Optional List<SelectMenuOption> options = List.of();
    @Optional Integer min = 1;
    @Optional Integer max = 1;
    @Optional String placeholder;

    @Override
    public SelectMenu construct() {
        SelectMenuBuilder selectMenuBuilder = new SelectMenuBuilder(component, id);

        selectMenuBuilder.setDisabled(disabled);
        selectMenuBuilder.addOptions(options);
        selectMenuBuilder.setMinimumValues(min);
        selectMenuBuilder.setMaximumValues(max);
        selectMenuBuilder.setPlaceholder(placeholder);
        return selectMenuBuilder.build();
    }
}
