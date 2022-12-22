package gpu;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Gpu;
import popup_menu.popup_menu;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static utils.generate_image.createUsageImageWithText;

/**
 * @class gpu_temp_tray
 * @description
 * This class is used to create a tray icon that shows the GPU temperature.
 * - It uses the JSensors library to get the GPU temperature.
 * - It uses the generate_image class to create the tray icon.
 * - It uses the popup_menu class to create the popup menu.
 *
 * @author Leon Burghardt
 * @version 1.0
 * @since 2022-12-22
 */
public class gpu_usage_tray {
    private static TrayIcon trayIcon;
    private static boolean isActive;

    /**
     * @method enable
     * @description
     * This method is used to enable the tray icon.
     * - It creates a tray icon and sets its tooltip to the current GPU temperature.
     * - It creates a popup menu.
     * - It adds the tray icon to the system tray.
     * - It starts a timer to update the tray icon every second.
     *
     * @throws AWTException the awt exception
     */
    public static void enable() throws AWTException {
        isActive = true;
        trayIcon = new TrayIcon(createUsageImageWithText(getGpuLoad()));
        trayIcon.setImageAutoSize(true);  // Enable automatic size adjustment
        trayIcon.setToolTip("CPU Load: " + getGpuLoad() + "%");


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
                    trayIcon.setImage(createUsageImageWithText(getGpuLoad()));
                    trayIcon.setToolTip("CPU Load: " + getGpuLoad() + "%");
                }
            }
        }, 0, 1000);
    }

    /**
     * @method disable
     * @description
     * This method is used to disable the tray icon.
     * - It removes the tray icon from the system tray.
     */
    public static void disable() {
        isActive = false;
        SystemTray.getSystemTray().remove(trayIcon);
    }

    /**
     * @method getGpuLoad
     * @description
     * This method is used to get the GPU load.
     * - It uses the JSensors library to get the GPU load.
     *
     * @return the GPU load
     */
    private static double getGpuLoad() {

        Components components = JSensors.get.components();
        List<Gpu> gpus = components.gpus;

        Double[] values = new Double[gpus.size()];

        for (com.profesorfalken.jsensors.model.components.Gpu gpu : gpus) {
            values[gpus.indexOf(gpu)] = gpu.sensors.loads.get(0).value;
        }

        // calculate average of all gpus
        Double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.length;

    }

}
