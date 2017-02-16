package projet.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class MatièreDAO {
	
	public static Matière create(String nom){
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();
		Matière matière = new Matière();
		matière.setNom(nom);
		em.persist(matière);
		em.getTransaction().commit();
		em.close();
		return matière;
	}
	
	public static List<Matière> getAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT m FROM Matière m");
		@SuppressWarnings("unchecked")
		List<Matière> listMatière = q.getResultList();
		
		return listMatière;
	}
	
	public static List<Matière> getAllByGroupe(Groupe groupe){
		EntityManager em = GestionFactory.factory.createEntityManager();
		Query q = em.createQuery("SELECT m FROM Matière m WHERE m.groupe =:groupe");
		q.setParameter("groupe", groupe);
		@SuppressWarnings("unchecked")
		List<Matière> matières = q.getResultList();
		return matières;
	}
	
	public static Matière update(Matière matière){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		//
		em.getTransaction().begin();

		// Attacher une entitÃ© persistante (etudiant) Ã  lâ€™EntityManager courant
		em.merge(matière);
		
		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();
		
		return matière;
	}
	
	public static Matière getByNom(String nomMatiere){
		EntityManager em = GestionFactory.factory.createEntityManager();
		Query query = em.createQuery("SELECT m FROM Matière m WHERE m.nom =:nom");
		query.setParameter("nom", nomMatiere);
		Matière matière = (Matière)query.getSingleResult();
		return matière;
	}
}
