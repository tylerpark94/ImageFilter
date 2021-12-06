package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class BoxBlur extends EffectBase {
    int radius;

    public BoxBlur (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        radius = Integer.parseInt(opts[0]);
    }

    public void Edit (int x, int y) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int total = 0;
        for (int x1 = -radius; x1 <= radius; x1++) {
            for (int y1 = -radius; y1 <= radius; y1++) {
                if ((x + x1) < (img.getWidth() - 1) && (x + x1) > 0 && (y + y1) < (img.getHeight() - 1) && (y + y1) > 0) {
                    int pixel = img.getRGB(x + x1, y + y1);
                    Color color = new Color(pixel, true);

                    red += color.getRed();
                    green += color.getGreen();
                    blue += color.getBlue();
                    total++;
                }
            }
        }

        red /= total;
        green /= total;
        blue /= total;

        Color newColor = new Color(red, green ,blue);
        output.setRGB(x, y, newColor.getRGB());
    }
}