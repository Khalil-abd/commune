package ka.commune.entityManager;

import ka.commune.entity.Operation;
import ka.commune.entity.Partenaire;
import javax.persistence.Query;
import java.util.List;

public class OperationManager extends AbstractEntityManager<Operation> {

    @Override
    public List<Operation> findAll() {
        Query query;
        List<Operation> list=null;
        query = em.createQuery("select o from Operation o");
        list=query.getResultList();
        return list;
    }

    @Override
    public List<Operation> search(String critere) {
        return null;
    }

    @Override
    public Operation findById(int id) {
        Query query = em.createQuery("select o from Operation o where o.idComptabilite= :id");
        query.setParameter("id", id);
        return (Operation) query.getSingleResult();
    }

    public List<Operation> findByPartenaire(Partenaire partenaire) {
        Query query = em.createQuery("select o from Operation o where o.partenaireBean= :id");
        query.setParameter("id", partenaire);
        List<Operation> list=null;
        list=query.getResultList();
        return list;
    }
}
