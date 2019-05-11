package org.theplu.entities;

import java.io.Serializable;

public class Filter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String photo_name;
	private String author_name;
	private String keyword;
	private int category_id;
	private String sort_param;
	private String sort_type;
	
	public Filter() {
		
	}

	public String getPhoto_name() {
		return photo_name;
	}

	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getSort_param() {
		return sort_param;
	}

	public void setSort_param(String sort_param) {
		this.sort_param = sort_param;
	}

	public String getSort_type() {
		return sort_type;
	}

	public void setSort_type(String sort_type) {
		this.sort_type = sort_type;
	}
}
