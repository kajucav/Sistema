package Banco_de_Dados.Conexao.src.main.java.Projeto_Final.xml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoBD implements ConectaBD {
    private static final String URL = "jdbc:postgresql://idly-protective-tuatara.data-1.use1.tembo.io:5432/loja_virtual?sslmode=require";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qbYNBToxRy2BpoRO";

    public Connection connect() {
        Connection conx = null;
        try {
            conx = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conx != null) {
                System.out.println("Feita a conexão com o Banco de Dados.");
            } else {
                System.out.println("Falha na conexão, tente novamente.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conx;
    }
}
