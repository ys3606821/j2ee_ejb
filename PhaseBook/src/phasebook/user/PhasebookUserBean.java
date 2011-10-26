package phasebook.user;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import phasebook.friendship.Friendship;
import phasebook.photo.Photo;
import phasebook.post.Post;


/**
 * Session Bean implementation class PhasebookUserBean
 */
@Stateless
public class PhasebookUserBean implements PhasebookUserRemote {

    /**
     * Default constructor. 
     */
    public PhasebookUserBean() {
    }
	
	public int create(String name, String email, String password) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
    	PhasebookUser user = new PhasebookUser(name, email, password);
		em.persist(user);
		em.refresh(user);
		tx.commit();
		return user.getId();
	}
	
	/* (non-Javadoc)
	 * @see phasebook.user.PhasebookUserRemote#login(java.lang.String, java.lang.String)
	 */
	public int login(String email, String password) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		try {
			Query q = em.createQuery("SELECT u FROM PhasebookUser u " +
						"WHERE u.email LIKE :email AND " +
						"u.password LIKE :password");
			q.setParameter("email",email);
			q.setParameter("password",password);
			
			PhasebookUser user = ((PhasebookUser)q.getSingleResult());
			
			em.merge(user);
			em.refresh(user);
			
			return user.getId();
		} catch(NoResultException ex){
			return -1;
		} catch(NonUniqueResultException ex){
			return -1;
		}
	}
	
	public List<Post> getUserReceivedPosts(Object userId){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(userId.toString()));
		em.persist(user);
		em.refresh(user);
		List<Post> userReceivedPosts = ((PhasebookUser)user).getReceivedPosts();
		List<Post> returnList = new ArrayList<Post>();
		for(int i = 0; i< userReceivedPosts.size(); i++){
			returnList.add(userReceivedPosts.get(i));
		}
		return returnList;
	}
	
	public List getUserPublicPosts(Object userId){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(userId.toString()));
		
		try{
			Query q = em.createQuery("SELECT u FROM Post u " +
					"WHERE u.toUser LIKE :user AND " +
					"u.private_ = :private_");
			q.setParameter("user",user);
			q.setParameter("private_",false);
			
			return q.getResultList();
		} catch(NoResultException e){
			List<Post> empty = new ArrayList<Post>();
			return empty;
		}
	}
	
	public List<Photo> getUserPhotos(Object userId){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(userId.toString()));
		em.persist(user);
		em.refresh(user);
		List<Photo> userPhotos = ((PhasebookUser)user).getUserPhotos();
		List<Photo> returnList = new ArrayList<Photo>();
		for(int i = 0; i< userPhotos.size(); i++){
			returnList.add(userPhotos.get(i));
		}
		return returnList;
	}
	
	public List getUserPublicPhotos(Object userId){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(userId.toString()));
		
		try{
			Query q = em.createQuery("SELECT u FROM Photo u " +
					"WHERE u.user LIKE :user AND " +
					"u.private_ = :private_");
			q.setParameter("user",user);
			q.setParameter("private_",false);
			
			return q.getResultList();
		} catch(NoResultException e){
			List<Photo> empty = new ArrayList<Photo>();
			return empty;
		}
	}
	
	public PhasebookUser getUserById(Object id){
		int userId = Integer.parseInt(id.toString());
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		try {
			PhasebookUser user = em.find(PhasebookUser.class, userId);
			em.persist(user);
			em.refresh(user);
			return user;
		} catch(NoResultException ex){
			return null;
		} catch(NonUniqueResultException ex){
			return null;
		}
	}
	
	public List getUsersFromSearch(Object search) {
		List users = new ArrayList();
		List results = new ArrayList();
		String s = search.toString();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		try {
			Query q = em.createQuery("SELECT u FROM PhasebookUser u ");
			users = q.getResultList();
			if (s != null)
			{
				Pattern pattern = Pattern.compile(s);
				for (int i=0; i<users.size(); i++)
				{
					PhasebookUser user = (PhasebookUser)users.get(i);
					boolean found = false;
					Matcher matcher = pattern.matcher(user.getName());
					if (matcher.find())
						found = true;
					matcher = pattern.matcher(user.getEmail());
					if (matcher.find())
						found = true;
					if (found)
						results.add(user);
				}
			}
			return results;
		} catch(NoResultException ex){
			return users;
		} 
	}
	
	public void addPost(PhasebookUser from, PhasebookUser to, String text, String privacy){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		System.out.println("\n\n\n\n\n"+privacy+"\n\n\n\n");
    	Post post = new Post(from, to, text, privacy);
		em.persist(post);
		em.refresh(post);
		tx.commit();
	}
	
	public void addPost(PhasebookUser from, PhasebookUser to, String text, String photoLink, String privacy){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Photo photo = new Photo(photoLink); 
		em.persist(photo);
		em.refresh(photo);
		
    	Post post = new Post(from, to, text, photo, privacy);
		em.persist(post);
		em.refresh(post);
		
		tx.commit();
	}
	
	public Photo addPhoto(PhasebookUser user, String text, String photoLink, String privacy){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Photo photo = new Photo(photoLink, user, privacy, text); 
		em.persist(photo);
		em.refresh(photo);

		tx.commit();
		return photo;
	}
	
	public void invite(PhasebookUser hostUser, PhasebookUser invitedUser)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
    	Friendship fship = new Friendship(hostUser, invitedUser);
		em.persist(fship);
		em.refresh(fship);
		tx.commit();
		
	}
	
	public void setProfilePicture(PhasebookUser user, Photo photo)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		em.merge(user);
		user.setPhoto(photo);
		em.merge(user);
		tx.commit();
		
	}

}
