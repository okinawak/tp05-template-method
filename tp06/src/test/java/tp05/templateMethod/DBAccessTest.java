package tp05.templateMethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

// Note : la base de données est détruite et recréée à chaque test,
// donc ils sont indépendants.
// 
// On pourrait aussi :
//
// - avoir deux bases, une en mémoire pour le test, l'autre persistante pour l'application (plus réaliste)
// - travailler sur la base persistante, mais faire chaque test dans une transaction, avec
//   rollback à la fin.
public class DBAccessTest {

    @Before
    public void init() throws SQLException {
        DbDefinition db = new DbDefinition();
        db.initialiserDB();
    }

    @Test
    public void retourneContenuDB() throws SQLException {
        DbDefinition db = new DbDefinition();
        String attendu = "1 Alan Turing\n2 Ada Lovelace\n3 Niklaus Wirth\n4 Charles Babbage\n5 John von Neumann\n";
        String contenu = db.retourneContenuDB();
        assertEquals(attendu, contenu);
    }


    @Test
    public void testGetById() throws SQLException {
        EtudiantDAO dao = new EtudiantDAO();
        String attendu = "Ada Lovelace";
        Etudiant e = dao.findById(2l);
        assertEquals(attendu, e.getNom());
    }

    @Test
    public void testGetByIdNull() throws SQLException {
        EtudiantDAO dao = new EtudiantDAO();
        Etudiant e = dao.findById(2000l);
        assertNull(e);
    }
    
    @Test
    public void testInsererEtudiant() throws SQLException {
        EtudiantDAO dao = new EtudiantDAO();
        Etudiant e = new Etudiant(10l, "test");
        dao.save(e);
        String attendu = "test";
        Etudiant res = dao.findById(10l);
        assertEquals(attendu, res.getNom());
    }

    @Test
    public void testInsererEtudiantBis() throws SQLException {
        EtudiantDAO dao = new EtudiantDAO();
        Etudiant e = new Etudiant(10l, "test");
        dao.save(e);
        String attendu = "test";
        Etudiant res = dao.findById(10l);
        assertEquals(attendu, res.getNom());
    }


    @Test
    public void testEnleverEtudiant() throws SQLException {
        EtudiantDAO dao = new EtudiantDAO();
        dao.delete(1l);
        Etudiant res = dao.findById(1l);
        assertNull(res);
    }

    @Test
    public void testUpdateEtudiant() throws SQLException {
        EtudiantDAO dao = new EtudiantDAO();
        Etudiant e = dao.findById(2l);    
        assertEquals("Ada Lovelace", e.getNom());
        e.setNom("A. Lovelace");
        dao.update(e);
        Etudiant res = dao.findById(2l);
        assertEquals("A. Lovelace", res.getNom());
    }
}
