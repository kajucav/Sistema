package swing.app.components.Forms;

import swing.app.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricoForm extends JPanel {

    public HistoricoForm() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Código do Histórico", "Nome da Tabela", "Nome da Coluna", "Código do Registro", "Valor Antigo", "Valor Novo", "Data da Modificação"};
        try (Connection connection = Database.connect()) {
            String query = "SELECT * FROM hist_mod";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            Object[][] data = new Object[rowCount][7];
            int i = 0;
            while (resultSet.next()) {
                data[i][0] = resultSet.getInt("cod_hist_mod");
                data[i][1] = resultSet.getString("tb_nome");
                data[i][2] = resultSet.getString("cln_nome");
                data[i][3] = resultSet.getInt("cod_registro");
                data[i][4] = resultSet.getString("valor_antigo");
                data[i][5] = resultSet.getString("valor_novo");
                data[i][6] = resultSet.getTimestamp("data_modificacao");
                i++;
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            add(new JScrollPane(table), BorderLayout.CENTER);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}