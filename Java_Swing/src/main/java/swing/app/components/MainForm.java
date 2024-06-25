package swing.app.components;

import net.miginfocom.swing.MigLayout;
import raven.swing.blur.BlurBackground;
import raven.swing.blur.style.StyleOverlay;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainForm extends BlurBackground {

    public MainForm() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/swing/images/background.jpg"));
        setOverlay(new StyleOverlay(new Color(20, 20, 20), 0.1f));
        setImage(icon.getImage());
        init();
    }

    private void init(){
    setLayout(new MigLayout("fill,insets 0 0 6 6","[fill]", "[fill]"));

    systemMenu = new SystemMenu();
    desktopPane = new JDesktopPane();
    desktopPane.setLayout(null);


    desktopPane.setOpaque(false);
    FormManager.getInstance().setDesktopPane(desktopPane);

    add(systemMenu, "dock west, gap 6 6 6 6, width 280!");
    add(desktopPane);
    }

    private SystemMenu systemMenu;
    private JDesktopPane desktopPane;
}
