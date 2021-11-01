package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class Effects {
    BufferedImage img;
    public Effects(BufferedImage inputImg, String[] args) {
        img = inputImg;
    }

    public static BufferedImage pixelate(BufferedImage inputImg, int radius) {
        int diameter = (2 * radius) + 1;
        int outputWidth = inputImg.getWidth() - (inputImg.getWidth() % diameter);
        int outputHeight = inputImg.getHeight() - (inputImg.getHeight() % diameter);
        BufferedImage outputImg = new BufferedImage(outputWidth, outputHeight, inputImg.getType());

        for (int x = radius; x <= outputWidth - radius; x += diameter) {
            for (int y = radius; y <= outputHeight - radius; y  += diameter) {
                int red = 0;
                int green = 0;
                int blue = 0;
                int total = 0;
                for (int x1 = -radius; x1 <= radius; x1++) {
                    for (int y1 = -radius; y1 <= radius; y1++) {
                        int pixel = inputImg.getRGB(x + x1, y + y1);
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
                        outputImg.setRGB(x + x1, y + y1, newColor.getRGB());
                    }
                }
            }
        }

        return outputImg;
    }

    public static BufferedImage BoxBlur(BufferedImage inputImg, int radius) {
        int height = inputImg.getHeight();
        int width = inputImg.getWidth();
        BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int red = 0;
                int green = 0;
                int blue = 0;
                int total =0;
                for (int x1 = -radius; x1 <= radius; x1++) {
                    for (int y1 = -radius; y1 <= radius; y1++) {
                        if ((x + x1) < (width - 1) && (x + x1) > 0 && (y + y1) < (height - 1) && (y + y1) > 0) {
                            int pixel = inputImg.getRGB(x + x1, y + y1);
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
                outputImg.setRGB(x, y, newColor.getRGB());
            }
        }

        return outputImg;
    }
}
