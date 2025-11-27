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
    String sql = "update etudiant set nom = ? where etudiant_id = ?";
    SQLSimpleHelper.update(sql, etudiant.getNom(), etudiant.getId());
}

public void delete(Long id) throws SQLException {
    String sql = "delete from Etudiant where etudiant_id = ?";
    SQLSimpleHelper.update(sql, id);
}

    public Etudiant findById(Long id) throws SQLException {
    String sql = "select * from Etudiant where etudiant_id = ?";
    List<Etudiant> resultats = new EtudiantQueryTemplate(sql).execute(id);
    
    if (resultats.isEmpty()) {
        return null;
    } else {
        return resultats.get(0);
    }
}

    public List<Etudiant> findAll() throws SQLException {
    String sql = "select * from Etudiant order by etudiant_id";
    return new EtudiantQueryTemplate(sql).execute();
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
