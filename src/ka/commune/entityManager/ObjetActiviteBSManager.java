package ka.commune.entityManager;

import java.util.List;
import javax.persistence.Query;
import ka.commune.entity.ObjetActiviteBS;

public class ObjetActiviteBSManager extends AbstractEntityManager<ObjetActiviteBS> {

	public ObjetActiviteBSManager() {
		// TODO Auto-generated constructor stub
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ObjetActiviteBS> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createNamedQuery("ObjetActiviteBS.findAll");
		return query.getResultList();
	}

	@Override
	public List<ObjetActiviteBS> search(String critere) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjetActiviteBS findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select o from ObjetActiviteBS o where o.idObjetActiviteBS= :id");
		query.setParameter("id", id);
		return (ObjetActiviteBS) query.getSingleResult();
	}

}
