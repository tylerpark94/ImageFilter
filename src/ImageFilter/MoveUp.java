package ImageFilter;

import java.awt.image.BufferedImage;

public class MoveUp extends EffectBase {
    int height;
    public MoveUp (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        height = Integer.parseInt(opts[0]);
    }

    public void Edit (int x, int y) {
        int heigtAddition =  -x * height;
        int newy = y + heigtAddition;

        while (newy < 0) {
            newy += img.getHeight();
        }
        output.setRGB(x, newy % img.getHeight(), img.getRGB(x, y));
    }
}