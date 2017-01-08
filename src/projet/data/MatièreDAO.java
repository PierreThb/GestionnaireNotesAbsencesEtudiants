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
}
