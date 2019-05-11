package org.theplu.entities;

import java.io.Serializable;

public class Rate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int rater_id;
	private int rated_id;
	private int photo_id;
	private int rate;
	
	public Rate() {
		
	}
	public int getRater_id() {
		return rater_id;
	}
	public void setRater_id(int rater_id) {
		this.rater_id = rater_id;
	}
	public int getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getRated_id() {
		return rated_id;
	}
	public void setRated_id(int rated_id) {
		this.rated_id = rated_id;
	}
	
	
}
