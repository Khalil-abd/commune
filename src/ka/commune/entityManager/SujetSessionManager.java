package ka.commune.entityManager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ka.commune.business.App;
import ka.commune.entity.Sujetsession;

public class SujetSessionManager extends AbstractEntityManager<Sujetsession> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Sujetsession> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select ss from Sujetsession ss");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sujetsession> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select ss from Sujetsession ss where ss.designationSujetSession= :dss or ss.objet= :objet"
				+ " or ss.mois=:mois or ss.annee=:annee");
		query.setParameter("dss", critere);
		query.setParameter("objet", critere);
		query.setParameter("annee", critere);
		query.setParameter("mois", critere);
		return query.getResultList();
	}

	@Override
	public Sujetsession findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select m from Mandat m where m.idMandat= :id");
		query.setParameter("id", id);
		return (Sujetsession) query.getSingleResult();
	}

}
