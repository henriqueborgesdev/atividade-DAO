package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/bd_locadora";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456";

    public static Connection getConexao() throws SQLException {
        try {
            // Carrega o driver JDBC do MySQL (lib/mysql-connector-j-9.5.0.jar)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado", e);
        }
    }
}
