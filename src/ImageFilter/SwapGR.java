package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class SwapGR extends EffectBase {
    public SwapGR (BufferedImage inputImg) {
        super(inputImg, null);
    }

    public void Edit (int x, int y) {
        int pixel = img.getRGB(x, y);
        Color color = new Color(pixel, true);
       
        color = new Color(color.getGreen(), color.getRed(), color.getBlue());
        output.setRGB(x, y, color.getRGB());
    }
}