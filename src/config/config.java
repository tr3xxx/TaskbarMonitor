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

/**
 * @class Config.
 * @description
 * This class is used to load and update registry values.
 * These values are used to determine which tray icons are displayed and which are not.
 *
 * @author Leon Burghardt
 * @version 1.0
 * @since   2022-12-22
 */

public class config {
    private static final Preferences prefs = Preferences.userNodeForPackage(config.class);

    /**
     * @method updateConfig
     * @description
     * This method is used to load the config from the registry.
     * It will check if the config is already set and if not it will set the default values.
     * After that it will check which tray icons are enabled and start them.
     *
     */


    public static void loadConfig() {
        int enabled_trays = 0;
        try {
            prefs.sync();

            default_tray.enable();

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
            if(enabled_trays != 0){
                default_tray.disable();
            }

        } catch(AWTException | BackingStoreException err){
            err.printStackTrace();
        }
    }

    /**
     * @method updateConfig
     * @description
     * This method is used to update the config in the registry.
     *
     * @param key   the key of the config value
     * @param value the value the key should be set to
     */
    public static void updateConfig(String key, boolean value) {
        prefs.putBoolean(key, value);
        System.out.println("Updated config: "+key+" = "+value);
        loadConfig();
    }

    /**
     * @method isEnable
     * @description
     * This method is used to check if a tray icon is enabled.
     * Necessary to check when the program is started to determine which tray icons should be started.
     *
     * @param key the key of the config value to check for
     * @return the boolean value of the config key, if the key is not set it will return false
     */
    public static boolean isEnabled(String key) {
        return (prefs.getBoolean(key, false));
    }

}
