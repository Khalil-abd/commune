package ka.commune.entityManager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ka.commune.business.App;
import ka.commune.entity.Objetreunion;

public class ObjetReunionManager extends AbstractEntityManager<Objetreunion>{

	@SuppressWarnings("unchecked")
	@Override
	public List<Objetreunion> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createNamedQuery("Objetreunion.findAll");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Objetreunion> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select o from Objetreunion o where o.domaine.idDomaine= :id ");
		query.setParameter("id", critere);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Objetreunion> searchByDomaine(int critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select o from Objetreunion o where o.domaine.idDomaine= :id ");
		query.setParameter("id", critere);
		return query.getResultList();
	}

	@Override
	public Objetreunion findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select o from Objetreunion o where o.idObjetReunion= :id");
		query.setParameter("id", id);
		return (Objetreunion) query.getSingleResult();
	}
}
