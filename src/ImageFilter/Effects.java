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

    public static BufferedImage GaussianBlur(BufferedImage inputImg, int radius) {
        int height = inputImg.getHeight();
        int width = inputImg.getWidth();
        BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());

        double sigma = Math.max(radius / 2, 1);
        int kernalWidth = 2 * radius + 1;

        Double[][] kernal = new Double[kernalWidth][kernalWidth];

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                int expNumerator = -(x * x + y * y);
                Double expDenominator = 2 * sigma * sigma;

                Double kernalValue = Math.exp(expNumerator / expDenominator);
                kernal[x + radius][y + radius] = kernalValue;
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int red = 0;
                int green = 0;
                int blue = 0;
                Double kernalTotal = 0.0;
                for (int x1 = -radius; x1 <= radius; x1++) {
                    for (int y1 = -radius; y1 <= radius; y1++) {
                        if ((x + x1) < (width - 1) && (x + x1) > 0 && (y + y1) < (height - 1) && (y + y1) > 0) {
                            int pixel = inputImg.getRGB(x + x1, y + y1);
                            Color color = new Color(pixel, true);
                            Double kernalValue = kernal[x1 + radius][y1 + radius];

                            red += color.getRed() * kernalValue;
                            green += color.getGreen() * kernalValue;
                            blue += color.getBlue() * kernalValue;
                            kernalTotal += kernalValue;
                        }
                    }
                }

                red /= kernalTotal;
                green /= kernalTotal;
                blue /= kernalTotal;

                Color newColor = new Color(red, green, blue);
                outputImg.setRGB(x, y, newColor.getRGB());
            }
        }

        return outputImg;
    }

    public static BufferedImage SmartGaussianBlur(BufferedImage inputImg, int radius, int threshold) {
        int height = inputImg.getHeight();
        int width = inputImg.getWidth();
        BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());

        double sigma = Math.max(radius / 2, 1);
        int kernalWidth = 2 * radius + 1;

        Double[][] kernal = new Double[kernalWidth][kernalWidth];

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                int expNumerator = -(x * x + y * y);
                Double expDenominator = 2 * sigma * sigma;

                Double kernalValue = Math.exp(expNumerator / expDenominator);
                kernal[x + radius][y + radius] = kernalValue;
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int refPixel = inputImg.getRGB(x, y);
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
                        if ((x + x1) < (width - 1) && (x + x1) > 0 && (y + y1) < (height - 1) && (y + y1) > 0) {
                            int pixel = inputImg.getRGB(x + x1, y + y1);
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
                outputImg.setRGB(x, y, newColor.getRGB());
            }
        }

        return outputImg;
    }

    public static BufferedImage MaximumGaussianBlur(BufferedImage inputImg, int radius) {
        int height = inputImg.getHeight();
        int width = inputImg.getWidth();
        BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int red = 0;
                int green = 0;
                int blue = 0;
            
                for (int x1 = -radius; x1 <= radius; x1++) {
                    for (int y1 = -radius; y1 <= radius; y1++) {
                        if ((x + x1) < (width - 1) && (x + x1) > 0 && (y + y1) < (height - 1) && (y + y1) > 0) {
                            int pixel = inputImg.getRGB(x + x1, y + y1);
                            Color color = new Color(pixel, true);
                            int red1 = color.getRed();
                            int green1 = color.getGreen();
                            int blue1 = color.getBlue();

                            if (red1 > red) {
                                red = red1;
                            }

                            if (green1 > green) {
                                green = green1;
                            }

                            if (blue1 > blue) {
                                blue = blue1;
                            }
                        }
                    }
                }
                Color newColor = new Color((int)red, (int)green, (int)blue);
                outputImg.setRGB(x, y, newColor.getRGB());
            }
        }

        return outputImg;
    }

    public static BufferedImage OilEffect(BufferedImage inputImg, int radius, int levels) {
        int height = inputImg.getHeight();
        int width = inputImg.getWidth();
        BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());
        int[] rHistogram = new int[levels];
		int[] gHistogram = new int[levels];
		int[] bHistogram = new int[levels];
		int[] rTotal = new int[levels];
		int[] gTotal = new int[levels];
		int[] bTotal = new int[levels];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int i = 0; i < levels; i++) {
				    rHistogram[i] = gHistogram[i] = bHistogram[i] = rTotal[i] = gTotal[i] = bTotal[i] = 0;
                }
            
                for (int x1 = -radius; x1 <= radius; x1++) {
                    for (int y1 = -radius; y1 <= radius; y1++) {
                        if ((x + x1) < (width - 1) && (x + x1) > 0 && (y + y1) < (height - 1) && (y + y1) > 0) {
                            int pixel = inputImg.getRGB(x + x1, y + y1);
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
                outputImg.setRGB(x, y, newColor.getRGB());
            }
        }

        return outputImg;
    }

    public static BufferedImage GlowEffect(BufferedImage inputImg, int radius, double amount) {
        int height = inputImg.getHeight();
        int width = inputImg.getWidth();
        BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());
        BufferedImage blurredImg = GaussianBlur(inputImg, radius);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel1 = inputImg.getRGB(x, y);
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
                outputImg.setRGB(x, y, newColor.getRGB());
            }
        }

        return outputImg;
    }
}
