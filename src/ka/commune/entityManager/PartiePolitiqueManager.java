package ka.commune.entityManager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import ka.commune.business.App;
import ka.commune.entity.Partiepolitique;

public class PartiePolitiqueManager extends AbstractEntityManager<Partiepolitique> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Partiepolitique> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select p from Partiepolitique p");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Partiepolitique> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select p from Partiepolitique p where p.designationPartiePolitique= :dpp");
		query.setParameter("dpp", critere);
		return query.getResultList();
	}

	@Override
	public Partiepolitique findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select p from Partiepolitique p where p.idPartiePolitique= :id");
		query.setParameter("id", id);
		return (Partiepolitique) query.getSingleResult();
	}

}
