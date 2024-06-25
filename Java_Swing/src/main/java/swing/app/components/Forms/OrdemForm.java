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

public class OrdemForm extends JPanel {

    private JTable table;

    public OrdemForm() {
        setLayout(new BorderLayout());

        // Define as colunas da tabela
        String[] columnNames = {"Código da Ordem", "Código do Produto", "Quantidade", "Data do Pedido", "Status da Compra"};

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

        // Busca as informações das ordens no banco de dados
        try (Connection connection = Database.connect()) {
            String query = "SELECT * FROM ordem_compra";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Adiciona as linhas à tabela
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (resultSet.next()) {
                int codOrdCompra = resultSet.getInt("cod_ord_compra");
                int codProd = resultSet.getInt("cod_prod");
                int qntComprar = resultSet.getInt("qnt_comprar");
                String dataPedido = resultSet.getTimestamp("data_pedido").toString();
                String statusCompra = resultSet.getString("status_compra");

                model.addRow(new Object[]{String.valueOf(codOrdCompra), String.valueOf(codProd), String.valueOf(qntComprar), dataPedido, statusCompra});
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