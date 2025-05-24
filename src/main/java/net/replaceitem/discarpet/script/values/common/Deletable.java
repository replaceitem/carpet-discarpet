package net.replaceitem.discarpet.script.values.common;

import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.Nullable;

public interface Deletable {
    RestAction<?> delete(@Nullable String reason);
}
