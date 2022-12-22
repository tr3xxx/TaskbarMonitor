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

public class cpu_temp_tray {
    private static TrayIcon trayIcon;
    public static void enable() throws AWTException {
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
                trayIcon.setPopupMenu(popup_menu.getPopup());
                trayIcon.setImage(createUsageImageWithText(getCpuTemp()));
                trayIcon.setToolTip("CPU Temp: " + getCpuTemp()+ "C");
                System.out.println(getCpuTemp());
            }
        }, 0, 1000);
    }

    public static void disable() {
        SystemTray.getSystemTray().remove(trayIcon);
    }

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
