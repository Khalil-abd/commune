package ka.commune.entityManager;

import java.util.List;

public interface IEntityManager<T> {

	public T add(T entity);
	public boolean delete(T entity);
	public List<T> findAll();
	public List<T> search(String critere);
	public T update(T entity);
	public T findById(int id);
}
