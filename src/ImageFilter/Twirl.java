package ImageFilter;

import java.awt.image.BufferedImage;

public class Twirl extends EffectBase {
    int angle, radius, radius2, midX, midY;
    double angleRadians;

    public Twirl (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        angle = Integer.parseInt(opts[0]);

        radius = Math.min(img.getWidth(), img.getHeight()) / 2;
        angleRadians = Math.toRadians(angle);
        radius2 = radius * radius;
        midX = img.getWidth() / 2;
        midY = img.getHeight() / 2;

    }

    public void Edit (int x, int y) {
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

        output.setRGB(x, y, img.getRGB((int)outX, (int)outY));
    }
}