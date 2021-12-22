package ka.commune.entityManager;

import ka.commune.entity.Materiel;

import javax.persistence.Query;
import java.util.List;

public class MaterielManger extends AbstractEntityManager<Materiel>{

    public MaterielManger() {
        super();
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Materiel> findAll() {
        Query query = em.createNamedQuery("Materiel.findAll");
        return query.getResultList();
    }

    @Override
    public List<Materiel> search(String critere) {
        return null;
    }

    @Override
    public Materiel findById(int id) {
        Query query = em.createQuery("select m from Materiel m where m.idMateirel= :id");
        query.setParameter("id", id);
        return (Materiel) query.getSingleResult();
    }
}
