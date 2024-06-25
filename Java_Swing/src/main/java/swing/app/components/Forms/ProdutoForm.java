package swing.app.components.Forms;

import swing.app.Ferramentas.ModTabela;
import swing.app.Ferramentas.TextFieldEditor;
import swing.app.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoForm extends JPanel {

    private JTable table;
    private JPanel productsPanel;

    public ProdutoForm() {
        setLayout(new BorderLayout());

        // Define as colunas da tabela
        String[] columnNames = {"Código do Produto", "Nome do Produto", "Marca do Produto", "Preço", "Local da Imagem"};

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

        // Cria o painel de produtos
        productsPanel = new JPanel(new GridLayout(0, 3));
        add(new JScrollPane(productsPanel), BorderLayout.SOUTH);

        // Busca as informações dos produtos no banco de dados
        try (Connection connection = Database.connect()) {
            String query = "SELECT * FROM produto";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Adiciona as linhas à tabela e os produtos ao painel
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (resultSet.next()) {
                int codProd = resultSet.getInt("cod_prod");
                String nomeProd = resultSet.getString("nome_prod");
                String marcaProd = resultSet.getString("marca_prod");
                double preco = resultSet.getDouble("preco");
                String imageLocal = resultSet.getString("img_local");

                model.addRow(new Object[]{String.valueOf(codProd), nomeProd, marcaProd, String.valueOf(preco), imageLocal});
                addProductToPanel(codProd, nomeProd, marcaProd, preco, imageLocal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProductToPanel(int codProd, String nomeProd, String marcaProd, double preco, String imageLocal) {
        JPanel productPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel("Nome: " + nomeProd);
        JLabel brandLabel = new JLabel("Marca: " + marcaProd);
        JLabel priceLabel = new JLabel("Preço: " + preco);
        JLabel imageLabel = new JLabel();

        try {
            ImageIcon imageIcon = new ImageIcon(new URL(imageLocal));
            imageLabel.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        productPanel.add(imageLabel, BorderLayout.NORTH);
        productPanel.add(nameLabel, BorderLayout.CENTER);
        productPanel.add(brandLabel, BorderLayout.CENTER);
        productPanel.add(priceLabel, BorderLayout.SOUTH);

        productsPanel.add(productPanel);
    }

    public JTable getTable() {
        return table;
    }
}