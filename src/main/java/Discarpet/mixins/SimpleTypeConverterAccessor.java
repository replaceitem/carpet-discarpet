package Discarpet.mixins;

import carpet.script.annotation.SimpleTypeConverter;
import carpet.script.value.Value;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SimpleTypeConverter.class)
public interface SimpleTypeConverterAccessor {
    @Invoker
    static <R> SimpleTypeConverter<Value, R> callGet(Class<R> outputType) {
        throw new UnsupportedOperationException();
    }
}
