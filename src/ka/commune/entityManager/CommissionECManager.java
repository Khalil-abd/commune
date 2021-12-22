package ka.commune.entityManager;

import ka.commune.entity.Association;
import ka.commune.entity.CommissionEC;

import javax.persistence.Query;
import java.util.List;

public class CommissionECManager extends AbstractEntityManager<CommissionEC>{

    @Override
    public List<CommissionEC> findAll() {
        Query query = em.createNamedQuery("CommissionEC.findAll");
        return query.getResultList();
    }

    @Override
    public List<CommissionEC> search(String critere) {
        return null;
    }

    @Override
    public CommissionEC findById(int id) {
        Query query = em.createQuery("select c from CommissionEC c where c.idCommissionEC= :id");
        query.setParameter("id", id);
        return (CommissionEC) query.getSingleResult();
    }
}
