package org.theplu.entities;

import org.theplu.utils.UtilsMethods;

public class Photo extends BasicEntity{

	public Photo() {
		super(PHOTO_ID);
		this.columnsName.add(USER_ID);
		this.columnsName.add(PHOTO_NAME);
		this.columnsName.add(PATH);
		this.columnsName.add(EXTENSION);
		this.columnsName.add(RELEASE_DATE);
		this.columnsName.add(SOLD_NR);
		this.columnsName.add(PRICE);
		this.columnsName.add(RATES_NR);
		this.columnsName.add(RATES_SUM);
		this.columnsName.add(PLACE);
		this.columnsName.add(DESCRIPTION);
		this.columnsName.add(IS_VERIFIED);
		this.columnsName.add(PICTURE_TYPE);
		this.columnsName.add(CATEGORTY);
	}
	
	public void setValueForColumnName(String columnName, Object value) {
		if(USER_ID.equals(columnName)) {
			this.setUser_id(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if( PHOTO_NAME.equals(columnName) ){
			this.setPhoto_name(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		
		if( PATH.equals(columnName) ){
			this.setPath(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		
		if(EXTENSION.equals(columnName)) {
			this.setExtension(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		if(RELEASE_DATE.equals(columnName)) {
			this.setRelease_date(UtilsMethods.saftyConversionLong(value));
			return;
		}
		if(SOLD_NR.equals(columnName)) {
			this.setSold_nr(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(PRICE.equals(columnName)) {
			this.setPrice(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(RATES_NR.equals(columnName)) {
			this.setRates_nr(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(RATES_SUM.equals(columnName)) {
			this.setRates_sum(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(PLACE.equals(columnName)) {
			this.setPlace(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		if(DESCRIPTION.equals(columnName)) {
			this.setDescription(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		if(IS_VERIFIED.equals(columnName)) {
			this.setIs_verified(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(PICTURE_TYPE.equals(columnName)) {
			this.setPicture_type(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(CATEGORTY.equals(columnName)) {
			this.setCategory(UtilsMethods.saftyConversionInt(value));
			return;
		}
		
		if(PHOTO_ID.equals(columnName)) {
			int id = UtilsMethods.saftyConversionInt(value);
			super.setId(id);
			return;
		}
		
		super.setValueForColumnName(columnName, value);
	}
	
	public Object getValueForColumnName(String columnName) {
		if(USER_ID.equals(columnName)) {
			return this.getUser_id();
		}
		if( PHOTO_NAME.equals(columnName) ){
			return this.getPhoto_name();
		}
		
		if( PATH.equals(columnName) ){
			return this.getPath();
		}
		
		if(EXTENSION.equals(columnName)) {
			return this.getExtension();
		}
		if(RELEASE_DATE.equals(columnName)) {
			return this.getRelease_date();
		}
		if(SOLD_NR.equals(columnName)) {
			return this.getSold_nr();
		}
		if(PRICE.equals(columnName)) {
			return this.getPrice();
		}
		if(RATES_NR.equals(columnName)) {
			return this.getRates_nr();
		}
		if(RATES_SUM.equals(columnName)) {
			return this.getRates_sum();
		}
		if(PLACE.equals(columnName)) {
			return this.getPlace();
		}
		if(DESCRIPTION.equals(columnName)) {
			return this.getDescription();
		}
		if(IS_VERIFIED.equals(columnName)) {
			return this.getIs_verified();
		}
		if(PICTURE_TYPE.equals(columnName)) {
			return this.getPicture_type();
		}
		if(CATEGORTY.equals(columnName)) {
			return this.getCategory();
		}
		
		if(PHOTO_ID.equals(columnName)) {
			return super.getId();
		}
		return super.getValueForColumnName(columnName);
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getPhoto_name() {
		return photo_name;
	}
	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public long getRelease_date() {
		return release_date;
	}
	public void setRelease_date(long release_date) {
		this.release_date = release_date;
	}
	public int getSold_nr() {
		return sold_nr;
	}
	public void setSold_nr(int sold_nr) {
		this.sold_nr = sold_nr;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getRates_nr() {
		return rates_nr;
	}
	public void setRates_nr(int rates_nr) {
		this.rates_nr = rates_nr;
	}
	public int getRates_sum() {
		return rates_sum;
	}
	public void setRates_sum(int rates_sum) {
		this.rates_sum = rates_sum;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIs_verified() {
		return is_verified;
	}
	public void setIs_verified(int is_verified) {
		this.is_verified = is_verified;
	}
	public int getPicture_type() {
		return picture_type;
	}
	public void setPicture_type(int picture_type) {
		this.picture_type = picture_type;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getPhoto_id() {
		return super.getId();
	}
	public void setPhoto_id(int id) {
		super.setId(id);
	}
	
	private int user_id;
	private String photo_name;
	private String path;
	private String extension;
	private long release_date;
	private int sold_nr;
	private int price;
	private int rates_nr;
	private int rates_sum;
	private String place;
	private String description;
	private int is_verified;
	private int picture_type;
	private int category;
	
	public static final String PHOTO_ID = "photo_id";
	public static final String USER_ID = "user_id";
	public static final String PHOTO_NAME = "photo_name";
	public static final String PATH = "path";
	public static final String EXTENSION = "extension";
	public static final String RELEASE_DATE = "release_date";
	public static final String SOLD_NR = "sold_nr";
	public static final String PRICE = "price";
	public static final String RATES_NR = "rates_nr";
	public static final String RATES_SUM = "rates_sum";
	public static final String PLACE = "place";
	public static final String DESCRIPTION = "description";
	public static final String IS_VERIFIED = "is_verified";
	public static final String PICTURE_TYPE = "picture_type";
	public static final String CATEGORTY = "category";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
