package ka.commune.entityManager;

import ka.commune.entity.Association;

import javax.persistence.Query;
import java.util.List;

public class AssociationManager extends AbstractEntityManager<Association> {
    @Override
    public List<Association> findAll() {
        Query query = em.createNamedQuery("Association.findAll");
        return query.getResultList();
    }

    @Override
    public List<Association> search(String critere) {
        return null;
    }

    @Override
    public Association findById(int id) {
        Query query = em.createQuery("select a from Association a where a.idAssociation= :id");
        query.setParameter("id", id);
        return (Association) query.getSingleResult();
    }
}
