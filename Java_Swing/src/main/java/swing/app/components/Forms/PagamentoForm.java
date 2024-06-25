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

public class PagamentoForm extends JPanel {

    private JTable table;

    public PagamentoForm() {
        setLayout(new BorderLayout());

        // Define as colunas da tabela
        String[] columnNames = {"Código do Pagamento", "Código do Produto", "CPF/CNPJ", "Quantidade do Produto", "Total", "Método de Pagamento", "Data do Pagamento"};

        // Define os editores de células
        TableCellEditor[] editors = {
                new TextFieldEditor(),
                new TextFieldEditor(),
                new TextFieldEditor(),
                new TextFieldEditor(),
                new TextFieldEditor(),
                new TextFieldEditor(),
                new TextFieldEditor()
        };

        // Cria a tabela
        table = ModTabela.createEditableTable(columnNames, editors);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Busca as informações dos pagamentos no banco de dados
        try (Connection connection = Database.connect()) {
            String query = "SELECT * FROM pagamento";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Adiciona as linhas à tabela
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (resultSet.next()) {
                int codPag = resultSet.getInt("cod_pag");
                int codProd = resultSet.getInt("cod_prod");
                long cpfCnpj = resultSet.getLong("cpf_cnpj");
                int qntProd = resultSet.getInt("qnt_prod");
                double total = resultSet.getDouble("total");
                String metodoPag = resultSet.getString("metodo_pag");
                String dataPag = resultSet.getTimestamp("data_pag").toString();

                model.addRow(new Object[]{String.valueOf(codPag), String.valueOf(codProd), String.valueOf(cpfCnpj), String.valueOf(qntProd), String.valueOf(total), metodoPag, dataPag});
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