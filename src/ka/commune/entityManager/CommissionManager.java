package ka.commune.entityManager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ka.commune.business.App;
import ka.commune.entity.Commission;

public class CommissionManager extends AbstractEntityManager<Commission> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Commission> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select c from Commission c");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commission> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select c from Commission c where c.designationCommission= :dc");
		query.setParameter("dc", critere);
		return query.getResultList();
	}

	@Override
	public Commission findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select c from Commission c where c.idCommission= :id");
		query.setParameter("id", id);
		return (Commission) query.getSingleResult();
	}

}
