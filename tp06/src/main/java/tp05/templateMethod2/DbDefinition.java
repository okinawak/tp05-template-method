package tp05.templateMethod2;

import java.sql.*;

import tp05.templateMethod2.template.ConnectionFactory;

/**
 * Cette classe contient simplement le code SQL
 * de définition de la base, sous une forme simple
 * à manipuler.
 */
public class DbDefinition {
    public static final String DB_DEFINITION[] = {
        "DROP TABLE IF EXISTS ETUDIANT;",
        "CREATE TABLE ETUDIANT("
            +"ETUDIANT_ID BIGINT PRIMARY KEY AUTO_INCREMENT,"
            +"NOM VARCHAR(255)"
        +");",
        "INSERT INTO ETUDIANT  VALUES (1,'Alan Turing');",
        "INSERT INTO ETUDIANT  VALUES (2,'Ada Lovelace');",
        "INSERT INTO ETUDIANT  VALUES (3,'Niklaus Wirth');",
        "INSERT INTO ETUDIANT  VALUES (4,'Charles Babbage');",
        "INSERT INTO ETUDIANT  VALUES (5,'John von Neumann');",
    };

    public void initialiserDB() throws SQLException {
        try (
                // Fermeture automatique avec try-with-resources
                Connection connection = ConnectionFactory.getInstance().getConnection();
                Statement st = connection.createStatement();) {
            connection.setAutoCommit(false); // démarre une transaction !
            try {
                for (String ligne : DbDefinition.DB_DEFINITION) {
                    st.executeUpdate(ligne);
                }
                connection.commit();
            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public String retourneContenuDB() throws SQLException {
        StringBuilder builder = new StringBuilder();
        try (
                // Fermeture automatique avec try-with-resources
                Connection connection = ConnectionFactory.getInstance().getConnection();
                Statement st = connection.createStatement();
                ResultSet res = st.executeQuery("select * from etudiant order by etudiant_id");) {
            while (res.next()) {
                Long id = res.getLong("ETUDIANT_ID");
                String nom = res.getString("NOM");
                builder.append(id + " " + nom + "\n");
            }
        } catch (SQLException | RuntimeException e) {
            throw e;
        }
        return builder.toString();
    }
}
