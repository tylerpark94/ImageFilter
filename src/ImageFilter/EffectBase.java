package ImageFilter;

import java.awt.image.BufferedImage;

public abstract class EffectBase {
    BufferedImage img, output;
    int iterateWidth, 
        iterateHeight, 
        xIterate = 1, 
        yIterate = 1,
        xStart = 0,
        yStart = 0;
    String[] opts;
    
    public EffectBase (BufferedImage inputImg, String[] args) {
        img = inputImg;
        opts = args;
        this.SetOptions();
    }

    public void SetOptions() {
        output = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        iterateHeight = img.getHeight();
        iterateWidth = img.getWidth();
    }

    public BufferedImage CreateEffect() {
        for (int x = xStart; x < iterateWidth; x += xIterate) {
            for (int y = yStart; y < iterateHeight; y += yIterate) {
                Edit(x, y);
            }
        }

        return output;
    }

    public abstract void Edit(int x, int y);
}