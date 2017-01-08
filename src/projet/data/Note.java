package projet.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Note implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable=false)
	private Integer valeur;
	
	@OneToOne
	private Matière matière;
	
	@OneToOne
	private Etudiant etudiant;
	
	private static final long serialVersionUID = 1L;
	
	public Note() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		this.valeur = valeur;
	}

	public Matière getMatière() {
		return matière;
	}

	public void setMatière(Matière matière) {
		this.matière = matière;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
}
