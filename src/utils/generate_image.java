package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The type Generate image.
 */
public class generate_image {
    /**
     * Create usage image with text buffered image.
     *
     * @param usage the usage
     * @return the buffered image
     */
    public static BufferedImage createUsageImageWithText(double usage) {
        // Set the font and font size
        Font font = new Font("Arial", Font.BOLD, 32);
        int fontSize = 12;

        // Set the text color based on the value of temp
        Color textColor;
        if (usage < 30) {
            textColor = Color.GREEN;
        } else if (usage < 70) {
            textColor = Color.YELLOW;
        } else {
            textColor = Color.RED;
        }

        return generateImage(round(usage), font, textColor,fontSize);
    }

    /**
     * Create temp image with text buffered image.
     *
     * @param temp the temp
     * @return the buffered image
     */
    public static BufferedImage createTempImageWithText(double temp) {
        // Set the font and font size
        Font font = new Font("Arial", Font.BOLD, 32);
        int fontSize = 12;

        // Set the text color based on the value of temp
        Color textColor;
        if (temp < 50) {
            textColor = Color.GREEN;
        } else if (temp < 70) {
            textColor = Color.YELLOW;
        } else {
            textColor = Color.RED;
        }

        return generateImage(round(temp), font, textColor,fontSize);
    }
    private static BufferedImage generateImage(Double text, Font font, Color textColor, int fontSize) {


        // Calculate the dimensions of the image
        FontMetrics fontMetrics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics().getFontMetrics(font);
        int width = fontMetrics.stringWidth(String.valueOf(text));
        int height = fontMetrics.getHeight();

        // Create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(new Color(0, 0, 0, 0));  // Set the background color to be transparent
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(textColor);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(text), 0, height - fontMetrics.getDescent());
        g2d.dispose();

        return image;
    }
    private static Double round(Double value){
        return Math.round(value * 100.0) / 100.0;
    }
}
