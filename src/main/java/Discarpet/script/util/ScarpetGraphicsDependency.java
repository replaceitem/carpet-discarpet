package Discarpet.script.util;

import carpet.script.value.Value;
import scarpet.graphics.script.values.PixelAccessibleValue;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScarpetGraphicsDependency {
    public static boolean isPixelAccessible(Value value) {
        return value instanceof PixelAccessibleValue;
    }

    public static BufferedImage getImageFromValue(Value value) {
        if(!(value instanceof PixelAccessibleValue pixelAccessibleValue)) return null;
        Image image = pixelAccessibleValue.getNativeImage();
        return image instanceof BufferedImage bufferedImage ? bufferedImage : null;
    }
}
