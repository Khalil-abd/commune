package ka.commune.entityManager;

import java.util.List;
import javax.persistence.Query;
import ka.commune.entity.ActiviteBS;

public class ActiviteBSManager extends AbstractEntityManager<ActiviteBS> {
	
	public ActiviteBSManager() {
		// TODO Auto-generated constructor stub
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActiviteBS> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createNamedQuery("ActiviteBS.findAll");
		return query.getResultList();
	}

	@Override
	public List<ActiviteBS> search(String critere) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActiviteBS findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select a from ActiviteBS a where a.idActiviteBS= :id");
		query.setParameter("id", id);
		return (ActiviteBS) query.getSingleResult();
	}

}
