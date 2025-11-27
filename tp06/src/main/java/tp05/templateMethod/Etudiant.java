package tp05.templateMethod;

public class Etudiant {
    Long id;
    String nom;

    public Etudiant(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }


}
