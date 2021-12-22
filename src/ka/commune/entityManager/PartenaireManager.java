package ka.commune.entityManager;

import ka.commune.entity.Partenaire;
import javax.persistence.Query;
import java.util.List;

public class PartenaireManager extends AbstractEntityManager<Partenaire>{

    public PartenaireManager()
    {
        super();
    }
    @Override
    public List<Partenaire> findAll() {
        List<Partenaire> list=null;
        Query query = em.createQuery("select p from Partenaire p");
        list=query.getResultList();
        return list;
    }

    @Override
    public List<Partenaire> search(String critere) {
        return null;
    }

    @Override
    public Partenaire findById(int id) {
        Query query = em.createQuery("select p from Partenaire p where p.idPartenaire= :id");
        query.setParameter("id", id);
        return (Partenaire) query.getSingleResult();
    }

}
