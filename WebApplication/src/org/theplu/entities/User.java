package org.theplu.entities;

import org.theplu.utils.UtilsMethods;

public class User extends BasicEntity{

	public User() {
		super(USER_ID);
		this.columnsName.add(USERNAME);
		this.columnsName.add(PASSWORD);
		this.columnsName.add(EMAIL);
		this.columnsName.add(COUNTRY);
		this.columnsName.add(USER_TYPE);
		this.columnsName.add(RATED_PICTURES_NR);
		this.columnsName.add(PICTURES_RATE_SUM);
		this.columnsName.add(IS_BANNED);
		this.columnsName.add(LOG_NR);
		this.columnsName.add(IS_ACTIVATED);
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public int getRated_pictures_nr() {
		return rated_pictures_nr;
	}

	public void setRated_pictures_nr(int rated_pictures_nr) {
		this.rated_pictures_nr = rated_pictures_nr;
	}

	public int getPictures_rate_sum() {
		return pictures_rate_sum;
	}

	public void setPictures_rate_sum(int pictures_rate_sum) {
		this.pictures_rate_sum = pictures_rate_sum;
	}

	public int getIs_banned() {
		return is_banned;
	}

	public void setIs_banned(int is_banned) {
		this.is_banned = is_banned;
	}

	public int getLog_nr() {
		return log_nr;
	}

	public void setLog_nr(int log_nr) {
		this.log_nr = log_nr;
	}

	public int getIs_activated() {
		return is_activated;
	}

	public void setIs_activated(int is_activated) {
		this.is_activated = is_activated;
	}
	
	public int getUser_id() {
		return super.getId();
	}
	
	public void setUser_id(int id) {
		super.setId(id);
	}
	
	public String getCard_nr() {
		return card_nr;
	}
	
	public void setCard_nr(String card_nr) {
		this.card_nr = card_nr;
	}
	
	public void setValueForColumnName(String columnName, Object value) {
		if( USERNAME.equals(columnName) ){
			this.setUsername(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		
		if( PASSWORD.equals(columnName) ){
			this.setPassword(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		
		if(EMAIL.equals(columnName)) {
			this.setEmail(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		if(COUNTRY.equals(columnName)) {
			this.setCountry(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		if(USER_TYPE.equals(columnName)) {
			this.setUser_type(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(RATED_PICTURES_NR.equals(columnName)) {
			this.setRated_pictures_nr(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(PICTURES_RATE_SUM.equals(columnName)) {
			this.setPictures_rate_sum(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(IS_BANNED.equals(columnName)) {
			this.setIs_banned(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(LOG_NR.equals(columnName)) {
			this.setLog_nr(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(IS_ACTIVATED.equals(columnName)) {
			this.setIs_activated(UtilsMethods.saftyConversionInt(value));
			return;
		}
		if(USER_ID.equals(columnName)) {
			int id = UtilsMethods.saftyConversionInt(value);
			super.setId(id);
			return;
		}
		if(CARD_NR.equals(columnName)) {
			this.setCard_nr(UtilsMethods.saftyConversionToStr(value));
			return;
		}
		super.setValueForColumnName(columnName, value);
	}
	
	public Object getValueForColumnName(String columnName){
		if( USERNAME.equals(columnName) ){
			return this.getUsername();
		}
		
		if( PASSWORD.equals(columnName) ){
			return this.getPassword();
		}
		if(EMAIL.equals(columnName)) {
			return this.getEmail();
		}
		if(COUNTRY.equals(columnName)) {
			return this.getCountry();
		}
		if(USER_TYPE.equals(columnName)) {
			return this.getUser_type();
		}
		if(RATED_PICTURES_NR.equals(columnName)) {
			return this.getRated_pictures_nr();
		}
		if(PICTURES_RATE_SUM.equals(columnName)) {
			return this.getPictures_rate_sum();
		}
		if(IS_BANNED.equals(columnName)) {
			return this.getIs_banned();
		}
		if(LOG_NR.equals(columnName)) {
			return this.getLog_nr();
		}
		if(IS_ACTIVATED.equals(columnName)) {
			return this.getIs_activated();
		}
		
		if(USER_ID.equals(columnName)) {
			return super.getId();
		}
		
		if(CARD_NR.equals(columnName)) {
			return this.getCard_nr();
		}
		return super.getValueForColumnName(columnName);
	}
	
	private String username;
	private String password;
	private String email;
	private String country;
	private int user_type;
	private int rated_pictures_nr;
	private int pictures_rate_sum;
	private int is_banned;
	private int log_nr;
	private int is_activated;
	private String card_nr;
	
	
	public static final String USER_ID = "user_id";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String COUNTRY = "country";
	public static final String USER_TYPE = "user_type";
	public static final String RATED_PICTURES_NR = "rated_pictures_nr";
	public static final String PICTURES_RATE_SUM = "pictures_rate_sum";
	public static final String IS_BANNED = "is_banned";
	public static final String LOG_NR = "log_nr";
	public static final String IS_ACTIVATED = "is_activated";
	public static final String CARD_NR = "card_nr";
	

	private static final long serialVersionUID = 1L;
}
