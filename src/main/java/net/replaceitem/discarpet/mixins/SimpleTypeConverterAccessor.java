package net.replaceitem.discarpet.mixins;

import carpet.script.annotation.SimpleTypeConverter;
import carpet.script.value.Value;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = SimpleTypeConverter.class, remap = false)
public interface SimpleTypeConverterAccessor<T extends Value, R> {
    @Invoker
    static <R> SimpleTypeConverter<Value, R> callGet(Class<R> outputType) {
        throw new UnsupportedOperationException();
    }
}
