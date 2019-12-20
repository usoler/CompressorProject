package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CompareCanvas extends Canvas {
    private BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
        setSize(Math.max(image.getWidth(), getWidth()), Math.max(image.getHeight(), getHeight()));
    }

    public void paint(Graphics graphics) {
        graphics.drawImage(image, 0, 0, getWidth() , image.getHeight()*getWidth()/image.getWidth(), null);
    }
}
