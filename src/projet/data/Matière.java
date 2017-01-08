package projet.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Mati�re implements Serializable{
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique=true,nullable=false)
	private String nom;
	
	@ManyToMany(mappedBy="mati�res",fetch=FetchType.LAZY)	// LAZY = fetch when needed, EAGER = fetch immediately
	private List<Etudiant> etudiants; //tous les �tudiants de cette mati�re
	
	@OneToMany(mappedBy="mati�re",fetch=FetchType.LAZY)
	private List<Note> notes;
	
	private static final long serialVersionUID = 1L;

	public Mati�re() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}
}
