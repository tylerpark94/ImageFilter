import java.io.File;
import java.io.IOException;
import ImageFilter.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageEditor {
    public static void main(String[] args) {
        int i = 0;
        String inputImgPath = args[i];
        i++;
        String option = args[i];
        i++;

        BufferedImage inputImg = null;
        BufferedImage outputImg = null;

        try {
            inputImg = ImageIO.read(new File(inputImgPath));
        } catch (IOException e) {
            System.out.println("ERROR READING FILE");
            System.out.println(e.getMessage());
        }

        switch(option) {
            case "swaprb":
            case "swapbr": 
                outputImg = ColorChange.SwapRB(inputImg);
                break;
            case "swaprg":
            case "swapgr":
                outputImg = ColorChange.SwapRG(inputImg);
                break;
            case "swapgb":
            case "swapbg":
                outputImg = ColorChange.SwapBG(inputImg);
                break;
            case "grey":
            case "gray":
                outputImg = ColorChange.Greyscale(inputImg);
                break;
            case "moveup":
                int amount = Integer.parseInt(args[i]);
                i++;
                outputImg = PixelMove.MoveUp(inputImg, amount);
                break;
            case "vwavy":
                int vHeight = Integer.parseInt(args[i]);
                i++;
                double vWidth = Double.parseDouble(args[i]);
                i++;
                outputImg = PixelMove.VerticalWavy(inputImg, vHeight, vWidth);
                break;
            case "hwavy":
                int hHeight = Integer.parseInt(args[i]);
                i++;
                double hWidth = Double.parseDouble(args[i]);
                i++;
                outputImg = PixelMove.HorizontalWavy(inputImg, hHeight, hWidth);
                break;
            case "2dwavy":
                int twoDHeight = Integer.parseInt(args[i]);
                i++;
                double twoDWidth = Double.parseDouble(args[i]);
                i++;
                outputImg = PixelMove.TwoDWavy(inputImg, twoDHeight, twoDWidth);
                break;
            case "pixelate":
                int radius = Integer.parseInt(args[i]);
                i++;
                outputImg = Effects.pixelate(inputImg, radius);
                break;
            case "twist":
                outputImg = PixelMove.Twist(inputImg);
                break;
            case "boxblur":
                int w = Integer.parseInt(args[i]);
                i++;
                outputImg = Effects.BoxBlur(inputImg, w);
                break;
            case "gblur": 
                int r = Integer.parseInt(args[i]);
                i++;
                outputImg = Effects.GaussianBlur(inputImg, r);
                break;
            case "sgblur": 
                int rad = Integer.parseInt(args[i]);
                i++;
                int t = Integer.parseInt(args[i]);
                i++;
                outputImg = Effects.SmartGaussianBlur(inputImg, rad, t);
                break;
            case "maxblur":
                int width = Integer.parseInt(args[i]);
                i++;
                outputImg = Effects.MaximumGaussianBlur(inputImg, width);
                break;
            case "oil":
                int j = Integer.parseInt(args[i]);
                i++;
                int levels = Integer.parseInt(args[i]);
                i++;
                outputImg = Effects.OilEffect(inputImg, j, levels);
                break;
            case "glow": 
                int p = Integer.parseInt(args[i]);
                i++;
                double glow = Double.parseDouble(args[i]);
                i++;
                outputImg = Effects.GlowEffect(inputImg, p, glow);
                break;
            case "twirl":
                int a = Integer.parseInt(args[i]);
                i++;
                outputImg = PixelMove.Twirl(inputImg, a);
                break;
            default:
                System.out.println("Invalid option");
                return;
        }

        try {
            String outputPath = args[i];
            File outputFile = new File(outputPath);
            ImageIO.write(outputImg, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println("ERROR SAVING");
        }
    }
}
