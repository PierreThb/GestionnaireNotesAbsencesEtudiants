package projet.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	
	public static Note getNoteByEtudiantMatière(Etudiant etudiant, Matière matière){
		EntityManager em = GestionFactory.factory.createEntityManager();
		Query q = em.createQuery("SELECT n FROM Note n WHERE n.etudiant=:etudiant AND n.matière=:matière");
		q.setParameter("etudiant", etudiant);
		q.setParameter("matière", matière);
		Note note = (Note) q.getSingleResult();
		return note;
	}
	
	public static List<Note> getNotesForOneEtudiant(Etudiant etudiant){
		EntityManager em = GestionFactory.factory.createEntityManager();
		Query q = em.createQuery("SELECT n FROM Note n WHERE n.etudiant=:etudiant");
		q.setParameter("etudiant", etudiant);
		List<Note> notes = q.getResultList();
		return notes;
	}
}
