package ka.commune.entityManager;

import ka.commune.entity.ReunionAssociation;
import javax.persistence.Query;
import java.util.List;

public class ReunionAssociationManager extends AbstractEntityManager<ReunionAssociation>{
    @Override
    public List<ReunionAssociation> findAll() {
        Query query = em.createNamedQuery("ReunionAssociation.findAll");
        return query.getResultList();
    }

    @Override
    public List<ReunionAssociation> search(String critere) {
        return null;
    }

    @Override
    public ReunionAssociation findById(int id) {
        Query query = em.createQuery("select ra from ReunionAssociation ra where ra.idReunion= :id");
        query.setParameter("id", id);
        return (ReunionAssociation) query.getSingleResult();
    }
}
