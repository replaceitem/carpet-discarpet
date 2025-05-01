package net.replaceitem.discarpet.script.values.common;

import net.dv8tion.jda.api.requests.RestAction;

public interface Deletable {
    RestAction<?> delete(String reason);
}
