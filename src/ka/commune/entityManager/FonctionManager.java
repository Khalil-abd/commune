package ka.commune.entityManager;

import ka.commune.entity.Fonction;
import javax.persistence.Query;
import java.util.List;

public class FonctionManager extends AbstractEntityManager<Fonction>{

    public FonctionManager() {
        super();
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Fonction> findAll() {
        Query query = em.createQuery("select f from Fonction f");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Fonction> search(String critere) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Fonction findById(int id) {
        Query query = em.createQuery("select f from Fonction f where f.idFonction= :id");
        query.setParameter("id", id);
        return (Fonction) query.getSingleResult();
    }
}
