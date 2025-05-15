package net.replaceitem.discarpet.script.util;

import carpet.script.Module;

import javax.annotation.Nullable;
import java.io.InputStream;

public interface FileArgumentExtension {
    InputStream asInputStream(@Nullable Module module);
}
