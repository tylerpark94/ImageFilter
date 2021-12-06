package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class SwapBG extends EffectBase {
    public SwapBG (BufferedImage inputImg) {
        super(inputImg, null);
    }

    public void Edit (int x, int y) {
        int pixel = img.getRGB(x, y);
        Color color = new Color(pixel, true);
        
        color = new Color(color.getRed(), color.getBlue(), color.getGreen());
        output.setRGB(x, y, color.getRGB());
    }
}