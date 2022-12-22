package mem;

import popup_menu.popup_menu;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static utils.generate_image.createUsageImageWithText;

/**
 * @class mem_usage_tray
 * @description
 * This class is used to create a tray icon that shows the memory usage.
 * - It uses the ManagementFactory library to get the memory usage.
 * - It uses the generate_image class to create the tray icon.
 * - It uses the popup_menu class to create the popup menu.
 *
 * @author Leon Burghardt
 * @version 1.0
 * @since 2022-12-22
 */
public class mem_usage_tray {
    private static TrayIcon trayIcon;
    private static boolean isActive;

    /**
     * @method enable
     * @description
     * This method is used to enable the tray icon.
     * - It creates a tray icon and sets its tooltip to the current memory usage.
     * - It creates a popup menu.
     * - It adds the tray icon to the system tray.
     * - It starts a timer to update the tray icon every second.
     *
     * @throws AWTException the awt exception
     */
    public static void enable() throws AWTException {
        isActive = true;
        trayIcon = new TrayIcon(createUsageImageWithText(getMemUsage()));
        trayIcon.setImageAutoSize(true);  // Enable automatic size adjustment
        trayIcon.setToolTip("Memory Usage: " + getMemUsage() + "%");
        SystemTray.getSystemTray().add(trayIcon);

        // Create a popup menu

        trayIcon.setPopupMenu(popup_menu.getPopup());
        SystemTray.getSystemTray().add(trayIcon);

        // Start a timer to update the tray icon every second
        Timer updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Update the tray icon with the current CPU load
                if(isActive) {
                    trayIcon.setPopupMenu(popup_menu.getPopup());
                    trayIcon.setImage(createUsageImageWithText(getMemUsage()));
                    trayIcon.setToolTip("Memory Usage: " + getMemUsage() + "%");
                }
            }
        }, 0, 1000);

    }

    /**
     * @method disable
     * @description
     * This method is used to disable the tray icon.
     * - It removes the tray icon from the system tray.
     * - It sets the isActive variable to false.
     */
    public static void disable() {
        isActive = false;
        SystemTray.getSystemTray().remove(trayIcon);
    }
    /**
     * @method getMemUsage
     * @description
     * This method is used to get the memory usage.
     * - It uses the ManagementFactory library to get the memory usage.
     * - It returns the memory usage as a percentage.
     *
     * @return the memory usage as a double
     */
    private static double getMemUsage() {
        // Get the memory MX bean
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        // Get the current memory usage
        MemoryUsage usage = memoryBean.getHeapMemoryUsage();

        // Calculate the percentage of memory used
        long used = usage.getUsed();
        long max = usage.getMax();
        return (double) used / max * 100;
    }
}
