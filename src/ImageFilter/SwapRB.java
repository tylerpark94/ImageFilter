package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class SwapRB extends EffectBase {
    public SwapRB (BufferedImage inputImg) {
        super(inputImg, null);
    }

    public void Edit (int x, int y) {
        int pixel = img.getRGB(x, y);
        Color color = new Color(pixel, true);
       
        color = new Color(color.getBlue(), color.getGreen(), color.getRed());
        output.setRGB(x, y, color.getRGB());
    }
}