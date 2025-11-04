package ImageFilter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoxRotate extends EffectBase {
    int boxSize;
    int numBoxes;
    int boxesPerRow;
    List<Integer> directions = new ArrayList();
    /*
     * 0 -> nothing
     * 1 -> 90 Clockwise
     * 2 -> 180 Clockwise
     * 3 -> 270 Clockwise
     */
    
    public BoxRotate (BufferedImage inputImg, String args[]) {
        super(inputImg, args);
        boxSize = Integer.parseInt(opts[0]);
        numBoxes = (int) Math.ceil((double) inputImg.getWidth() / boxSize) * (int) Math.ceil((double) inputImg.getHeight() / boxSize);
        boxesPerRow = (int) Math.ceil((double) inputImg.getWidth() / boxSize);

        System.out.println("boxSize" + boxSize);
        System.out.println("numBoxes" + numBoxes);
        System.out.println("boxesPerRow" + boxesPerRow);

        Random random = new Random();

        for (int i = 0; i < numBoxes; i++) {
            directions.add(random.nextInt(4));
        }
    }

    public void Edit (int x, int y) {
        output.setRGB(x, y, determineOriginRGB(x, y));
    }

    private int determineOriginRGB(int x, int y) {
        int boxNumber = determineBoxNumber(x, y);
        // System.out.println("boxNumber" + boxNumber);

        int direction = directions.get(boxNumber - 1);

        switch(direction) {
            case 0: 
                return img.getRGB(x, y);
                // return Color.BLACK.getRGB();
            case 1:
                return get90DegreeRGB(x, y);
            case 2:
                return get180DegreeRGB(x, y);
            case 3:
                return get270DegreeRGB(x, y);
        }
        return img.getRGB(x, y);
    }

private int get270DegreeRGB(int x, int y) {
    int xBox = x / boxSize;
    int yBox = y / boxSize;
    int xBoxCoord = x % boxSize;
    int yBoxCoord = y % boxSize;

    double quadrantSize = boxSize / 2.0;

    double tempX = xBoxCoord - quadrantSize;
    double tempY = yBoxCoord - quadrantSize;

    int newXCoord = (int) (tempY + quadrantSize);
    int newYCoord = (int) (-tempX + quadrantSize);

    // Clamp to valid range
    newXCoord = Math.min(Math.max(newXCoord, 0), boxSize - 1);
    newYCoord = Math.min(Math.max(newYCoord, 0), boxSize - 1);

    int newX = xBox * boxSize + newXCoord;
    int newY = yBox * boxSize + newYCoord;

    // Make sure we're not out of bounds
    if (newX < 0 || newY < 0 || newX >= img.getWidth() || newY >= img.getHeight()) {
        return 0; // or some fallback color
    }

    return img.getRGB(newX, newY);
    }

private int get180DegreeRGB(int x, int y) {
    int xBox = x / boxSize;
    int yBox = y / boxSize;
    int xBoxCoord = x % boxSize;
    int yBoxCoord = y % boxSize;

    double quadrantSize = boxSize / 2.0;

    double tempX = xBoxCoord - quadrantSize;
    double tempY = yBoxCoord - quadrantSize;

    int newXCoord = (int) (-tempY + quadrantSize);
    int newYCoord = (int) (-tempX + quadrantSize);

    // Clamp to valid range
    newXCoord = Math.min(Math.max(newXCoord, 0), boxSize - 1);
    newYCoord = Math.min(Math.max(newYCoord, 0), boxSize - 1);

    int newX = xBox * boxSize + newXCoord;
    int newY = yBox * boxSize + newYCoord;

    // Make sure we're not out of bounds
    if (newX < 0 || newY < 0 || newX >= img.getWidth() || newY >= img.getHeight()) {
        return 0; // or some fallback color
    }

    return img.getRGB(newX, newY);
    }

private int get90DegreeRGB(int x, int y) {
    int xBox = x / boxSize;
    int yBox = y / boxSize;
    int xBoxCoord = x % boxSize;
    int yBoxCoord = y % boxSize;

    double quadrantSize = boxSize / 2.0;

    double tempX = xBoxCoord - quadrantSize;
    double tempY = yBoxCoord - quadrantSize;

    int newXCoord = (int) (-tempY + quadrantSize);
    int newYCoord = (int) (tempX + quadrantSize);

    // Clamp to valid range
    newXCoord = Math.min(Math.max(newXCoord, 0), boxSize - 1);
    newYCoord = Math.min(Math.max(newYCoord, 0), boxSize - 1);

    int newX = xBox * boxSize + newXCoord;
    int newY = yBox * boxSize + newYCoord;

    // Make sure we're not out of bounds
    if (newX < 0 || newY < 0 || newX >= img.getWidth() || newY >= img.getHeight()) {
        return 0; // or some fallback color
    }

    return img.getRGB(newX, newY);
}

    private int determineBoxNumber(int x, int y) {
        // System.out.println("x" + x);
        // System.out.println("y" + y);

        int xBox = (int) Math.ceil((double) (x + 1) / boxSize);
        int yBox = (int) Math.ceil((double) (y + 1) / boxSize);

        // System.out.println("xBox" + xBox);
        // System.out.println("yBox" + yBox);

        return (yBox - 1) * boxesPerRow + xBox;
    }
}