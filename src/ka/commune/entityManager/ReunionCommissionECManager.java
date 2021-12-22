package ka.commune.entityManager;

import ka.commune.entity.Association;
import ka.commune.entity.ReunionCommissionec;

import javax.persistence.Query;
import java.util.List;

public class ReunionCommissionECManager extends AbstractEntityManager<ReunionCommissionec>{
    @Override
    public List<ReunionCommissionec> findAll() {
        Query query = em.createNamedQuery("ReunionCommissionec.findAll");
        return query.getResultList();
    }

    @Override
    public List<ReunionCommissionec> search(String critere) {
        return null;
    }

    @Override
    public ReunionCommissionec findById(int id) {
        Query query = em.createQuery("select r from ReunionCommissionec r where r.idReunion= :id");
        query.setParameter("id", id);
        return (ReunionCommissionec) query.getSingleResult();
    }
}
