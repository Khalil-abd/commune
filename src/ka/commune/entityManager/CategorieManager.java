package ka.commune.entityManager;

import ka.commune.entity.Categorie;

import javax.persistence.Query;
import java.util.List;

public class CategorieManager extends AbstractEntityManager<Categorie>{
    @Override
    public List<Categorie> findAll() {
        Query query = em.createNamedQuery("Categorie.findAll");
        return query.getResultList();
    }

    @Override
    public List<Categorie> search(String critere) {
        return null;
    }

    @Override
    public Categorie findById(int id) {
        Query query = em.createQuery("select c from Categorie c where c.idCategorie= :id");
        query.setParameter("id", id);
        return (Categorie) query.getSingleResult();
    }
}
