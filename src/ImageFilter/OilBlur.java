package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class OilBlur extends EffectBase {
    int radius, levels;
    int[] rHistogram, gHistogram, bHistogram, rTotal, gTotal, bTotal;

    public OilBlur (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        radius = Integer.parseInt(opts[0]);
        levels = Integer.parseInt(opts[1]);
        this.CreateHistorams();
    }

    private void CreateHistorams() {
        rHistogram = new int[levels];
		gHistogram = new int[levels];
		bHistogram = new int[levels];
		rTotal = new int[levels];
		gTotal = new int[levels];
		bTotal = new int[levels];
    }

    public void Edit (int x, int y) {
        for (int i = 0; i < levels; i++) {
            rHistogram[i] = gHistogram[i] = bHistogram[i] = rTotal[i] = gTotal[i] = bTotal[i] = 0;
        }
    
        for (int x1 = -radius; x1 <= radius; x1++) {
            for (int y1 = -radius; y1 <= radius; y1++) {
                if ((x + x1) < (img.getWidth() - 1) && (x + x1) > 0 && (y + y1) < (img.getHeight() - 1) && (y + y1) > 0) {
                    int pixel = img.getRGB(x + x1, y + y1);
                    Color color = new Color(pixel, true);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();

                    int red1 = red * levels / 256;
                    int green1 = green * levels / 256;
                    int blue1 = blue * levels / 256;

                    rTotal[red1] += red;
                    gTotal[green1] += green;
                    bTotal[blue1] += blue;
                    rHistogram[red1]++;
                    gHistogram[green1]++;
                    bHistogram[blue1]++;

                }
            }
        }
        int red = 0;
        int green = 0;
        int blue = 0;

        for (int i = 1; i < levels; i++) {
            if (rHistogram[i] > rHistogram[red])
                red = i;
            if (gHistogram[i] > gHistogram[green])
                green = i;
            if (bHistogram[i] > bHistogram[blue])
                blue = i;
        }

        red = rTotal[red] / rHistogram[red];
        green = gTotal[green] / gHistogram[green];
        blue = bTotal[blue] / bHistogram[blue];

        Color newColor = new Color(red, green, blue);
        output.setRGB(x, y, newColor.getRGB());
    }
}