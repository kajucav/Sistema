package desmpregados.xml;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertProduct {

    private static final String URL = "jdbc:postgresql://idly-protective-tuatara.data-1.use1.tembo.io:5432/loja_virtual?sslmode=require";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qbYNBToxRy2BpoRO";

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO produto (nome_prod, preco, qtd_estoque, departamento, marca, img_local) VALUES (?, ?, ?, ?, ?, ?)";

    public static void main(String[] args) {
        // Dados do produto a ser inserido
        String nomeProd = "Chocolate Lacta";
        double preco = 5.99;
        int qtdEstoque = 50;
        String departamento = "Alimentos";
        String marca = "Lacta";
        String imgLocal = "imgs/1631_Tablete_de_Chocolate_80_Gramas_Lacta_ao_Leite_1.jpg";

        // Conexão com o banco de dados e inserção do produto
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {

            preparedStatement.setString(1, nomeProd);
            preparedStatement.setBigDecimal(2, BigDecimal.valueOf(preco));
            preparedStatement.setInt(3, qtdEstoque);
            preparedStatement.setString(4, departamento);
            preparedStatement.setString(5, marca);
            preparedStatement.setString(6, imgLocal);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("Um novo produto foi inserido com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir o produto:");
            e.printStackTrace();
        }
    }
}