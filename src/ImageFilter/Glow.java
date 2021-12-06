package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class Glow extends EffectBase {
    int radius;
    double amount;
    int[] rHistogram, gHistogram, bHistogram, rTotal, gTotal, bTotal;
    BufferedImage blurredImg;

    public Glow (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        radius = Integer.parseInt(opts[0]);
        amount = Double.parseDouble(opts[1]);
        EffectBase temp = new GaussianBlur(inputImg, args);
        blurredImg = temp.CreateEffect();
    }

    public void Edit (int x, int y) {
        int pixel1 = img.getRGB(x, y);
        Color color1 = new Color(pixel1, true);
        int red1 = color1.getRed();
        int green1 = color1.getGreen();
        int blue1 = color1.getBlue();

        int pixel2 = blurredImg.getRGB(x, y);
        Color color2 = new Color(pixel2, true);
        int red2 = color2.getRed();
        int green2 = color2.getGreen();
        int blue2 = color2.getBlue();

        double a = 4 * amount;
        
        double red = red1 + a * red2;
        double green = green1 + a * green2;
        double blue = blue1 + a * blue2;

        if (red > 255) {
            red = 255;
        } else if (red < 0) {
            red = 0;
        }

        if (green > 255) {
            green = 255;
        } else if (green < 0) {
            green = 0;
        }

        if (blue > 255) {
            blue = 255;
        } else if (blue < 0) {
            blue = 0;
        }

        Color newColor = new Color((int)red, (int)green, (int)blue);
        output.setRGB(x, y, newColor.getRGB());
    }
}