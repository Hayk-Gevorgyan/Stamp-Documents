package org.stamp.java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StampBuilder {
    private BufferedImage image;
    private String signature;
    public static final String BLANK_STAMP = "src\\main\\resources\\emptyStampExampleNoBG.png";
    public static final String STAMP = "src\\main\\resources\\stamp.png";
    public static final int STAMP_WIDTH = 150;
    public static final int STAMP_HEIGHT = 150;
    public static final int FONT_SIZE = 35;
    private static final Scanner scanner = new Scanner(System.in);
    public void setImage() {
        File inputImageFile = new File(BLANK_STAMP);
        this.image = new BufferedImage(STAMP_WIDTH,STAMP_HEIGHT,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        try {
            g2.drawImage(ImageIO.read(inputImageFile), 0, 0, STAMP_WIDTH, STAMP_HEIGHT, null);
        } catch (IOException e) {
            image = null;
        }
        g2.dispose();
    }
    public void setSignature() {
        System.out.println("Enter a new signature");
        String signature = scanner.nextLine();
        while (signature.length() > 3) {
            System.out.println("Enter a new signature");
            signature = scanner.nextLine();
        }
        this.signature = signature;
    }
    public void Build() throws RuntimeException {
        if (image != null && signature != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(signature);
            int textHeight = fm.getHeight();

            int centerX = (image.getWidth() - textWidth) / 2;
            int centerY = (image.getHeight() - textHeight) / 2 + fm.getAscent();

            g2d.drawString(signature, centerX, centerY);
            g2d.dispose();

            File outputImageFile = new File(STAMP);
            try {
                ImageIO.write(image, "png", outputImageFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            while (image == null) {
                setImage();
            }
            if (signature == null) {
                setSignature();
            }
        }
    }
}
