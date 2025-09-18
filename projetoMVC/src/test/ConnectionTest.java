package test;

import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();

        if (connection != null) {
            System.out.println("Conexão realizada com sucesso!");
        } else {
            System.out.println("Erro na conexão...");
        }

        connection.close();

    }

}
