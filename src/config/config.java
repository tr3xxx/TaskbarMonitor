package config;

import cpu.cpu_temp_tray;
import cpu.cpu_usage_tray;
import gpu.gpu_temp_tray;
import gpu.gpu_usage_tray;
import mem.mem_usage_tray;
import default_tray.default_tray;

import java.awt.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class config {
    private static final Preferences prefs = Preferences.userNodeForPackage(config.class);

    public static void loadConfig() {
        int enabled_trays = 0;
        try {
            prefs.sync();
            // Load cpu preferences
            if (prefs.getBoolean("show_cpu_usage_tray", false)) {
                cpu_usage_tray.enable();
                enabled_trays++;
            }
            else {
                cpu_usage_tray.disable();
            }
            if (prefs.getBoolean("show_cpu_temp_tray", false)) {
                cpu_temp_tray.enable();
                enabled_trays++;
            }
            else {
                cpu_temp_tray.disable();
            }
            // Load gpu preferences
            if (prefs.getBoolean("show_gpu_usage_tray", false)) {
                gpu_usage_tray.enable();
                enabled_trays++;
            }
            else {
                gpu_usage_tray.disable();
            }
            if (prefs.getBoolean("show_gpu_temp_tray", false)) {
                gpu_temp_tray.enable();
                enabled_trays++;
            }
            else {
                gpu_temp_tray.disable();
            }

            // Load memory preferences
            if (prefs.getBoolean("show_mem_usage_tray", false)) {
                mem_usage_tray.enable();
                enabled_trays++;
            }
            else {
                mem_usage_tray.disable();
            }


            // prepend the program from closing if there are no enabled trays
            if(enabled_trays == 0){
                default_tray.enable();
                System.out.println("No enabled trays, default tray enabled");
            }
            else{
                System.out.println("Enabled trays: " + enabled_trays);
                default_tray.disable();
            }




        } catch(AWTException | BackingStoreException err){
            err.printStackTrace();
        }
    }

    public static void updateConfig(String key, boolean value) {
        prefs.putBoolean(key, value);
        System.out.println("Updated config: "+key+" = "+value);
        loadConfig();
    }

    public static boolean isEnabled(String key) {
        return (prefs.getBoolean(key, false));
    }

}
