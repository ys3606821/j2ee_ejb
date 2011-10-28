package phasebook.friendship;

import javax.ejb.Remote;

import phasebook.user.PhasebookUser;

@Remote
public interface FriendshipRemote {

	
	public int friendshipStatus(PhasebookUser user_a, PhasebookUser user_b);
	public Friendship searchFriendship(PhasebookUser user_a, PhasebookUser user_b);
	public void acceptFriendship(PhasebookUser toUser, PhasebookUser fromUser);
	public Object getNewFriendshipAcceptances(PhasebookUser entry);
	public Object getNewFriendshipInvites(PhasebookUser entry);
	public void readUnreadFriendshipAcceptances(PhasebookUser entry);
	public void readUnreadFriendshipInvites(PhasebookUser entry);
	
}
