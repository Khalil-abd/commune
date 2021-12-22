package ka.commune.entityManager;

import ka.commune.entity.Realisation;

import javax.persistence.Query;
import java.util.List;

public class RealisationManager extends AbstractEntityManager<Realisation>{

    @Override
    public List<Realisation> findAll() {
        Query query = em.createNamedQuery("Realisation.findAll");
        return query.getResultList();
    }

    @Override
    public List<Realisation> search(String critere) {
        return null;
    }

    @Override
    public Realisation findById(int id) {
        Query query = em.createQuery("select r from Realisation r where r.idRealisation= :id");
        query.setParameter("id", id);
        return (Realisation) query.getSingleResult();
    }
}
