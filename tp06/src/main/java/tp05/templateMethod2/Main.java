package tp05.templateMethod2;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DbDefinition db = new DbDefinition();
        db.initialiserDB();
        String contenu = db.retourneContenuDB();
        System.out.println(contenu);
    }
}
