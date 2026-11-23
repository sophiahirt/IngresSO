package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexaoDB {

    // Ajuste aqui o usuário e a senha do seu MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/ingresso";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    private ConexaoDB() {
    }

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
