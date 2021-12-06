package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class SmartGaussianBlur extends EffectBase {
    int radius, kernalWidth, threshold;
    double sigma;
    Double[][] kernal;

    public SmartGaussianBlur (BufferedImage inputImg, String args[]) {
        super(inputImg, args);

        radius = Integer.parseInt(opts[0]);
        threshold = Integer.parseInt(opts[1]);

        this.CreateKernal();
    }

    private void CreateKernal() {
        sigma = Math.max(radius / 2, 1);
        kernalWidth = 2 * radius + 1;

        kernal = new Double[kernalWidth][kernalWidth];

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                int expNumerator = -(x * x + y * y);
                Double expDenominator = 2 * sigma * sigma;

                Double kernalValue = Math.exp(expNumerator / expDenominator);
                kernal[x + radius][y + radius] = kernalValue;
            }
        }
    }

    public void Edit (int x, int y) {
        int refPixel = img.getRGB(x, y);
        Color refColor = new Color(refPixel, true);
        int refRed = refColor.getRed();
        int refGreen = refColor.getGreen();
        int refBlue = refColor.getBlue();

        double red = 0;
        double green = 0;
        double blue = 0;

        Double redKernalTotal = 0.0;
        Double greenKernalTotal = 0.0;
        Double blueKernalTotal = 0.0;
    
        for (int x1 = -radius; x1 <= radius; x1++) {
            for (int y1 = -radius; y1 <= radius; y1++) {
                if ((x + x1) < (img.getWidth() - 1) && (x + x1) > 0 && (y + y1) < (img.getHeight() - 1) && (y + y1) > 0) {
                    int pixel = img.getRGB(x + x1, y + y1);
                    Color color = new Color(pixel, true);
                    Double kernalValue = kernal[x1 + radius][y1 + radius];
                    int red1 = color.getRed();
                    int green1 = color.getGreen();
                    int blue1 = color.getBlue();

                    int diff;

                    diff = refRed - red1;
                    if (diff >= -threshold && diff <= threshold) {
                        red += red1 * kernalValue;
                        redKernalTotal += kernalValue;
                    }

                    diff = refGreen - green1;
                    if (diff >= -threshold && diff <= threshold) {
                        green += green1 * kernalValue;
                        greenKernalTotal += kernalValue;
                    }

                    diff = refBlue - blue1;
                    if (diff >= -threshold && diff <= threshold) {
                        blue += blue1 * kernalValue;
                        blueKernalTotal += kernalValue;
                    }
                }
            }
        }

        red = redKernalTotal == 0.0 ? refRed : red / redKernalTotal;
        green = greenKernalTotal == 0.0 ? refGreen : green / greenKernalTotal;
        blue = blueKernalTotal == 0.0 ? refBlue : blue / blueKernalTotal;

        Color newColor = new Color((int)red, (int)green, (int)blue);
        output.setRGB(x, y, newColor.getRGB());
    }
}