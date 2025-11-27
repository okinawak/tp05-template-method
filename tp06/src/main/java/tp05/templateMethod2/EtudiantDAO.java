package tp05.templateMethod2;

import java.sql.*;
import java.util.List;
import tp05.templateMethod2.template.SQLSimpleHelper;

import tp05.templateMethod2.template.SQLQueryTemplate;


public class EtudiantDAO {

    public void save(Etudiant etudiant) throws SQLException {
        String sql = "insert into Etudiant values (?,?)";
        SQLSimpleHelper.insert(sql, etudiant.getId(), etudiant.getNom());
    }

    public void update(Etudiant etudiant) throws SQLException {
          // à compléter...
    }

    public void delete(Long id) throws SQLException {
        // à compléter...
    }

    public Etudiant findById(Long id) throws SQLException {
        // à compléter...
        return null;
    }

    public List<Etudiant> findAll() throws SQLException {
        // à compléter...
        return null;
    }

    private static class EtudiantQueryTemplate extends SQLQueryTemplate<Etudiant> {        
        protected EtudiantQueryTemplate(String sql) {
            super(sql);          
        }

        @Override
        protected Etudiant extractData(ResultSet res) throws SQLException {
            Long id = res.getLong("ETUDIANT_ID");
            String nom = res.getString("nom");
            return new Etudiant(id, nom);
        }            
    }
}
