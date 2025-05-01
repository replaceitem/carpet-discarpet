package net.replaceitem.discarpet.script.util;

import carpet.script.value.Value;
import org.jetbrains.annotations.Nullable;
import scarpet.graphics.script.values.PixelAccessibleValue;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScarpetGraphicsDependency {
    public static boolean isPixelAccessible(Value value) {
        return value instanceof PixelAccessibleValue;
    }

    public static @Nullable BufferedImage getImageFromValue(Value value) {
        //noinspection rawtypes
        if(!(value instanceof PixelAccessibleValue pixelAccessibleValue)) return null;
        Image image = pixelAccessibleValue.getNativeImage();
        return image instanceof BufferedImage bufferedImage ? bufferedImage : null;
    }
}
