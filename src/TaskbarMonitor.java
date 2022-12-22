import java.awt.*;
import java.util.prefs.Preferences;

import cpu.cpu_temp_tray;
import cpu.cpu_usage_tray;
import gpu.gpu_temp_tray;
import gpu.gpu_usage_tray;
import mem.mem_usage_tray;
import

public class TaskbarMonitor {


    public static void main(String[] args){
        // Check if the system tray is supported
        if (!SystemTray.isSupported()) {
            System.exit(1);
        }

    }

    private static void loadConfig(){
        try {

            Preferences prefs = Preferences.userNodeForPackage(TaskbarMonitor.class);

            // Load cpu preferences
            if (prefs.getBoolean("show_cpu_usage_tray", false)) {
                cpu_usage_tray.enable();
            }
            if (prefs.getBoolean("show_cpu_temp_tray", false)) {
                cpu_temp_tray.enable();
            }

            // Load gpu preferences
            if (prefs.getBoolean("show_gpu_usage_tray", false)) {
                gpu_usage_tray.enable();
            }
            if (prefs.getBoolean("show_gpu_temp_tray", false)) {
                gpu_temp_tray.enable();
            }

            // Load memory preferences
            if (prefs.getBoolean("show_mem_usage_tray", false)) {
                mem_usage_tray.enable();
            }

        } catch(AWTException err){
            err.printStackTrace();
        }

    }


}
