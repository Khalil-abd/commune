package ka.commune.entityManager;

import ka.commune.entity.ActiviteType;

import javax.persistence.Query;
import java.util.List;

public class ActiviteTypeManager extends AbstractEntityManager<ActiviteType>{
    @Override
    public List<ActiviteType> findAll() {
        Query query = em.createNamedQuery("ActiviteType.findAll");
        return query.getResultList();
    }

    @Override
    public List<ActiviteType> search(String critere) {
        return null;
    }

    @Override
    public ActiviteType findById(int id) {
        Query query = em.createQuery("select r from ActiviteType r where r.idActiviteType= :id");
        query.setParameter("id", id);
        return (ActiviteType) query.getSingleResult();
    }
}
