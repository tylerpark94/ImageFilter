package ImageFilter;

import java.awt.image.BufferedImage;

public class VerticalWavy extends EffectBase {
    int heightModifer;
    double widthModifier;
    
    public VerticalWavy (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        heightModifer = Integer.parseInt(opts[0]);
        widthModifier = Double.parseDouble(opts[1]);
    }

    public void Edit (int x, int y) {
        Double heigtAddition = heightModifer * Math.sin(widthModifier * x);
        output.setRGB(x, (y + heigtAddition.intValue() + img.getHeight()) % img.getHeight(), img.getRGB(x, y));
    }
}