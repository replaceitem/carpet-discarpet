package Discarpet.script.functions;

import Discarpet.Bot;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.UserValue;

import carpet.script.Expression;
import carpet.script.value.*;

import static Discarpet.Discarpet.*;

public class Get {
    public static void apply(Expression expr) {
        expr.addContextFunction("dc_get_display_name", 2, (c, t, lv) -> {
            Value user = lv.get(0);
            Value server = lv.get(1);
            if(!(user instanceof UserValue)) scarpetException("dc_get_display_name","user",0);
            if(!(server instanceof ServerValue)) scarpetException("dc_get_display_name","server",1);
            return StringValue.of(((ServerValue) server).server.getDisplayName(((UserValue) user).user));
        });

        expr.addContextFunction("dc_get_bot_user", 0, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_get_bot_user");
            return new UserValue(b.getApi().getYourself());
        });
    }
}
