package phasebook.friendship;

import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import phasebook.user.PhasebookUser;

@Stateless
public class FriendshipBean implements FriendshipRemote {
	
	
	/**
	 * Checks what's the friendship status of two users.
	 *@return
	 *0 if user_a is not friend of user_b{@literal}<br>
	 *1 if user_a has sent a friendship request to user_b and user_b has not yet accepted it<br>
	 *2 if user_b has sent a friendship request to user_a and user_a has not yet accepted it<br>
	 *3 if user_a is friend of user_b <br>
	 *-1 if there's a database error
	 */
	public int friendshipStatus(PhasebookUser user_a, PhasebookUser user_b)
	{
		Friendship myFriendship = searchFriendship(user_a,user_b);
		
		if(myFriendship == null && !user_a.equals(user_b))
			return 0;
		else if(!myFriendship.isAccepted_() && myFriendship.getHostUser().equals(user_a))
			return 1;
		else if(!myFriendship.isAccepted_() && myFriendship.getHostUser().equals(user_b))
			return 2;
		else if(myFriendship.isAccepted_())
			return 3;
		else
			return -1;
	}
	
	/**
	 * Searches for the Friendship_ID of a friendship between two users
	 *@return
	 *Friendship<br>
	 *null if there's no friendship between the given users
	 */

	public Friendship searchFriendship(PhasebookUser user_a, PhasebookUser user_b)
	{
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		Friendship result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u " +
				"WHERE (u.hostUser = :user_a AND " +
				"u.invitedUser = :user_b) OR"+
				"(u.hostUser = :user_b AND " +
				"u.invitedUser = :user_a)");
		q.setParameter("user_a",user_a);
		q.setParameter("user_b",user_b);
		
		try
		{
			result = (Friendship) q.getSingleResult();
		}
		catch(NoResultException e)
		{
			System.out.println("<Não foram encontrados resultados>");
		}
		catch(NonUniqueResultException e)
		{
			System.out.println("<Foi encontrado mais de um resultado>");
		}
		finally
		{
			return result;
		}
	}

	/**
	 * Accepts a friendship request by the fromUser to the toUser 
	 */
	public void acceptFriendship(PhasebookUser toUser, PhasebookUser fromUser) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
    	Friendship friend = searchFriendship(toUser, fromUser);
    	em.merge(friend);
    	friend.setAccepted_(true);
    	em.merge(friend);
		tx.commit();	
	}

}
