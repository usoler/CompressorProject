package presentation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CompareCanvas extends Canvas {
    private BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void paint(Graphics graphics) {
        graphics.drawImage(image, 0, 0, null);
    }
}
