package ka.commune.entityManager;

import java.util.List;
import javax.persistence.Query;
import ka.commune.entity.Salle;

public class SalleManager extends AbstractEntityManager<Salle> {

	
	public SalleManager() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Salle> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createNamedQuery("Salle.findAll");
		return query.getResultList();
	}

	@Override
	public List<Salle> search(String critere) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Salle findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select s from Salle s where s.idSalle= :id");
		query.setParameter("id", id);
		return (Salle) query.getSingleResult();
	}

}
