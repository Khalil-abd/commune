package ka.commune.entityManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ka.commune.business.App;
import ka.commune.entity.Mandat;

public class MandatManager extends AbstractEntityManager<Mandat>{

	
	public MandatManager() {
		// TODO Auto-generated constructor stub
		super();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Mandat> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select m from Mandat m");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mandat> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select m from Mandat m where m.dateDebut= :db or m.dateFin= :df");
		query.setParameter("db", critere);
		query.setParameter("df", critere);
		return query.getResultList();
	}

	@Override
	public Mandat findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select m from Mandat m where m.idMandat= :id");
		query.setParameter("id", id);
		return (Mandat) query.getSingleResult();
	}

}
