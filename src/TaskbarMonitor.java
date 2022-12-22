import java.awt.*;

import config.config;

/**
 *  @class TaskbarMonitor.
 *  @description
 *  This program is used to monitor the cpu and gpu usage and temperature aswell as the memory usage.
 *  It will display and update chosen values by the user in the taskbar as tray icons
 *
 *  @author  Leon Burghardt
 *  @version 1.0
 *  @since   2022-12-22
 */

public class TaskbarMonitor {
    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
        // Check if the system tray is supported
        if (!SystemTray.isSupported()) {
            System.exit(1);
        }
        config.loadConfig();
    }
}
