package org.theplu.dao;

import java.util.List;

import org.theplu.entities.Comment;
import org.theplu.entities.Rate;
import org.theplu.entities.User;

public interface IDAOUser extends IDAOAbstract<User>{

	Boolean login(User user);
	Boolean register(User user);
	Boolean checkIfUsernameExist(String username);
	List<Comment> getAllComments();
	List<Comment> getCommentsBySpecificUser(String comment);
	Boolean addComment(Comment comment);
	Boolean canComment(Comment comment);
	Integer photoNrInDays(Integer id, int days);
	Boolean ratePicture(Rate rate);
	User getUserByName(String username);
	List<User> getUsersByName(String name);
	boolean activateUser(String username);
	Boolean ban(String username);
}
