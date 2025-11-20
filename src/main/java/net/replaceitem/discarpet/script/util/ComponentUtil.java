package net.replaceitem.discarpet.script.util;

import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.components.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ComponentUtil {
    public static <T extends Component> List<T> ensureComponentType(List<Component> components, Class<T> componentInterface, Function<Component, String> message) {
        return components.stream().mapMulti((Component component, Consumer<T> consumer) -> {
            if (!componentInterface.isInstance(component)) {
                throw new InternalExpressionException(message.apply(component));
            }
            consumer.accept(componentInterface.cast(component));
        }).toList();
    }
}
