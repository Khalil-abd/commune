package ka.commune.entityManager;

import ka.commune.entity.Categorie;
import ka.commune.entity.PartenaireOperation;

import javax.persistence.Query;
import java.util.List;

public class PartenaireOperationManager extends AbstractEntityManager<PartenaireOperation> {

    @Override
    public List<PartenaireOperation> findAll() {
        Query query = em.createNamedQuery("PartenaireOperation.findAll");
        return query.getResultList();
    }

    @Override
    public List<PartenaireOperation> search(String critere) {
        return null;
    }

    @Override
    public PartenaireOperation findById(int id) {
        Query query = em.createQuery("select c from PartenaireOperation c where c.idpartenaire= :id");
        query.setParameter("id", id);
        return (PartenaireOperation) query.getSingleResult();
    }
}
