package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class Pixelate extends EffectBase {
    int radius, diameter, outWidth, outHeight;
    
    public Pixelate (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
    }

    public void SetOptions() {
        radius = Integer.parseInt(opts[0]);
        diameter = (2 * radius) + 1;
        outWidth = img.getWidth() - (img.getWidth() % diameter);
        outHeight = img.getHeight() - (img.getHeight() % diameter);
        output = new BufferedImage(outWidth, outHeight, img.getType());

        iterateHeight = outHeight - radius;
        iterateWidth = outWidth - radius;
        xIterate = diameter;
        yIterate = diameter;
        
        xStart = radius;
        yStart = radius;
    }

    public void Edit (int x, int y) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int total = 0;
        for (int x1 = -radius; x1 <= radius; x1++) {
            for (int y1 = -radius; y1 <= radius; y1++) {
                int pixel = img.getRGB(x + x1, y + y1);
                Color color = new Color(pixel, true);

                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();
                total++;
            }
        }

        red /= total;
        green /= total;
        blue /= total;

        for (int x1 = -radius; x1 <= radius; x1++) {
            for (int y1 = -radius; y1 <= radius; y1++) {
                Color newColor = new Color(red, green ,blue);
                output.setRGB(x + x1, y + y1, newColor.getRGB());
            }
        }
    }
}