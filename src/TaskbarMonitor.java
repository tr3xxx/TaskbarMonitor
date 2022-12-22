import java.awt.*;

import config.config;
public class TaskbarMonitor {
    public static void main(String[] args) {
        // Check if the system tray is supported
        if (!SystemTray.isSupported()) {
            System.exit(1);
        }
        config.loadConfig();
    }
}
