package org.theplu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.theplu.entities.BasicEntity;

public abstract class DAOAbstractDatabase<T extends BasicEntity> implements IDAOAbstract<T> {
	public DAOAbstractDatabase(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean add(T object) {
		if( object == null ){
			return false;
		}
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}
		
		String columnsName = "";
		String questionMarks = "";
		for(String columnName : object.columnsName()){
			if(object.primaryKeyColumnName().equals(columnName)) 
				continue;
			columnsName = columnsName == "" ? columnName : String.format("%s, %s", columnsName, columnName);
			questionMarks = questionMarks == "" ? "?" : String.format("%s, ?", questionMarks);
		}
		
		String strQuery = String.format("INSERT INTO %s (%s) VALUES (%s)", this.clazz.getSimpleName(), columnsName, questionMarks);
		
		try {
			PreparedStatement st = conn.prepareStatement(strQuery);
			int parameterIndex = 1;
			for(String columnName : object.columnsName()){
				if(object.primaryKeyColumnName().equals(columnName)) 
					continue;
				st.setObject(parameterIndex++, object.getValueForColumnName(columnName));
			}
			return st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean removeById(int id) {
		return false;
	}

	@Override
	public boolean update(T object) {
		return true;
	}

	@Override
	public List<T> getAll() {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement(String.format("select * from %s", this.clazz.getSimpleName().toLowerCase()));
			ResultSet rs = st.executeQuery();
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				list.add(readFromResultSet(rs));
				System.out.println(list.get(list.size()-1));
			}
			closeStat(st);
			closeResultSet(rs);
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	@Override
	public T getById(int id) {
		Connection conn = createConnection();
		if (conn == null) {
			System.out.println("Connection null");
			return null;
		}
		
		try {
			T objectForIdName = clazz.newInstance();
			PreparedStatement st = conn.prepareStatement(String.format("select * from %s where %s=?", this.clazz.getSimpleName(), objectForIdName.primaryKeyColumnName()));
			st.setObject(1, id);
			ResultSet rs = st.executeQuery();
			T object = null;
			if( rs.next()) {
				object = readFromResultSet(rs);
			}
			closeStat(st);
			closeResultSet(rs);
			return object;
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		System.out.println("Returning null");
		return null;
	}
	
	/**
	 * Pomocna metoda koja sluzi da cita podatke iz prosledjenog result seta.
	 * @param rs
	 * @return
	 */
	protected T readFromResultSet(ResultSet rs){
		if( rs == null ){
			return null;
		}
		
		try {
			T object = this.clazz.newInstance();
			object = clazz.newInstance();
			for(String columnName : object.columnsName()){
				object.setValueForColumnName(columnName, rs.getObject(columnName));
			}
			
			return object;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Pomocna metoda koja kreira konekciju ka bazi
	 * 
	 * @return
	 */
	protected Connection createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/web_projekat", USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Pomocna metoda zatvaranje konekcije
	 * 
	 * @param conn
	 */
	protected void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}
	}

	/**
	 * Pomocna metoda za zatvaranje statemnt-a
	 * 
	 * @param stat
	 */
	protected void closeStat(PreparedStatement stat) {
		if (stat == null) {
			return;
		}

		try {
			stat.close();
		} catch (SQLException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}
	}

	/**
	 * Pomocna metoda koja zatvara prosledjeni result set.
	 * @param rs
	 */
	protected void closeResultSet(ResultSet rs){
		if( rs == null ){
			return;
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// property
	/**
	 * Svojstvo koje cuva klasu za koju se pravi query
	 */
	protected Class<T> clazz;

	// constants
	private String USERNAME = "root";
	private String PASSWORD = "";
}
