package cpu;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Cpu;
import popup_menu.popup_menu;
import static utils.generate_image.createUsageImageWithText;

/**
 * @class cpu_usage_tray
 * @description
 * This class is used to create a tray icon that shows the CPU usage.
 * - It uses the JSensors library to get the CPU usage.
 * - It uses the generate_image class to create the tray icon.
 * - It uses the popup_menu class to create the popup menu.
 *
 *  @author Leon Burghardt
 *  @version 1.0
 *  @since 2022-12-22
 */
public class cpu_usage_tray {

    private static TrayIcon trayIcon;
    private static boolean isActive;

    /**
     * @method enable
     * @description
     * This method is used to enable the tray icon.
     * - It creates a tray icon and sets its tooltip to the current CPU usage.
     * - It creates a popup menu.
     * - It adds the tray icon to the system tray.
     * - It starts a timer to update the tray icon every second.
     *
     * @throws AWTException the awt exception
     */
    public static void enable() throws AWTException {
        isActive = true;
        // Create a tray icon and set its tooltip to the current CPU load
        trayIcon = new TrayIcon(createUsageImageWithText(getCpuLoad()));
        trayIcon.setImageAutoSize(true);  // Enable automatic size adjustment
        trayIcon.setToolTip("CPU Load: " + getCpuLoad() + "%");

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
                    trayIcon.setImage(createUsageImageWithText(getCpuLoad()));
                    trayIcon.setToolTip("CPU Load: " + getCpuLoad() + "%");
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
     *
     */
    public static void disable() {
        isActive = false;
        SystemTray.getSystemTray().remove(trayIcon);
    }

    /**
     * @method getCpuLoad
     * @description
     * This method is used to get the CPU load.
     * - It uses the JSensors library to get the CPU load.
     * - It returns the CPU load as a double.
     *
     * @return the cpu load as a double
     */
    private static Double getCpuLoad() {
        List<Cpu> cpus = JSensors.get.components().cpus;

        Double[] values = new Double[cpus.size()];

        for (Cpu cpu : cpus) {
            values[cpus.indexOf(cpu)] = cpu.sensors.loads.get(0).value;
        }

        // calculate average of all cpus
        Double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.length;

    }
}
