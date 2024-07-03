package ru.nsu.fit.filters;

import java.awt.image.BufferedImage;
import ru.nsu.fit.parametersFrame.*;
import java.util.List;

public class BadFilter implements Filter{
    @Override
    public BufferedImage execute(BufferedImage originalImage) {
        BufferedImage result = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int rgb = originalImage.getRGB(x, y);
                float R = (float)((rgb & 0x00FF0000) >> 16); // красный
                float G = (float)((rgb & 0x0000FF00) >> 8); // зеленый
                float B = (float)(rgb & 0x000000FF); // синий
                int newPixel = 0xFF000000 | ((int)(R*0.937) << 16) | ((int)(G*0.784) << 8) | ((int)(B*0.772));
                result.setRGB(x, y,newPixel);
            }
        }
        return result;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
