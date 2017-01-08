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
}
