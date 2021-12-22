package ka.commune.entityManager;

import java.util.List;
import javax.persistence.Query;
import ka.commune.entity.PretSalle;

public class PretSalleManager extends AbstractEntityManager<PretSalle> {

	public PretSalleManager() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PretSalle> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createNamedQuery("PretSalle.findAll");
		return query.getResultList();
	}

	@Override
	public List<PretSalle> search(String critere) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PretSalle findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select ps from PretSalle ps where s.idPretSalle= :id");
		query.setParameter("id", id);
		return (PretSalle) query.getSingleResult();
	}

	

	

	

}
