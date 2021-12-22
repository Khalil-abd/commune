package ka.commune.entityManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ka.commune.business.App;
import ka.commune.entity.User;

public class UserManager extends AbstractEntityManager<User>{
	
	
	public UserManager()
	{
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select u from User u");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> search(String critere) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select u from User u where u.login= :login");
		query.setParameter("login", critere);
		return query.getResultList();
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select u from User u where u.idUser= :id");
		query.setParameter("id", id);
		return (User) query.getSingleResult();
	}
	
	public User login(String login , String password) throws NoResultException
	{
		Query query;
		User user=null;
		try
		{
			query = em.createQuery("select u from User u where u.login= :login and u.password=:password");
			query.setParameter("login", login);
			query.setParameter("password", password);
			user=(User)query.getSingleResult();
			em.clear();
			em.close();
		}catch (Exception e)
		{
			return null;
		}
		finally {
			
		}
		return user;
	}
	
}
