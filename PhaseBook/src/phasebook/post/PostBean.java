package phasebook.post;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import phasebook.user.PhasebookUser;

@Stateless
public class PostBean implements PostRemote {
	
	public void readUnreadPosts(PhasebookUser entry)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Post u WHERE u.toUser = :user AND u.read_ = :status");
		q.setParameter("user",entry);
		q.setParameter("status",false);
		
		try
		{
			result=q.getResultList();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Post post;
			for(Object object : result)
			{
				post = (Post)object;
				em.merge(post);
				post.setRead_(true);
				em.merge(post);
			}
			tx.commit();
			em.close();
			emf.close();
		}
		catch(NoResultException e)
		{
			em.close();
			emf.close();
			System.out.println("<Não foram encontrados posts por ler>");
		}
	}
	
	public void removePost(String myPostId)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		Post result = null;
		
		Query q = em.createQuery("SELECT u FROM Post u WHERE u.id = :postid");
		q.setParameter("postid",Integer.parseInt(myPostId));
		
		try
		{
			result=(Post) q.getSingleResult();
			EntityTransaction tx = em.getTransaction();
			tx.begin();

			em.merge(result);
			java.util.Date today = new java.util.Date();
			result.setDeletedAt(new Date(today.getTime()));
			em.merge(result);
				
			tx.commit();
			em.close();
			emf.close();
		}
		catch(NoResultException e)
		{
			em.close();
			emf.close();
			System.out.println("<Não foram encontrados posts por ler>");
		}		
	}
	
	public Object getUnreadPosts(PhasebookUser entry)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		List<Object> result = null;
		
		Query q = em.createQuery("SELECT u FROM Post u WHERE u.toUser = :user AND u.read_ = :readStatus");
		q.setParameter("user",entry);
		q.setParameter("readStatus", false);
		
		result=(List<Object>) q.getResultList();
		
		return result;
	}
	
}
