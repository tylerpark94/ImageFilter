package ImageFilter;

import java.awt.image.BufferedImage;

public class HorizontalWavy extends EffectBase {
    int heightModifer;
    double widthModifier;
    
    public HorizontalWavy (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        heightModifer = Integer.parseInt(opts[0]);
        widthModifier = Double.parseDouble(opts[1]);
    }

    public void Edit (int x, int y) {
        Double heigtAddition = heightModifer * Math.sin(widthModifier * y);
        output.setRGB((x + heigtAddition.intValue() + img.getWidth()) % img.getWidth() , y, img.getRGB(x, y));
    }
}