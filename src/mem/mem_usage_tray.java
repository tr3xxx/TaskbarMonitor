package mem;

import popup_menu.popup_menu;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static utils.generate_image.createUsageImageWithText;

public class mem_usage_tray {
    private static TrayIcon trayIcon;
    public static void enable() throws AWTException {

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
                trayIcon.setPopupMenu(popup_menu.getPopup());
                trayIcon.setImage(createUsageImageWithText(getMemUsage()));
                trayIcon.setToolTip("Memory Usage: " + getMemUsage()+ "%");
                System.out.println(getMemUsage());
            }
        }, 0, 1000);

    }
    public static void disable() {
        SystemTray.getSystemTray().remove(trayIcon);
    }
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
