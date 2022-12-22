package default_tray;

import popup_menu.popup_menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class default_tray {
    private static TrayIcon trayIcon;

    public static void enable() throws AWTException {
        trayIcon = new TrayIcon(Objects.requireNonNull(fetchDefault()));
        trayIcon.setImageAutoSize(true);
        trayIcon.setPopupMenu(popup_menu.getPopup());
        SystemTray.getSystemTray().add(trayIcon);
    }

    private static BufferedImage fetchDefault() {
        try {
            // Create a URL object for the image
            String default_url = "https://www.taxfox.eu/wp-content/uploads/2017/04/icon-info.png";
            URL url = new URL(default_url);

            // Read the image from the URL and return it as a BufferedImage
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void disable() {
        SystemTray.getSystemTray().remove(trayIcon);
    }
}


