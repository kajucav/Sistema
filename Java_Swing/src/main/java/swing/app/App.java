package Java_Swing.src.main.java.swing.app;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import swing.app.components.MainForm;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private MainForm mainForm = new MainForm();

    public App(){
        init();
    }

    public void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().putClientProperty( FlatClientProperties.FULL_WINDOW_CONTENT, true );
        setSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        getContentPane().add(mainForm);
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13) );
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new App().setVisible(true));
    }
}
