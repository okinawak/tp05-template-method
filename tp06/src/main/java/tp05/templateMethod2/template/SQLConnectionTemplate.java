package tp05.templateMethod2.template;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Une interaction avec la base de données SQL.
 * <p> Classe d'assez bas niveau. 
 * On préfèrera probablement utiliser sa dérivée, SQLTemplate.
 * Utilise le pattern "template method"
 * 
 * @author rosmord
 */
public abstract class SQLConnectionTemplate<T> {

    /**
     * Exécute la requête associée à cette template.
     */
    public final  T execute() {
        try (
                // Fermeture automatique avec try-with-resources
                Connection connection = ConnectionFactory.getInstance().getConnection();) {
            T resultat = null;
            connection.setAutoCommit(false);
            try {
                resultat = doQuery(connection);
            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            }
            connection.commit();
            return resultat;
        } catch (SQLException e) {
            throw new SQLTemplateException(e);
        }     
    }


    /**
     * Suite de commandes JDBC à exécuter dans une connexion.
     * 
     *
     * @param db la connection (ne pas s'occuper de la fermer)
     * @throws java.sql.SQLException
     */
    protected abstract T doQuery(Connection db) throws SQLException;

}
