package tp05.templateMethod;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JDBCTemplate {

    /**
     * Méthode template qui gère l'ouverture et la fermeture de la connexion.
     * Elle appelle une méthode abstraite pour exécuter l'opération spécifique.
     */
    public final void executer() throws SQLException {
        
        try (Connection connection = ConnectionFactory.getInstance().getConnection()) {
            
            executerAvecConnexion(connection);
            
        } 
    }

    
    protected abstract void executerAvecConnexion(Connection connection) throws SQLException;
}