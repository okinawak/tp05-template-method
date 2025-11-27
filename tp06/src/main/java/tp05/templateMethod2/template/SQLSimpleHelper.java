package tp05.templateMethod2.template;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Un ensemble de méthodes pratiques pour exécuter des requêtes SQL.
 */
public class SQLSimpleHelper {
    /**
     * Helper class statique : pas d'instanciation.
     */
    private SQLSimpleHelper() {
    }

    /**
     * Insère une entrée dans la base, et récupère son id (forcément un long).
     * 
     * @param sql  : la requête d'insertion, avec des '?' à la place des parties
     *             variables.
     * @param args les valeurs des '?'
     * @return l'id de la nouvelle entrée.
     * @throws SQLTemplateException en cas de problème.
     */
    public static long insert(String sql, Object... args) {
        List<List<Object>> argList = Collections.singletonList(Arrays.asList(args));
        Long res = new InsertTemplateAux(sql, argList).execute();
        return res;
    }

    /**
     * Exécute une modification sur une entrée.
     * 
     * @param sql  une requête de modification ; des '?' signalent des arguments.
     * @param args les valeurs des arguments.
     */
    public static void update(String sql, Object... args) {
        List<List<Object>> argList = Collections.singletonList(Arrays.asList(args));
        updateBatch(sql, argList);
    }

    /**
     * Exécute une modification sur une série d'entrée
     * 
     * @param sql     une requête de modification ; des '?' signalent des arguments.
     * @param argList une liste de t-uple, chacun des t-uples correspondant à une
     *                série d'arguments.
     */
    public static void updateBatch(String sql, List<List<Object>> argList) {
        new SQLUpdateTemplateAux(sql, argList).execute();
    }

    /**
     * Exécute une requête qui doit retourner exactement un entier (ou null).
     * 
     * @param sql
     * @param args
     * @return un Integer ou null, selon que la requête a un retour ou non.
     * @throws SQLTemplateException si la requête retourne plus d'un résultat.
     */
    public static Integer sqlIntQuery(String sql, Object... args) {
        List<Integer> res = new SQLIntegerQueryAux(sql).execute(args);
        if (res.isEmpty())
            return null;
        else if (res.size() == 1)
            return res.get(0);
        else
            throw new SQLTemplateException("la requête renvoie " + res.size() + " entiers " + res);
    }

    public static Long sqlLongQuery(String sql, Object... args) {
        List<Long> res = new SQLLongQueryAux(sql).execute(args);
        if (res.isEmpty())
            return null;
        else if (res.size() == 1)
            return res.get(0);
        else
            throw new SQLTemplateException("la requête renvoie " + res.size() + " entiers " + res);
    }

    /**
     * Template pour l'insertion, avec récupération de l'id.
     */
    private static class InsertTemplateAux extends SQLStatementTemplate<Long> {

        public InsertTemplateAux(String sql, List<List<Object>> argList) {
            super(sql, argList);
        }

        @Override
        protected Long initResult() {
            return null; // Sera fixé à la fin !
        }

        @Override
        protected void performStatement(Long currentResult, PreparedStatement pst) throws SQLException {
            pst.executeUpdate();
        }

        @Override
        protected Long performAtEnd(Connection db, Long result) throws SQLException {
            String LAST_ID_REQUEST = "SELECT SCOPE_IDENTITY()";
            try (Statement st = db.createStatement(); ResultSet rs = st.executeQuery(LAST_ID_REQUEST)) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLTemplateException("insert n'a pas fonctionné ?");
                }
            }
        }
    }

    private static class SQLUpdateTemplateAux extends SQLStatementTemplate<Void> {

        public SQLUpdateTemplateAux(String sql, List<List<Object>> argList) {
            super(sql, argList);
        }

        @Override
        protected Void initResult() {
            return null;
        }

        @Override
        protected void performStatement(Void currentResult, PreparedStatement pst) throws SQLException {
            pst.executeUpdate();
        }
    }

    private static class SQLIntegerQueryAux extends SQLQueryTemplate<Integer> {
        public SQLIntegerQueryAux(String sql) {
            super(sql);
        }

        @Override
        protected Integer extractData(ResultSet res) throws SQLException {
            return res.getInt(1);
        }
    }

    private static class SQLLongQueryAux extends SQLQueryTemplate<Long> {
        public SQLLongQueryAux(String sql) {
            super(sql);
        }

        @Override
        protected Long extractData(ResultSet res) throws SQLException {
            return res.getLong(1);
        }
    }
}