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
	private Mati�re mati�re;
	
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

	public Mati�re getMati�re() {
		return mati�re;
	}

	public void setMati�re(Mati�re mati�re) {
		this.mati�re = mati�re;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
}
