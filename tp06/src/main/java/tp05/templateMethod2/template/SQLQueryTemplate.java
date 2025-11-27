package tp05.templateMethod2.template;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SQLQueryTemplate. Utilise le pattern "template method" pour exécuter une
 * requête SQL dans une connexion, en gérant correctement ouverture et fermeture
 * de connexion, et en renvoyant le résultat.
 * <p>
 * Note : cette classe, comme elle ouvre systématiquement une connexion pour
 * chaque requête, est assez peu efficace.
 */
public abstract class SQLQueryTemplate<T> {
    private final String sql;

    protected SQLQueryTemplate(String sql) {
        this.sql = sql;
    }

    /**
     * Exécute une requête unique.
     * 
     * @param <T>
     * @param sql  : le code SQL de la requête (? pour chaque argument variable)
     * @param args : les arguments variables à passer.
     * @return le résultat de la requête.
     */
    public List<T> execute(final Object... args) {
        List<List<Object>> argList = Collections.singletonList(Arrays.asList(args));
        return execute(argList);
    }

    /**
     * Exécute une série de requêtes, pour chaque n-uplets dans une liste
     * d'arguments.
     * 
     * @param sql     la requête à exécuter
     * @param argList les valeurs à passer à cette requête.
     * @return
     */
    public List<T> execute(List<List<Object>> argList) {
        List<T> resultat = new SQLQueryTemplateAux(sql, argList).execute();
        return resultat;
    }

    protected abstract T extractData(ResultSet res) throws SQLException;

    private class SQLQueryTemplateAux extends SQLStatementTemplate<List<T>> {

        public SQLQueryTemplateAux(String sql, List<List<Object>> argList) {
            super(sql, argList);
        }

        @Override
        protected List<T> initResult() {
            return new ArrayList<>();
        }

        @Override
        protected void performStatement(List<T> currentResult, PreparedStatement pst) throws SQLException {
            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    currentResult.add(extractData(resultSet));
                }
            }
        }
    }
}