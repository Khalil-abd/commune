package ka.commune.entityManager;

import ka.commune.entity.Resultat;

import javax.persistence.Query;
import java.util.List;

public class ResultatManager extends AbstractEntityManager<Resultat> {

    @Override
    public List<Resultat> findAll() {
        Query query = em.createNamedQuery("Resultat.findAll");
        return query.getResultList();
    }

    @Override
    public List<Resultat> search(String critere) {
        return null;
    }

    @Override
    public Resultat findById(int id) {
        Query query = em.createQuery("select r from Resultat r where r.idResultat= :id");
        query.setParameter("id", id);
        return (Resultat) query.getSingleResult();
    }
}
