package ka.commune.entityManager;

import ka.commune.entity.PretMateriel;

import javax.persistence.Query;
import java.util.List;

public class PretMaterielManager extends AbstractEntityManager<PretMateriel>{
    @Override
    public List<PretMateriel> findAll() {
        Query query = em.createNamedQuery("PretMateriel.findAll");
        return query.getResultList();
    }

    @Override
    public List<PretMateriel> search(String critere) {
        return null;
    }

    @Override
    public PretMateriel findById(int id) {
        return null;
    }
}
