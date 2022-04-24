package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuBuilder;
import org.javacord.api.entity.message.component.SelectMenuOption;

import java.util.List;

@ParsableClass(name = "select_menu")
public class SelectMenuParsable implements ParsableConstructor<SelectMenu> {
    String id;
    @Optional Boolean disabled = false;
    @Optional List<SelectMenuOption> options = List.of();
    @Optional Integer min = 1;
    @Optional Integer max = 1;
    @Optional String placeholder;
    
    
    @Override
    public SelectMenu construct() {
        SelectMenuBuilder selectMenuBuilder = new SelectMenuBuilder();

        selectMenuBuilder.setCustomId(id);
        selectMenuBuilder.setDisabled(disabled);

        selectMenuBuilder.addOptions(options);
        selectMenuBuilder.setMinimumValues(min);
        selectMenuBuilder.setMaximumValues(max);
        selectMenuBuilder.setPlaceholder(placeholder);
        return selectMenuBuilder.build();
    }
}
