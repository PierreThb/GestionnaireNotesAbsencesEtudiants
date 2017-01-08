package projet.data;

import javax.persistence.EntityManager;

public class NoteDAO {
	
	public static Note retrieveById(int idNote){
		EntityManager em = GestionFactory.factory.createEntityManager();
		Note note = em.find(Note.class, idNote);
		return note;
	}
	
	public static Note create(Integer valeur, Matière matière, Etudiant etudiant){
		EntityManager manager = GestionFactory.factory.createEntityManager();
		manager.getTransaction().begin();
		
		Note note = new Note();
		note.setEtudiant(etudiant);
		note.setMatière(matière);
		note.setValeur(valeur);
		manager.persist(note);
		
		manager.getTransaction().commit();
		manager.close();
		
		return note;
	}
	
public static Note update(Note note) {
		
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();

		em.merge(note);
		em.getTransaction().commit();
		em.close();
		
		return note;
	}
}
