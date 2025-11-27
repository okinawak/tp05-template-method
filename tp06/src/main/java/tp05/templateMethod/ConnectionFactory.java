package tp05.templateMethod;

import java.sql.*;

public class ConnectionFactory {
    public static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final ConnectionFactory INSTANCE = new ConnectionFactory();

    public static ConnectionFactory getInstance() {
        return INSTANCE;
    }

    private ConnectionFactory() {
        try {
            Class.forName("org.h2.Driver");
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, "sa", "");
        } catch (final SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
