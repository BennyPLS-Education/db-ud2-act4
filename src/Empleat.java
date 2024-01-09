import java.io.Serializable;
import java.time.LocalDate;

public class Empleat implements Serializable {
    private int numero;
    private String nom;
    private String cognoms;
    private LocalDate alta;

    public Empleat(int numero, String nom, String cognoms, LocalDate alta) {
        this.numero = numero;
        this.nom = nom;
        this.cognoms = cognoms;
        this.alta = alta;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public LocalDate getAlta() {
        return alta;
    }
}
