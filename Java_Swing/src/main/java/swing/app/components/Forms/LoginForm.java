package swing.app.components.Forms;

import swing.app.database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JPanel {

    private JTextField loginField;
    private JTextField passwordField;
    private JButton saveButton;

    public LoginForm(String login) {
        setLayout(new BorderLayout());

        loginField = new JTextField();
        passwordField = new JTextField();
        saveButton = new JButton("Salvar");

        add(new JLabel("Login:"), BorderLayout.NORTH);
        add(loginField, BorderLayout.CENTER);
        add(new JLabel("Senha:"), BorderLayout.SOUTH);
        add(passwordField, BorderLayout.SOUTH);
        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLoginData(login);
            }
        });

        // Busca as informações do usuário no banco de dados
        try (Connection connection = Database.connect()) {
            String query = "SELECT * FROM usuario WHERE login = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String loginResult = resultSet.getString("login");
                String passwordResult = resultSet.getString("senha");

                loginField.setText(loginResult);
                passwordField.setText(passwordResult);
            } else {
                // Usuário não encontrado, exiba uma mensagem de erro
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateLoginData(String login) {
        String newLogin = loginField.getText();
        String newPassword = passwordField.getText();

        try (Connection connection = Database.connect()) {
            String query = "UPDATE usuario SET login = ?, senha = ? WHERE login = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newLogin);
            statement.setString(2, newPassword);
            statement.setString(3, login);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Dados de login atualizados com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar os dados de login.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}