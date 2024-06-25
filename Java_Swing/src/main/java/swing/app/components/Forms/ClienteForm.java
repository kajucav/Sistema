package swing.app.components.Forms;

import swing.app.Ferramentas.ModTabela;
import swing.app.Ferramentas.TextFieldEditor;
import swing.app.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteForm extends JPanel {

    private JTable table;

    public ClienteForm() {
        setLayout(new BorderLayout());

        // Define as colunas da tabela
        String[] columnNames = {"CPF/CNPJ", "Nome", "Email", "Telefone", "CEP"};

        // Define os editores de células
        TableCellEditor[] editors = {
                new TextFieldEditor(),
                new TextFieldEditor(),
                new TextFieldEditor(),
                new TextFieldEditor(),
                new TextFieldEditor()
        };

        // Cria a tabela
        table = ModTabela.createEditableTable(columnNames, editors);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Busca as informações dos clientes no banco de dados
        try (Connection connection = Database.connect()) {
            String query = "SELECT * FROM cliente";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Adiciona as linhas à tabela
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (resultSet.next()) {
                long cpfCnpj = resultSet.getLong("cpf_cnpj");
                String nomeCliente = resultSet.getString("nome_cliente");
                String email = resultSet.getString("email");
                String telefone = resultSet.getString("telefone");
                int cep = resultSet.getInt("cep");

                model.addRow(new Object[]{String.valueOf(cpfCnpj), nomeCliente, email, telefone, String.valueOf(cep)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JTable getTable() {
        return table;
    }
}