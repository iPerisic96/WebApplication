package org.theplu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.theplu.entities.Buying;
import org.theplu.entities.Category;
import org.theplu.entities.Filter;
import org.theplu.entities.Photo;
import org.theplu.entities.User;
import org.theplu.utils.UtilsMethods;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DAOPhoto extends DAOAbstractDatabase<Photo> implements IDAOPhoto{

	public DAOPhoto(Class<Photo> clazz) {
		super(clazz);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean addPhoto(Photo photo) {
		photo.setRelease_date(new GregorianCalendar().getTimeInMillis());
		return super.add(photo);
	}

	@Override
	public List<Photo> searchNextFive(int count, String username, int id) {
		if(count<=0) {
        	return null;
        }
		
        Connection conn = (Connection) createConnection();

        if (conn == null)
            return null;

        String statement;
        int offset = 6 * (count-1);
        if(!username.equals("")) {
        	statement = String.format("SELECT * FROM photo WHERE %s = %d ORDER BY %s LIMIT 6 OFFSET %d", Photo.USER_ID, id, Photo.RELEASE_DATE, offset);
        }else {
        	statement = String.format("SELECT * FROM photo ORDER BY %s LIMIT 6 OFFSET %d", Photo.RELEASE_DATE, offset);
        }
        
        try {

            PreparedStatement st = (PreparedStatement) conn.prepareStatement(statement);

            ResultSet rs = st.executeQuery();

            List<Photo> list = new ArrayList<Photo>();

            while (rs.next())
                list.add(readFromResultSet(rs));

            closeStat(st);
            closeResultSet(rs);

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return null;
    }

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		System.out.println("Usao u getAllCategories");
		Connection conn = (Connection) createConnection();

        if (conn == null)
            return null;
        
        List<Category> categories = null;
        String statement = "SELECT * FROM categories";
        try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
			categories = new ArrayList<>();
			while(rs.next()) {
				Category c = new Category();
				c.setCategory_id(rs.getInt(Category.CATEGORY_ID));
				c.setCategory_name(rs.getString(Category.CATEGORY_NAME));
				categories.add(c);
			}
			closeStat(ps);
            closeResultSet(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnection(conn);
		}
		return categories;
	}

	@Override
	public List<Photo> getFilteredPhoto(Filter filter) {
		Connection conn = (Connection) createConnection();

        if (conn == null)
            return null;
        
        UtilsMethods.reorganizeFilter(filter);
        String stmnt = "SELECT * FROM photo WHERE ";
        User user = getUserByName(filter.getAuthor_name());
        int id;
        if(user!=null) {
        	id = user.getId();
        	stmnt += Photo.USER_ID + " = " + id + " AND ";
        }
        
        if(filter.getCategory_id() != -1) {
        	stmnt += Photo.CATEGORTY + " = " + filter.getCategory_id() + " AND ";
        }
        
        stmnt += Photo.PHOTO_NAME + " LIKE " + "\'%%" + filter.getPhoto_name() + "%%\' AND " + Photo.DESCRIPTION + " LIKE \'%%" + filter.getKeyword()+ "%%\' ";
        stmnt += "ORDER BY " + filter.getSort_param() + " " + filter.getSort_type(); // ASC ili DSC sort type
        List<Photo> photos = null;
        /**
         * Izmeniti Photo.USER_ID u Photo.USER_NAME
         */
        
        try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(stmnt);
			photos = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				photos.add(readFromResultSet(rs));
			}
			closeStat(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeConnection(conn);
		}
		return photos;
	}

	@Override
	public User getUserByName(String username) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return null;
		}
		
		User user = null;
		
		try {
			String stmnt = "SELECT * FROM user WHERE " + User.USERNAME + " = \"" + username + "\"";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(stmnt);
			
			ResultSet rs = ps.executeQuery();
		
			
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt(User.USER_ID));
				user.setCountry(rs.getString(User.COUNTRY));
				user.setEmail(rs.getString(User.EMAIL));
				user.setIs_activated(rs.getInt(User.IS_ACTIVATED));
				user.setIs_banned(rs.getInt(User.IS_BANNED));
				user.setLog_nr(rs.getInt(User.LOG_NR));
				user.setPassword(rs.getString(User.PASSWORD));
				user.setPictures_rate_sum(rs.getInt(User.PICTURES_RATE_SUM));
				user.setRated_pictures_nr(rs.getInt(User.RATED_PICTURES_NR));
				user.setUser_type(rs.getInt(User.USER_TYPE));
				user.setUsername(rs.getString(User.USERNAME));
				
			}
			
			ps.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return user;
	}

	@Override
	public Boolean buy(Buying buying) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return null;
		}
		
		String statement = "INSERT INTO buying (buyer_id, seller_id, picture_id, price) VALUES ( " + buying.getBuyer_id()+ ", " + buying.getSeller_id() + ", " + buying.getPicture_id()+ ", " + buying.getPrice() + ");";
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			ps.execute();
			closeStat(ps);
			closeConnection(conn);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection(conn);
		return false;
	}
	
}
