package net.replaceitem.discarpet.mixins;

import carpet.script.CarpetScriptServer;
import carpet.script.Module;
import carpet.script.argument.FileArgument;
import carpet.script.exception.ThrowStatement;
import carpet.script.exception.Throwables;
import net.replaceitem.discarpet.script.util.FileArgumentExtension;
import net.replaceitem.discarpet.util.StreamableByteArrayOutputStream;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Mixin(value = FileArgument.class, remap = false)
public abstract class FileArgumentMixin implements FileArgumentExtension {

    @Shadow @Final public static Object writeIOSync;
    @Shadow @Nullable protected abstract Path toPath(@Nullable Module module);
    @Shadow public abstract void close();

    @SuppressWarnings("SynchronizeOnNonFinalField")
    @Unique
    @Override
    @NonNull
    public InputStream asInputStream(@Nullable Module module) {
        try {
            synchronized (writeIOSync) {
                Path dataFile = toPath(module);
                if (dataFile == null || !Files.exists(dataFile)) throw new ThrowStatement("Failed to read file " + dataFile, Throwables.IO_EXCEPTION);

                try (InputStream is = Files.newInputStream(dataFile)) {
                    try (StreamableByteArrayOutputStream byteArrayOutputStream = new StreamableByteArrayOutputStream()) {
                        is.transferTo(byteArrayOutputStream);
                        return byteArrayOutputStream.createInputStream();
                    }
                } catch (IOException e) {
                    CarpetScriptServer.LOG.warn("IOException when reading file", e);
                    throw new ThrowStatement("Failed to read file " + dataFile, Throwables.IO_EXCEPTION);
                }
            }
        } finally {
            this.close();
        }
    }
}
