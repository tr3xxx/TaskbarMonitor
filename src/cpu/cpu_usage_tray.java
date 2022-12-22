package cpu;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.sun.management.OperatingSystemMXBean;

import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import popup_menu.popup_menu;

import static utils.generate_image.createUsageImageWithText;

public class cpu_usage_tray {

    private static TrayIcon trayIcon;

    public static void enable() throws AWTException {
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
                trayIcon.setPopupMenu(popup_menu.getPopup());
                trayIcon.setImage(createUsageImageWithText(getCpuLoad()));
                trayIcon.setToolTip("CPU Load: " + getCpuLoad()+ "%");
            }
        }, 0, 1000);
    }

    public static void disable() {
        SystemTray.getSystemTray().remove(trayIcon);
    }


    private static Double getCpuLoad() {
        Components components = JSensors.get.components();
        List<Cpu> cpus = components.cpus;

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
