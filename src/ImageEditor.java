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
        EffectBase image = null;

        try {
            inputImg = ImageIO.read(new File(inputImgPath));
        } catch (IOException e) {
            System.out.println("ERROR READING FILE");
            System.out.println(e.getMessage());
        }

        switch(option) {
            case "swaprb":
            case "swapbr":
                image = new SwapRB(inputImg);
                outputImg = image.CreateEffect();
                break;
            case "swaprg":
            case "swapgr":
                image = new SwapGR(inputImg);
                outputImg = image.CreateEffect();
                break;
            case "swapgb":
            case "swapbg":
                image = new SwapBG(inputImg);
                outputImg = image.CreateEffect();
                break;
            case "grey":
            case "gray":
                image = new Greyscale(inputImg);
                outputImg = image.CreateEffect();
                break;
            case "moveup":
                String[] temp = {args[i]};
                i++;
                image = new MoveUp(inputImg, temp);
                outputImg = image.CreateEffect();
                break;
            case "vwavy":
                String[] temp2 = {args[i], args[i+1]};
                i++;
                i++;
                image = new VerticalWavy(inputImg, temp2);
                outputImg = image.CreateEffect();
                break;
            case "hwavy":
                String[] temp3 = {args[i], args[i+1]};
                i++;
                i++;
                image = new HorizontalWavy(inputImg, temp3);
                outputImg = image.CreateEffect();
                break;
            case "2dwavy":
                String[] temp4 = {args[i], args[i+1]};
                i++;
                i++;
                image = new VerticalWavy(inputImg, temp4);
                outputImg = image.CreateEffect();
                image = new HorizontalWavy(outputImg, temp4);
                outputImg = image.CreateEffect();
                break;
            case "pixelate":
                String[] temp6 = {args[i]};
                i++;
                image = new Pixelate(inputImg, temp6);
                outputImg = image.CreateEffect();
                break;
            case "boxblur":
                String[] temp7 = {args[i]};
                i++;
                image = new BoxBlur(inputImg, temp7);
                outputImg = image.CreateEffect();
                break;
            case "gblur": 
                String[] temp8 = {args[i]};
                i++;
                image = new GaussianBlur(inputImg, temp8);
                outputImg = image.CreateEffect();
                break;
            case "sgblur":
                String[] temp9 = {args[i], args[i+1]};
                i++;
                i++;
                image = new SmartGaussianBlur(inputImg, temp9);
                outputImg = image.CreateEffect();
                break;
            case "maxblur":
                String[] temp10 = {args[i]};
                i++;
                image = new MaxBlur(inputImg, temp10);
                outputImg = image.CreateEffect();
                break;
            case "oil":
                String[] temp11 = {args[i], args[i+1]};
                i++;
                i++;
                image = new OilBlur(inputImg, temp11);
                outputImg = image.CreateEffect();
                break;
            case "glow": 
                String[] temp12 = {args[i], args[i+1]};
                i++;
                i++;
                image = new Glow(inputImg, temp12);
                outputImg = image.CreateEffect();
                break;
            case "twirl":
                String[] temp5 = {args[i]};
                i++;
                image = new Twirl(inputImg, temp5);
                outputImg = image.CreateEffect();
                break;
            default:
                System.out.println("Invalid option");
                return;
        }

        try {
            String outputPath = args[i];
            String ext = outputPath.substring(outputPath.length() - 3);
            String outputExtension = ext.equals("png") ? "png" : "jpg";
            File outputFile = new File(outputPath);
            ImageIO.write(outputImg, outputExtension, outputFile);
        } catch (IOException e) {
            System.out.println("ERROR SAVING");
            System.out.println(e.getMessage());
        }
    }
}
