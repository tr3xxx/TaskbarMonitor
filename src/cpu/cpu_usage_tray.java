package cpu;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.management.OperatingSystemMXBean;

import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import popup_menu.popup_menu;

public class cpu_usage_tray {

    private static TrayIcon trayIcon;

    public static void enable() throws AWTException {
        // Create a tray icon and set its tooltip to the current CPU load
        trayIcon = new TrayIcon(createImageWithText(getCpuLoad()));
        trayIcon.setImageAutoSize(true);  // Enable automatic size adjustment
        trayIcon.setToolTip("CPU Load: " + getCpuLoad() + "%");
        trayIcon.setPopupMenu(new PopupMenu("CPU Load: " + getCpuLoad() + "%"));

        // Create a popup menu

        trayIcon.setPopupMenu(popup_menu.getPopup());
        SystemTray.getSystemTray().add(trayIcon);


        // Start a timer to update the tray icon every second
        Timer updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Update the tray icon with the current CPU load
                trayIcon.setImage(createImageWithText(getCpuLoad()));
                trayIcon.setToolTip("CPU Load: " + getCpuLoad()+ "%");
            }
        }, 0, 1000);
    }

    public static void disable() {
        SystemTray.getSystemTray().remove(trayIcon);
    }

    private static BufferedImage createImageWithText(double temp) {
        // Set the font and font size
        Font font = new Font("Arial", Font.BOLD, 32);
        int fontSize = 12;

        // Set the text color based on the value of temp
        Color textColor;
        if (temp < 30) {
            textColor = Color.GREEN;
        } else if (temp < 70) {
            textColor = Color.YELLOW;
        } else {
            textColor = Color.RED;
        }

        // Convert the temp value to a string
        String text = String.valueOf(temp);

        // Calculate the dimensions of the image
        FontMetrics fontMetrics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics().getFontMetrics(font);
        int width = fontMetrics.stringWidth(text);
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
        g2d.drawString(text, 0, height - fontMetrics.getDescent());
        g2d.dispose();

        return image;
    }

    private static Double getCpuLoad() {
        try {
            // Get the operating system MX bean
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            DecimalFormat df = new DecimalFormat("#.#");

            // Parse the string using the DecimalFormat.parse() method
            Number number = df.parse(df.format(osBean.getCpuLoad() * 100));
            System.out.println(number.doubleValue());

            // Convert the Number to a double
            return number.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.00;
        }
    }
}
