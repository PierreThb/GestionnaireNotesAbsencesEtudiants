package projet.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Mati�reDAO {
	
	public static Mati�re create(String nom){
		EntityManager em = GestionFactory.factory.createEntityManager();
		em.getTransaction().begin();
		Mati�re mati�re = new Mati�re();
		mati�re.setNom(nom);
		em.persist(mati�re);
		em.getTransaction().commit();
		em.close();
		return mati�re;
	}
	
	public static List<Mati�re> getAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
				
		// Recherche 
		Query q = em.createQuery("SELECT m FROM Mati�re m");
		@SuppressWarnings("unchecked")
		List<Mati�re> listMati�re = q.getResultList();
		
		return listMati�re;
	}
	
	public static List<Mati�re> getAllByGroupe(Groupe groupe){
		EntityManager em = GestionFactory.factory.createEntityManager();
		Query q = em.createQuery("SELECT m FROM Mati�re m WHERE m.groupe =:groupe");
		q.setParameter("groupe", groupe);
		@SuppressWarnings("unchecked")
		List<Mati�re> mati�res = q.getResultList();
		return mati�res;
	}
	
	public static Mati�re update(Mati�re mati�re){
		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();
		
		//
		em.getTransaction().begin();

		// Attacher une entité persistante (etudiant) à l’EntityManager courant
		em.merge(mati�re);
		
		// Commit
		em.getTransaction().commit();

		// Close the entity manager
		em.close();
		
		return mati�re;
	}
	
	public static Mati�re getByNom(String nomMatiere){
		EntityManager em = GestionFactory.factory.createEntityManager();
		Query query = em.createQuery("SELECT m FROM Mati�re m WHERE m.nom =:nom");
		query.setParameter("nom", nomMatiere);
		Mati�re mati�re = (Mati�re)query.getSingleResult();
		return mati�re;
	}
}
