package swing.app.Ferramentas;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class TextFieldEditor extends AbstractCellEditor implements TableCellEditor {
    private final JTextField textField = new JTextField();

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setText((String) value);
        return textField;
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }
}