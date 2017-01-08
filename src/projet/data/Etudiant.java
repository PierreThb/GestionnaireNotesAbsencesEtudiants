package projet.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Etudiant implements Serializable{
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable=false) 
	private String prenom;
	
	@Column(nullable=false)
	private String nom;
	
	private int nbAbsences;
	
	@ManyToOne
	private Groupe groupe;
	
	@ManyToMany
	private List<Mati�re> mati�res;
	
	private static final long serialVersionUID = 1L;

	public Etudiant() {
		super();
		nbAbsences = 0;
	}  
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}   
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}   
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Groupe getGroupe() {
		return this.groupe;
	}
	
	public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
        if (!groupe.getEtudiants().contains(this)) {
        	groupe.getEtudiants().add(this);
        }
    }
	
	public List<Mati�re> getMati�res() {
		return mati�res;
	}

	public void setMati�res(List<Mati�re> mati�res) {
		this.mati�res = mati�res;
	}

	public int getNbAbsences() {
		return nbAbsences;
	}

	public void setNbAbsences(int nbAbsences) {
		this.nbAbsences = nbAbsences;
	}

	@Override
	public String toString() {
		return "[" + this.getId() + "] " + this.getPrenom() + " " + this.getNom();
	}
}
