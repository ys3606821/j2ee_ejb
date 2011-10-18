package phasebook.post.message;

import javax.persistence.Column;
import javax.persistence.Entity;

import phasebook.post.Post;

@Entity
public class Message extends Post {

	private static final long serialVersionUID = 1L;
	
	@Column(name="POST_ID")
	private int id;
	@Column(name="TEXT")
	private String text;
	
	
	protected Message()
	{
		super();
	}
	
	protected Message(int id, String text)
	{
		super();
		this.id=id;
		this.text=text;
	}

	protected int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected String getText() {
		return text;
	}

	protected void setText(String text) {
		this.text = text;
	}
	
	
	
}