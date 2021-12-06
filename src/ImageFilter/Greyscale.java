package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class Greyscale extends EffectBase {
    public Greyscale (BufferedImage inputImg) {
        super(inputImg, null);
    }

    public void Edit (int x, int y) {
        int pixel = img.getRGB(x, y);
        Color color = new Color(pixel, true);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int avg = (red + green + blue) / 3;
       
        color = new Color(avg, avg, avg);
        output.setRGB(x, y, color.getRGB());
    }
}