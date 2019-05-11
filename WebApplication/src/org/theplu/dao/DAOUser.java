package org.theplu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.theplu.entities.Comment;
import org.theplu.entities.Photo;
import org.theplu.entities.Rate;
import org.theplu.entities.User;
import org.theplu.utils.UtilsMethods;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DAOUser extends DAOAbstractDatabase<User> implements IDAOUser{

	public DAOUser() {
		super(User.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean login(User user) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return null;
		}
		
		try {
			String stmnt = "SELECT * FROM " + this.clazz.getSimpleName() + " WHERE " + User.USERNAME + " = \""+user.getUsername()+"\" AND "+
			User.PASSWORD +" = \"" + user.getPassword()+ "\" AND " + User.IS_BANNED + " = 0";
			
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(stmnt);
			
			ResultSet rs = ps.executeQuery();
			
			Boolean isLogged = rs.next();
			
			ps.close();
			rs.close();
			
			return isLogged;
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
		return null;
	}
	
	public Boolean register(User user) {
		Boolean exist = checkIfUsernameExist(user.getUsername());
		
		if (exist) {
			return false;
		}
		
		if (user.getUser_type() == 0){
			user.setUser_type(4);      //kupac
			sendActivationEmail(user);
		}
			
		// verifikacioni email
		
		super.add(user);
		return true;
	}
	public Boolean checkIfUsernameExist(String username) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return false;
		}
		Boolean exist = false;
		try {
			String stmnt = "SELECT * FROM user WHERE username = \"" + username + "\"";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(stmnt);
			
			ResultSet rs = ps.executeQuery();
			
			Boolean isLogged = rs.next();
			
			ps.close();
			rs.close();
			
			return isLogged;
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
		return exist;
	}
	
	public void sendActivationEmail(User user) {
		
		final String username = "web2018projekatjul@gmail.com";
		final String password = "MikaPera21";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(user.getEmail()));
	         message.setSubject("WP Store Activation");
	
	         // Now set the actual message
			 //TODO: Change URL!
	         message.setText("Hi " + user.getUsername() + ", thanks for registering on WP Store!\n"
	         		+ "To complete the activation, please click this link: http://localhost:8080/Domaci7/rest/user/activate/" + user.getUsername());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
      
      
}

	public List<Comment> getAllComments(){
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return null;
		}
		
		List<Comment> comments = null;
		try {
			String statement = "SELECT*FROM comments";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			
			ResultSet rs = ps.executeQuery();
			
			comments = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment c = new Comment();
				c.setComment_id(rs.getInt(Comment.COMMENT_ID));
				c.setCommentator_id(rs.getInt(Comment.COMMENTATOR_ID));
				c.setCommented_id(rs.getInt(Comment.COMMENTED_ID));
				c.setComment(rs.getString(Comment.COMMENT));
				comments.add(c);
			}
			
			ps.close();
			rs.close();
			
			return comments;
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
		
		return comments;
	}

	@Override
	public List<Comment> getCommentsBySpecificUser(String username) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return null;
		}
		
		User user = this.getUserByName(username);
		if(user==null) {
			return null;
		}
		List<Comment> comments = null;
		try {
			String statement = "SELECT*FROM comments WHERE "+Comment.COMMENTED_ID+" = "+user.getId()+" ORDER BY "+Comment.COMMENT_ID+" DESC";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			
			ResultSet rs = ps.executeQuery();
			
			comments = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment c = new Comment();
				c.setComment_id(rs.getInt(Comment.COMMENT_ID));
				c.setCommentator_id(rs.getInt(Comment.COMMENTATOR_ID));
				c.setCommented_id(rs.getInt(Comment.COMMENTED_ID));
				c.setComment(rs.getString(Comment.COMMENT));
				comments.add(c);
			}
			
			ps.close();
			rs.close();
			
			return comments;
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
		
		return comments;
	}

	public Boolean addComment(Comment comment) {
		if(!canComment(comment)) {
			return false;
		}
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return false;
		}
		
		Boolean added = false;
		try {
			String statement = "INSERT INTO comments ("+Comment.COMMENTATOR_ID+", "+Comment.COMMENTED_ID
					+", "+Comment.COMMENT+")"
					+ " VALUES ("+comment.getCommentator_id()+", "+comment.getCommented_id()
					+", \""+comment.getComment()+"\")";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			
			added = ps.execute();
			ps.close();
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
		return added;
	}

	@Override
	public Boolean canComment(Comment comment) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return false;
		}
		
		Boolean canComment = false;
		try {
			String statement = 	"SELECT*FROM buying WHERE buyer_id="+comment.getCommentator_id()
							+	" AND seller_id="+comment.getCommented_id();
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			
			ResultSet rs = ps.executeQuery();
			
			canComment = rs.next();
			rs.close();
			ps.close();
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
		return canComment;
	}
	

	@Override
	public Integer photoNrInDays(Integer id, int days) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return -1;
		}
		
		Integer photos = 0;
		try {
			String statement = "SELECT*FROM photo WHERE "+Photo.USER_ID+" = "+id
							+	" AND "+Photo.PICTURE_TYPE+" = 1";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				long release = rs.getLong(Photo.RELEASE_DATE);
				if(UtilsMethods.millisDifferToDays(release, new GregorianCalendar().getTimeInMillis())<=days) {
					photos++;
				}
			}
			
			rs.close();
			ps.close();
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
		return photos;
	}
	
	public Boolean ratePicture(Rate rate) {
		Comment comment = new Comment();
		comment.setCommentator_id(rate.getRater_id());
		comment.setCommented_id(rate.getRated_id());
		Boolean canRate = canComment(comment);
		if(!canRate || !UtilsMethods.rateInRange(rate.getRate())) {
			return false;
		}
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return false;
		}
		
		String statement = 	"UPDATE photo SET "+Photo.RATES_NR+" = "+Photo.RATES_NR+" + 1, "+
							Photo.RATES_SUM+" = "+Photo.RATES_SUM+" + "+rate.getRate()+
							" WHERE "+Photo.PHOTO_ID+" = "+rate.getPhoto_id();
		
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			ps.execute();
			canRate = true;
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection(conn);
		}
		
		return canRate;
	}

	@Override
	public User getUserByName(String username) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return null;
		}
		
		User user = null;
		
		try {
			String statement = "SELECT*FROM user WHERE "+User.USERNAME+" = \""+username+"\"";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			
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
	public List<User> getUsersByName(String name) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return null;
		}
		
		ArrayList<User> users = null;
		
		String statement = "SELECT*FROM user WHERE "+User.USERNAME+" LIKE \'%%"+name+"%%\'";
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			ResultSet rs = ps.executeQuery();
			users = new ArrayList<>();
			while(rs.next()) {
				User user = new User();
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
				users.add(user);
			}
			closeResultSet(rs);
			closeStat(ps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection(conn);
		}
		return users;
	}

	@Override
	public boolean activateUser(String username) {
		Connection conn = (Connection) createConnection();
		boolean flag = false;
		
		if (conn == null)
			return false;

		String statement = String.format("UPDATE user SET %s = %d WHERE %s = \'%s\'", User.IS_ACTIVATED, 1, User.USERNAME, username);
		
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			if(ps.executeUpdate() > 0)
				flag = true;
			closeStat(ps);
			closeConnection(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;

	}

	
	@Override
	public Boolean ban(String username) {
		Connection conn = (Connection) createConnection();
		
		if(conn==null) {
			return null;
		}
		
		String statement = String.format("UPDATE user SET %s = %d WHERE %s = \'%s\'", User.IS_BANNED, 1, User.USERNAME, username);
		
		try {
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(statement);
			if(ps.executeUpdate()>0) {
				closeStat(ps);
				closeConnection(conn);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
