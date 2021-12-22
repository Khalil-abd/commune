package ka.commune.entityManager;

import ka.commune.entity.Cansistance;
import javax.persistence.Query;
import java.util.List;

public class CansistanceManager extends AbstractEntityManager<Cansistance> {
    @Override
    public List<Cansistance> findAll() {
        Query query = em.createNamedQuery("Cansistance.findAll");
        return query.getResultList();
    }

    @Override
    public List<Cansistance> search(String critere) {
        return null;
    }

    @Override
    public Cansistance findById(int id) {
        Query query = em.createQuery("select c from Cansistance c where c.idCansistance= :id");
        query.setParameter("id", id);
        return (Cansistance) query.getSingleResult();
    }
}
