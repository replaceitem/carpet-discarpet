package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import Discarpet.script.util.ValueUtil;
import carpet.script.value.Value;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.component.SelectMenuOptionBuilder;

@ParsableClass(name = "select_menu_option")
public class SelectMenuOptionParsable implements ParsableConstructor<SelectMenuOption> {
    
    String value;
    String label;
    @Optional Value emoji;
    @Optional String description;
    @Optional Boolean default_option = false;
    
    
    @Override
    public SelectMenuOption construct() {
        SelectMenuOptionBuilder selectMenuOptionBuilder = new SelectMenuOptionBuilder();
        selectMenuOptionBuilder.setValue(value);
        selectMenuOptionBuilder.setLabel(label);
        selectMenuOptionBuilder.setEmoji(ValueUtil.emojiFromValue(emoji));
        selectMenuOptionBuilder.setDescription(description);
        selectMenuOptionBuilder.setDefault(default_option);
        return selectMenuOptionBuilder.build();
    }
}
