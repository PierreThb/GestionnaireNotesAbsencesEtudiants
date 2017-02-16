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
public class Groupe implements Serializable{
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique=true, nullable=false) 
	private String nom;
	
	@OneToMany(mappedBy="groupe", fetch=FetchType.LAZY)	// LAZY = fetch when needed, EAGER = fetch immediately
	private List<Etudiant> etudiants;
	
	@ManyToMany
	private List<Matière> matières;
	
	private static final long serialVersionUID = 1L;

	public Groupe() {
		super();
	}   
	public Integer getId() {
		return this.id;
	}
	
	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}
	public void setId(Integer id) {
		this.id = id;
	}   
	
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom.toUpperCase();
	}
	
	public List<Etudiant> getEtudiants() {
		return this.etudiants;
	}
	public List<Matière> getMatières() {
		return matières;
	}
	public void setMatières(List<Matière> matières) {
		this.matières = matières;
	}
	
	public void addMatière(Matière matière){
		this.matières.add(matière);
	}
}
