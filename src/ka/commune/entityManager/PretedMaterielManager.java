package ka.commune.entityManager;

import ka.commune.entity.PretedMateriel;
import javax.persistence.Query;
import java.util.List;

public class PretedMaterielManager extends AbstractEntityManager<PretedMateriel> {
    @Override
    public List<PretedMateriel> findAll() {
        Query query = em.createNamedQuery("PretedMateriel.findAll");
        return query.getResultList();
    }

    @Override
    public List<PretedMateriel> search(String critere) {
        return null;
    }

    @Override
    public PretedMateriel findById(int id) {
        Query query = em.createQuery("select p from PretedMateriel p where p.id= :id");
        query.setParameter("id", id);
        return (PretedMateriel) query.getSingleResult();
    }
}
