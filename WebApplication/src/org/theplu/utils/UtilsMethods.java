package org.theplu.utils;

import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.theplu.entities.Filter;

/**
 * Klasa sa pomocnim metodam
 * @author user
 *
 */
public final class UtilsMethods {
	/**
	 * Pomocna metoda koja konvertuje object o u broj i u slucaju da object o ne moze da se konvertuje vraca se vrednost 0.
	 * @param o
	 * @return
	 */
	public static int saftyConversionInt(Object o){
		int id = 0;
		try{
			id = Integer.parseInt(o == null ? "0" : o.toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return id;
	}
	
	public static long saftyConversionLong(Object o){
		long id = 0;
		try{
			id = Long.parseLong(o == null ? "0" : o.toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * Pomocna metoda koja konvertuje object o u broj i u slucaju da object o ne moze da se konvertuje vraca se vrednost 0.
	 * @param o
	 * @return
	 */
	public static double saftyConversionDouble(Object o){
		double id = 0;
		try{
			id = Double.parseDouble(o == null ? "0" : o.toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * Pomocna metoda koja sluzi da konvertuje object o u string i u slucaju da je o == null vraca se prazan string.
	 * @param o
	 * @return
	 */
	public static String saftyConversionToStr(Object o){
		return o == null ? "" : o.toString();
	}
	
	/**
	 * Method which convert string in format dd.mm.yyyy. to GregorianCalendar object
	 * @param str
	 * @return
	 */
	public static GregorianCalendar convertStringToDate(String str) {
		StringTokenizer st = new StringTokenizer(str, ".");
		int day = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int year = Integer.parseInt(st.nextToken());
		System.out.println(day +" "+month+" "+year);
		return new GregorianCalendar(year, month, day);
	}
	
	/**
	 * Method which returns days differ beetwen two dates
	 * @param gc1
	 * @param gc2
	 * @return
	 */
	public static long dayDiffer(GregorianCalendar gc1, GregorianCalendar gc2) {
		long millis = gc2.getTimeInMillis() - gc1.getTimeInMillis();
		System.out.println(gc2.getTimeInMillis()+" "+gc1.getTimeInMillis());
		long days = (millis / (1000*60*60*24));
		System.out.println(days);
		return days;
	}
	
	public static int millisDifferToDays(long release, long now) {
		int days = (int)((now - release)/(1000*60*60*24));
		return days;
	}
	
	public static Boolean rateInRange(int rate) {
		if(rate>5 || rate<1)
			return false;
		
		return true;
	}
	
	public static void reorganizeFilter(Filter filter) {
		if(filter.getSort_type().equalsIgnoreCase("asc")) {
			filter.setSort_type("ASC");
		}else {
			filter.setSort_type("DESC");
		}
		
		if(filter.getSort_param().equalsIgnoreCase("Date")) {
			filter.setSort_param("release_date");
		}else if(filter.getSort_param().equalsIgnoreCase("Numbers sold")){
			filter.setSort_param("sold_nr");
		}else if(filter.getSort_param().equalsIgnoreCase("Price")){
			filter.setSort_param("price");
		}else if(filter.getSort_param().equalsIgnoreCase("Photo name")){
			filter.setSort_param("photo_name");
		}else if(filter.getSort_param().equalsIgnoreCase("Rate")){
			filter.setSort_param("rate");
		}
	}
}
