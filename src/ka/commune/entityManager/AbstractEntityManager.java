package ka.commune.entityManager;

import javax.persistence.EntityManager;

public abstract class AbstractEntityManager<T> implements IEntityManager<T>{
	protected EntityManager em;
	
	public AbstractEntityManager() {
		// TODO Auto-generated constructor stub
		em=EMF.getEntityManagerFactory().createEntityManager();
	}
	
	@Override
	public T add(T entity) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		try {
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			em.getTransaction().begin();
			em.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		return entity;
	}
	
	@Override
	public boolean delete(T entity) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		try {
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(!em.getTransaction().isActive())
				em.getTransaction().begin();
			em.getTransaction().rollback();
			return false;
		}
		return true;
	}
	
	
	@Override
	public T update(T entity) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		try {
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			em.getTransaction().begin();
			em.getTransaction().rollback();
			return null;
		}
		return entity;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

}
