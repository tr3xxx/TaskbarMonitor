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

public class gpu_temp_tray {
    private static TrayIcon trayIcon;
    public static void enable() throws AWTException {
        trayIcon = new TrayIcon(createUsageImageWithText(getGpuTemp()));
        trayIcon.setImageAutoSize(true);  // Enable automatic size adjustment
        trayIcon.setToolTip("CPU Load: " + getGpuTemp() + "C");


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
                trayIcon.setImage(createUsageImageWithText(getGpuTemp()));
                trayIcon.setToolTip("GPU Temp: " + getGpuTemp()+ "C");
                System.out.println(getGpuTemp());
            }
        }, 0, 1000);
    }

    public static void disable() {
        SystemTray.getSystemTray().remove(trayIcon);
    }
    private static double getGpuTemp() {

        Components components = JSensors.get.components();
        List<Gpu> gpus = components.gpus;

        Double[] values = new Double[gpus.size()];

        for (com.profesorfalken.jsensors.model.components.Gpu gpu : gpus) {
            values[gpus.indexOf(gpu)] = gpu.sensors.temperatures.get(0).value;
        }

        // calculate average of all gpus
        Double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.length;

    }
}
