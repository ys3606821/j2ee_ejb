package phasebook.post.photo;

import javax.persistence.Column;
import javax.persistence.Entity;

import phasebook.post.Post;
import phasebook.user.PhasebookUser;

@Entity
public class Photo extends Post{

	private static final long serialVersionUID = 1L;
	
	@Column(name="POST_ID")
	private int postId;
	@Column(name="URL")
	private PhasebookUser url;
	@Column(name="LABEL")
	private String label;
	
	protected Photo()
	{
		super();
	}
	
	protected Photo(int postId, PhasebookUser url, String label) {
		super();
		this.postId = postId;
		this.url = url;
		this.label = label;
	}

	protected int getPostId() {
		return postId;
	}

	protected void setPostId(int postId) {
		this.postId = postId;
	}

	protected PhasebookUser getUrl() {
		return url;
	}

	protected void setUrl(PhasebookUser url) {
		this.url = url;
	}

	protected String getLabel() {
		return label;
	}

	protected void setLabel(String label) {
		this.label = label;
	}
	
	
	
}