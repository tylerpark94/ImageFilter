package ImageFilter;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class ColorChange {
    BufferedImage img;
    public ColorChange(BufferedImage inputImg, String[] args) {
        img = inputImg;
    }

    public static BufferedImage SwapBG(BufferedImage inputImg) {
        for (int x = 0; x < inputImg.getWidth(); x++) {
            for (int y = 0; y < inputImg.getHeight(); y++) {
                int pixel = inputImg.getRGB(x, y);
                Color color = new Color(pixel, true);
               
                color = new Color(color.getRed(), color.getBlue(), color.getGreen());
                inputImg.setRGB(x, y, color.getRGB());
            }
        }

        return inputImg;
    }

    public static BufferedImage SwapGB(BufferedImage inputImg) {
        return ColorChange.SwapBG(inputImg);
    }

    public static BufferedImage SwapRB(BufferedImage inputImg) {
        for (int x = 0; x < inputImg.getWidth(); x++) {
            for (int y = 0; y < inputImg.getHeight(); y++) {
                int pixel = inputImg.getRGB(x, y);
                Color color = new Color(pixel, true);
               
                color = new Color(color.getBlue(), color.getGreen(), color.getRed());
                inputImg.setRGB(x, y, color.getRGB());
            }
        }

        return inputImg;
    }

    public static BufferedImage SwapBR(BufferedImage inputImg) {
        return ColorChange.SwapRB(inputImg);
    }

    public static BufferedImage SwapGR(BufferedImage inputImg) {
        for (int x = 0; x < inputImg.getWidth(); x++) {
            for (int y = 0; y < inputImg.getHeight(); y++) {
                int pixel = inputImg.getRGB(x, y);
                Color color = new Color(pixel, true);
               
                color = new Color(color.getGreen(), color.getRed(), color.getBlue());
                inputImg.setRGB(x, y, color.getRGB());
            }
        }

        return inputImg;
    }

    public static BufferedImage SwapRG(BufferedImage inputImg) {
        return ColorChange.SwapGR(inputImg);
    }

    public static BufferedImage Greyscale(BufferedImage inputImg) {
        for (int x = 0; x < inputImg.getWidth(); x++) {
            for (int y = 0; y < inputImg.getHeight(); y++) {
                int pixel = inputImg.getRGB(x, y);
                Color color = new Color(pixel, true);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int avg = (red + green + blue) / 3;
               
                color = new Color(avg, avg, color.getBlue());
                inputImg.setRGB(x, y, color.getRGB());
            }
        }

        return inputImg;
    }
}
