package phasebook.friendship;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Friendship implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)	
			
	
	private int ID, inviterUserID, inveteeUserID;
	private boolean accepted;
	private Date creationDate, deletionDate;
	
	
	protected Friendship()
	{
		super();
	}
	
	protected Friendship(int inviterUserID, int inveteeUserID) 
	{
		super();
		this.inviterUserID = inviterUserID;
		this.inveteeUserID = inveteeUserID;
	}
	
	private Date getCurrentTime()
	{		
		Calendar currenttime = Calendar.getInstance();
		return new Date((currenttime.getTime()).getTime());
	}

	protected int getID() {
		return ID;
	}

	protected void setID(int iD) {
		ID = iD;
	}

	protected int getInviterUserID() {
		return inviterUserID;
	}

	protected void setInviterUserID(int inviterUserID) {
		this.inviterUserID = inviterUserID;
	}

	protected int getInveteeUserID() {
		return inveteeUserID;
	}

	protected void setInveteeUserID(int inveteeUserID) {
		this.inveteeUserID = inveteeUserID;
	}

	protected boolean isAccepted() {
		return accepted;
	}

	protected void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	protected Date getCreationDate() {
		return creationDate;
	}

	protected void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	protected Date getDeletionDate() {
		return deletionDate;
	}

	protected void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	
	protected void deleteFriendship() {
		this.deletionDate=getCurrentTime();
	}
	
}
