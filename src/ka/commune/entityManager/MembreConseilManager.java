package ka.commune.entityManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ka.commune.business.App;
import ka.commune.entity.Membreconseil;

		public class MembreConseilManager extends AbstractEntityManager<Membreconseil> {

			public MembreConseilManager() {
				// TODO Auto-generated constructor stub
				super();
			}

			@SuppressWarnings("unchecked")
			@Override
			public List<Membreconseil> findAll() {
				// TODO Auto-generated method stub
				Query query;
				List<Membreconseil> list=null;
				query = em.createQuery("select mc from Membreconseil mc");
				list=query.getResultList();
				return list;
			}

			@SuppressWarnings("unchecked")
			@Override
			public List<Membreconseil> search(String critere) {
				return null;
			}

			@Override
			public Membreconseil findById(int id) {
				// TODO Auto-generated method stub
				Query query = em.createQuery("select m from Membreconseil m where m.idMembreConseil= :id");
				query.setParameter("id", id);
				return (Membreconseil) query.getSingleResult();
			}

		}
