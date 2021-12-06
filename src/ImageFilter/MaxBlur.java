package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class MaxBlur extends EffectBase {
    int radius;

    public MaxBlur (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        radius = Integer.parseInt(opts[0]);
    }

    public void Edit (int x, int y) {
        int red = 0;
        int green = 0;
        int blue = 0;
    
        for (int x1 = -radius; x1 <= radius; x1++) {
            for (int y1 = -radius; y1 <= radius; y1++) {
                if ((x + x1) < (img.getWidth() - 1) && (x + x1) > 0 && (y + y1) < (img.getHeight() - 1) && (y + y1) > 0) {
                    int pixel = img.getRGB(x + x1, y + y1);
                    Color color = new Color(pixel, true);
                    int red1 = color.getRed();
                    int green1 = color.getGreen();
                    int blue1 = color.getBlue();

                    if (red1 > red) {
                        red = red1;
                    }

                    if (green1 > green) {
                        green = green1;
                    }

                    if (blue1 > blue) {
                        blue = blue1;
                    }
                }
            }
        }
        Color newColor = new Color((int)red, (int)green, (int)blue);
        output.setRGB(x, y, newColor.getRGB());
    }
}