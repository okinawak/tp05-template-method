package tp05.templateMethod;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JDBCTemplate<T> {

    /**
     * Méthode template qui gère l'ouverture et la fermeture de la connexion.
     * Elle appelle une méthode abstraite pour exécuter l'opération spécifique.
     */
    public final T executer() throws SQLException {
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            return executerAvecConnexion(connection);
        }
    }

    
    protected abstract T executerAvecConnexion(Connection connection) throws SQLException;
}