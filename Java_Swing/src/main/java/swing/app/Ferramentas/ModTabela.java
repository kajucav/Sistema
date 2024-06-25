package swing.app.Ferramentas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class ModTabela {

    public static JTable createEditableTable(String[] columnNames, TableCellEditor[] editors) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);


        for (int i = 0; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellEditor(editors[i]);
        }

        return table;
    }
}