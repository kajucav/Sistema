package swing.app.components.Forms;

import javax.swing.*;
import java.awt.*;

public class SobreForm extends JPanel {

    private JTextArea textArea;

    public SobreForm() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Exibe as informações dos autores e o número do SAC
        String autores = "Autores: Gabriel Duarte, Elter Rodrigues, Kauanne Julia, João Vitor Jardim";
        String sac = "SAC: 22303292";
        textArea.setText(autores + "\n" + sac);

        // Adiciona um campo de texto onde o usuário pode escrever um relatório de problema
        JTextField reportField = new JTextField();
        add(reportField, BorderLayout.SOUTH);
    }
}