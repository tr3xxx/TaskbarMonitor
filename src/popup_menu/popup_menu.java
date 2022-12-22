package popup_menu;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import config.config;

public class popup_menu {
    public static PopupMenu getPopup() {

        // Inner class to handle the connection between the popup item and the data
        class MenuItemData {
            MenuItem menuItem;
            String data;

            public MenuItemData(MenuItem menuItem, String data) {
                this.menuItem = menuItem;
                this.data = data;
            }
        }

        PopupMenu popup = new PopupMenu();

        // Item Setup
        MenuItem aboutItem = new MenuItem("About");
        MenuItem exitItem = new MenuItem("Exit");

        // creating menu items
        MenuItem cpuUsageItem = new MenuItem("CPU Usage (disabled)");
        MenuItem cpuTempItem = new MenuItem("CPU Temperature (disabled)");
        MenuItem gpuUsageItem = new MenuItem("GPU Usage (disabled)");
        MenuItem gpuTempItem = new MenuItem("GPU Temperature (disabled)");
        MenuItem memUsageItem = new MenuItem("Memory Usage (disabled)");

        // creating menu data (values used in config)
        String cpuUsage = "show_cpu_usage_tray";
        String cpuTemp = "show_cpu_temp_tray";
        String gpuUsage = "show_gpu_usage_tray";
        String gpuTemp = "show_gpu_temp_tray";
        String memUsage = "show_mem_usage_tray";


        // creating menu item data
        MenuItemData[] menuItems = new MenuItemData[]{
                new MenuItemData(cpuUsageItem, cpuUsage),
                new MenuItemData(cpuTempItem, cpuTemp),
                new MenuItemData(gpuUsageItem, gpuUsage),
                new MenuItemData(gpuTempItem, gpuTemp),
                new MenuItemData(memUsageItem, memUsage)
        };


        // adding menu items to popup
        int index = 1;
        for (MenuItemData menuItem : menuItems) {

            if (config.isEnabled(menuItem.data)) {
                menuItem.menuItem.setLabel(menuItem.menuItem.getLabel().replace("(disabled)", "(enabled)"));
            }
            popup.add(menuItem.menuItem);
            if(index == 2 || index == 4 || index == 5){
                popup.addSeparator();
                if(index == 5){
                    popup.add(aboutItem);
                    popup.add(exitItem);
                }
            }
            index++;
        }

        // creating Action Listener for menu items (exit/about are handled extra cause they are not in the menuItems array)

        for (MenuItemData menuItem : menuItems) {
            menuItem.menuItem.addActionListener(e -> {
                    if (config.isEnabled(menuItem.data)) {
                        config.updateConfig(menuItem.data, false);
                        menuItem.menuItem.setLabel(menuItem.menuItem.getLabel().replace("(enabled)", "(disabled)"));
                    } else {
                        config.updateConfig(menuItem.data, true);
                        menuItem.menuItem.setLabel(menuItem.menuItem.getLabel().replace("(disabled)", "(enabled)"));
                    }
                });
        }


        exitItem.addActionListener(e -> System.exit(0));
        aboutItem.addActionListener(e -> {
            // Create a URI object for the URL
            URI uri = null;
            try {
                uri = new URI("https://github.com/tr3xxx/TaskbarMonitor");
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }

            // Check if the Desktop class is supported
            if (Desktop.isDesktopSupported()) {
                // Get the desktop object
                Desktop desktop = Desktop.getDesktop();

                // Check if the browse action is supported
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    // Open the URL in the default browser
                    try {
                        desktop.browse(uri);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });


        return popup;
    }
}

