package cpu;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import popup_menu.popup_menu;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static utils.generate_image.createUsageImageWithText;

/**
 * @class cpu_temperature_tray
 * @description
 * This class is used to create a tray icon that shows the CPU temperature.
 * - It uses the JSensors library to get the CPU temperature.
 * - It uses the generate_image class to create the tray icon.
 * - It uses the popup_menu class to create the popup menu.
 *
 * @author Leon Burghardt
 * @version 1.0
 * @since 2022-12-22
 */
public class cpu_temp_tray {
    private static TrayIcon trayIcon;
    private static boolean isActive;

    /**
     * @method enable
     * @description
     * This method is used to enable the tray icon.
     * - It creates a tray icon and sets its tooltip to the current CPU temperature.
     * - It creates a popup menu.
     * - It adds the tray icon to the system tray.
     * - It starts a timer to update the tray icon every second.
     *
     * @throws AWTException the awt exception
     */
    public static void enable() throws AWTException {
        isActive = true;
        trayIcon = new TrayIcon(createUsageImageWithText(getCpuTemp()));
        trayIcon.setImageAutoSize(true);  // Enable automatic size adjustment
        trayIcon.setToolTip("CPU Temp: " + getCpuTemp() + "C");


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
                    trayIcon.setImage(createUsageImageWithText(getCpuTemp()));
                    trayIcon.setToolTip("CPU Temp: " + getCpuTemp() + "C");
                }
            }
        }, 0, 1000);
    }

    /**
     * @method disable
     * @description
     * This method is used to disable the tray icon.
     * - It sets the isActive variable to false.
     * - It removes the tray icon from the system tray.
     *
     */
    public static void disable() {
        isActive = false;
        SystemTray.getSystemTray().remove(trayIcon);
    }

    /**
     * @method getCpuLoad
     * @description
     * This method is used to get the CPU temperature.
     * - It uses the JSensors library to get the CPU temperature.
     * - It returns the CPU load.
     *
     * @return the cpu temperature as double
     */
    private static double getCpuTemp() {
        Components components = JSensors.get.components();
        List<Cpu> cpus = components.cpus;

        Double[] values = new Double[cpus.size()];

        for (Cpu cpu : cpus) {
            values[cpus.indexOf(cpu)] = cpu.sensors.temperatures.get(0).value;
        }

        // calculate average of all cpus
        Double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

}
