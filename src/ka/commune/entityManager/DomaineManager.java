package ka.commune.entityManager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import ka.commune.business.App;
import ka.commune.entity.Domaine;

public class DomaineManager extends AbstractEntityManager<Domaine> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Domaine> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select d from Domaine d");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Domaine> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select d from Domaine d where d.designationDomaine= :db");
		query.setParameter("db", critere);
		return query.getResultList();
	}

	@Override
	public Domaine findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select m from Mandat m where m.idMandat= :id");
		query.setParameter("id", id);
		return (Domaine) query.getSingleResult();
	}

}
