package org.theplu.entities;

import java.io.Serializable;

public class Buying implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int buyer_id;
	private int seller_id;
	private int picture_id;
	private int price;
	
	public Buying() {
		
	}
	
	public int getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(int buyer_id) {
		this.buyer_id = buyer_id;
	}
	public int getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(int seller_id) {
		this.seller_id = seller_id;
	}
	public int getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(int picture_id) {
		this.picture_id = picture_id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
