package ImageFilter;

import java.lang.Math;
import java.awt.image.BufferedImage;

public class PixelMove {
    BufferedImage img;
    public PixelMove(BufferedImage inputImg, String[] args) {
        img = inputImg;
    }

    public static BufferedImage MoveUp(BufferedImage inputImg, int height) {
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(), inputImg.getType());
        int imgHeight = inputImg.getHeight();

        for (int x = 0; x < inputImg.getWidth(); x++) {
            for (int y = 0; y < imgHeight; y++) {
                Double heigtAddition = 0.5 * -x * height;
                int newy = y + heigtAddition.intValue();

                while (newy < 0) {
                    newy += imgHeight;
                }
                outputImg.setRGB(x, newy % imgHeight, inputImg.getRGB(x, y));
            }
        }

        return outputImg;
    }

    public static BufferedImage VerticalWavy(BufferedImage inputImg, int heightModifer, double widthModifier) {
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(), inputImg.getType());
        int imgHeight = inputImg.getHeight();

        for (int x = 0; x < inputImg.getWidth(); x++) {
            for (int y = 0; y < imgHeight; y++) {
                Double heigtAddition = heightModifer * Math.sin(widthModifier * x);
                outputImg.setRGB(x, (y + heigtAddition.intValue() + imgHeight) % imgHeight, inputImg.getRGB(x, y));
            }
        }

        return outputImg;
    }

    public static BufferedImage HorizontalWavy(BufferedImage inputImg, int heightModifer, double widthModifier) {
        BufferedImage outputImg = new BufferedImage(inputImg.getWidth(), inputImg.getHeight(), inputImg.getType());
        int imgWidth = inputImg.getWidth();

        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < inputImg.getHeight(); y++) {
                Double heigtAddition = heightModifer * Math.sin(widthModifier * y);
                outputImg.setRGB((x + heigtAddition.intValue() + imgWidth) % imgWidth , y, inputImg.getRGB(x, y));
            }
        }

        return outputImg;
    }

    public static BufferedImage TwoDWavy(BufferedImage inputImg, int heightModifer, double widthModifier) {
        BufferedImage img = VerticalWavy(inputImg, heightModifer, widthModifier);
        return HorizontalWavy(img, heightModifer, widthModifier);
    }

    public static BufferedImage Twist(BufferedImage inputImg) {
        int oddWidth = inputImg.getWidth() - 1;
        int oddHeight = inputImg.getHeight() - 1;
        int outputSize = Math.min(oddWidth, oddHeight);
        BufferedImage outputImg = new BufferedImage(outputSize, outputSize, inputImg.getType());

        int midx = oddWidth / 2;
        int midy = oddHeight / 2;

        int xOffset = (oddWidth - outputSize) / 2;
        int yOffset = (oddHeight - outputSize) / 2;

        int iterations = outputSize / 2;

        for(int i = 1; i < iterations; i++) {
            int move = i;
            int minx = midx - i;
            int miny = midy  - i;

            int maxx = midx + i;
            int maxy = midy + i;

            // Moving straight lines
            for (int x = minx; x <= maxx - move; x++) {
                try {
                    outputImg.setRGB(x + move - xOffset, miny - yOffset, inputImg.getRGB(x, miny));
                } catch (Exception e) {
                    System.out.println("x: " + x);
                    System.out.println("move: " + move);
                    System.out.println("total: " + (x + move));
                    System.out.println("maxx: " + maxx);
                    System.out.println("minx: " + minx);
                    System.out.println("outputSize: " + outputSize);
                    System.out.println("iterations: " + iterations);
                }
            }

            for (int y = miny; y <= maxy - move; y++) {
                outputImg.setRGB(maxx - xOffset, y + move - yOffset, inputImg.getRGB(maxx, y));
            }

            for (int x = maxx; x >= minx + move; x--) {
                outputImg.setRGB(x - xOffset - move, maxy - yOffset, inputImg.getRGB(x, maxy));
            }

            for (int y = maxy; y >= miny + move; y--) {
                outputImg.setRGB(minx - xOffset, y - move - yOffset, inputImg.getRGB(minx, y));
            }

            // Moving around the Corners
            for (int x = maxx - move + 1; x < maxx; x++) {
                int yMove = move - (maxx - x);
                outputImg.setRGB(maxx - xOffset, miny + yMove - yOffset, inputImg.getRGB(x, miny));
            }

            for (int y = maxy - move + 1; y < maxy; y++) {
                int xMove = move - (maxy - y);
                outputImg.setRGB(maxx - xMove - xOffset, maxy - yOffset, inputImg.getRGB(maxx, y));
            }

            for (int x = minx + move - 1; x > minx; x--) {
                int yMove = move - (x - minx);
                outputImg.setRGB(minx - xOffset, maxy - yMove - yOffset, inputImg.getRGB(x, maxy));
            }

            for (int y = miny + move - 1; y > miny; y--) {
                int xMove = move - (y - miny);
                outputImg.setRGB(minx + xMove - xOffset, miny - yOffset, inputImg.getRGB(minx, y));
            }
        }

        return outputImg;
    }

    public static BufferedImage Twirl(BufferedImage inputImg, int angle) {
        int oddWidth = inputImg.getWidth() - 1;
        int oddHeight = inputImg.getHeight() - 1;
        int radius = Math.min(oddWidth, oddHeight) / 2;
        double angleRadians = Math.toRadians(angle);

        int radius2 = radius * radius;

        BufferedImage outputImg = new BufferedImage(oddWidth, oddHeight, inputImg.getType());

        int midX = oddWidth / 2;
        int midY = oddHeight / 2;

        for (int x = 0; x < oddWidth; x++) {
            for (int y = 0; y < oddHeight; y++) {
                int dx = x - midX;
                int dy = y - midY;
                double distance = dx * dx + dy* dy;

                double outX = 0;
                double outY = 0;

                if (distance > radius2) {
                    outX = x;
                    outY = y;
                } else {
                    distance = Math.sqrt(distance);
                    double a = Math.atan2(dy, dx) + angleRadians * (radius - distance) / radius;
                    outX = midX + distance * Math.cos(a);
                    outY = midY + distance * Math.sin(a);
                }

                outputImg.setRGB(x, y, inputImg.getRGB((int)outX, (int)outY));
            }
        }

        return outputImg;
    }
}