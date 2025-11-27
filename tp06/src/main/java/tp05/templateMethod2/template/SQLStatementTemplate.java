package tp05.templateMethod2.template;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe de base pour implémenter les Query et les Updates.
 * Utilise le pattern "template method".
 * 
 */
public abstract class SQLStatementTemplate<T> extends SQLConnectionTemplate<T> {

    private final String sql;

    private final List<List<Object>> argList;

    protected SQLStatementTemplate(final String sql, final List<List<Object>> argList) {
        this.sql = sql;
        this.argList = argList;
    }

    /**
     * Crée un objet dans lequel on accumulera le résultat.
     * Cet objet sera renvoyé à la fin de la requête.
     * @return
     */
    protected abstract T initResult();

    @Override
    protected final T doQuery(final Connection db) throws SQLException {
        T result = initResult();
        try (PreparedStatement pst = db.prepareStatement(sql)) {
            for (final List<Object> args : argList) {
                // Pour chaque t-uple d'arguments...
                for (int i = 0; i < args.size(); i++) {
                    pst.setObject(i + 1, args.get(i));
                }
                performStatement(result, pst);
            }
            result = performAtEnd(db, result);
            return result;
        }
    }

    /**
     * Méthode (optionnelle) appelée à la fin de la template.
     * <p>Permet de recalculer le résultat après coup, par exemple 
     * en exécutant une nouvelle requête.
     * 
     * <p> Cette méthode a été introduite essentiellement pour pouvoir 
     * exécuter un select last_insert_id() (ou assimilé) après un insert.
     * 
     * <p> Par défaut, ne touche pas au résultat.
     * @param db
     * @param result
     * @return le résultat, éventuellement modifié.
     */
    protected T performAtEnd(Connection db, T result) throws SQLException {
        return result;
    }

    /**
     * Exécute le preparedStatement pour la requête SQL passée en paramètre du
     * constructeur, initialisée avec un des t-uples d'arguments.
     * 
     * @param currentResult l'objet dans lequel on doit accumuler d'éventuels
     *                      résultats.
     * @param pst           le preparedStatement à exécuter.
     * @throws SQLException
     */
    protected abstract void performStatement(T currentResult, PreparedStatement pst) throws SQLException;

}