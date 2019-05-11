package org.theplu.entities;

import java.io.Serializable;

public class Comment implements Serializable{

	public Comment() {
		
	}

	public int getCommentator_id() {
		return commentator_id;
	}
	public void setCommentator_id(int commentator_id) {
		this.commentator_id = commentator_id;
	}
	public int getCommented_id() {
		return commented_id;
	}
	public void setCommented_id(int commented_id) {
		this.commented_id = commented_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getComment_id() {
		return comment_id;
	}
	
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int comment_id;
	private int commentator_id;
	private int commented_id;
	private String comment;
	
	public static final String COMMENT_ID = "comment_id";
	public static final String COMMENTATOR_ID = "commentator_id";
	public static final String COMMENTED_ID = "commented_id";
	public static final String COMMENT = "comment";

}
