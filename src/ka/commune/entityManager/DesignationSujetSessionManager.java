package ka.commune.entityManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import ka.commune.business.App;
import ka.commune.entity.Designationsujetsession;

public class DesignationSujetSessionManager extends AbstractEntityManager<Designationsujetsession> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Designationsujetsession> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select d from Designationsujetsession d");
		return query.getResultList();
	}

	@Override
	public List<Designationsujetsession> search(String critere) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Designationsujetsession findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select d from Designationsujetsession d where d.idDesignation= :id");
		query.setParameter("id", id);
		return (Designationsujetsession) query.getSingleResult();
	}

}
