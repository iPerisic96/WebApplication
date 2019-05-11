package org.theplu.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.theplu.utils.UtilsMethods;

public abstract class BasicEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2156973367342327643L;


	public BasicEntity(String idName){
		this.id = Integer.MIN_VALUE;
		this.columnsName = new ArrayList<String>();
		this.ID = idName;
		columnsName.add(this.ID);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicEntity other = (BasicEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	public void setValueForColumnName(String columnName, Object value){
		if( ID.equals(columnName) ){
			setId(UtilsMethods.saftyConversionInt(value));
		}
	}
	
	/**
	 * Metoda koja vraca vrednost za prosledjeni naziv kolone.
	 * @param columnName
	 */
	public Object getValueForColumnName(String columnName){
		if( ID.equals(columnName) ){
			return this.id;
		}
		
		return null;
	}
	
	public String primaryKeyColumnName(){
		return ID;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	/**
	 * Metoda koja vraca nazive kolona koje entity ima tabeli.
	 * @return
	 */
	public List<String> columnsName(){
		return this.columnsName;
	}
	
	/**
	 * Lista koja sadrzi naziv svih kolona.
	 */
	protected List<String> columnsName;
	
	private int id;
	private final String ID;
}
