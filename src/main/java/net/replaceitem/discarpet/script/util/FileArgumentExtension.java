package net.replaceitem.discarpet.script.util;

import carpet.script.Module;
import org.jspecify.annotations.Nullable;

import java.io.InputStream;

public interface FileArgumentExtension {
    InputStream asInputStream(@Nullable Module module);
}
