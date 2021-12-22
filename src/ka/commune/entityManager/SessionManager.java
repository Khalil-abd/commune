package ka.commune.entityManager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ka.commune.business.App;
import ka.commune.entity.Session;

public class SessionManager extends AbstractEntityManager<Session> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Session> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select s from Session s");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Session> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select s from Session s where s.designationSession= :ds");
		query.setParameter("ds", critere);
		return query.getResultList();
	}

	@Override
	public Session findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select s from Session s where s.idSession= :id");
		query.setParameter("id", id);
		return (Session) query.getSingleResult();
	}

}
