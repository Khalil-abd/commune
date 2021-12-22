package ka.commune.entityManager;

import ka.commune.entity.NiveauScolaire;
import javax.persistence.Query;
import java.util.List;

public class NiveauScolaireManager extends AbstractEntityManager<NiveauScolaire>{

    public NiveauScolaireManager() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NiveauScolaire> findAll() {
        Query query = em.createQuery("select n from NiveauScolaire n");
        return query.getResultList();
    }

    @Override
    public List<NiveauScolaire> search(String critere) {
        return null;
    }

    @Override
    public NiveauScolaire findById(int id) {
        Query query = em.createQuery("select n from NiveauScolaire n where n.idNiveauScolaire= :id");
        query.setParameter("id", id);
        return (NiveauScolaire) query.getSingleResult();
    }
}
