package popup_menu;

import java.awt.*;

public class popup_menu {
    public static PopupMenu getPopup() {
        PopupMenu popup = new PopupMenu();
        MenuItem aboutItem = new MenuItem("About");

        MenuItem exitItem = new MenuItem("Exit");
        popup.add(aboutItem);
        popup.add(exitItem);

        exitItem.addActionListener(e -> System.exit(0));
        aboutItem.addActionListener(e -> System.out.println("This is a CPU monitor"));


        return popup;
    }
}

