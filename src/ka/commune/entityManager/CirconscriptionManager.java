package ka.commune.entityManager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import ka.commune.business.App;
import ka.commune.entity.Circonscription;

public class CirconscriptionManager extends AbstractEntityManager<Circonscription> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Circonscription> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createNamedQuery("Circonscription.findAll");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Circonscription> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select c from Circonscription c where c.designationCirconscription= :dc or c.numero= :num");
		query.setParameter("dc", critere);
		query.setParameter("num", critere);
		return query.getResultList();
	}


	@Override
	public Circonscription findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select c from Circonscription c where c.idCirconscription= :id");
		query.setParameter("id", id);
		return (Circonscription) query.getSingleResult();
	}

}
