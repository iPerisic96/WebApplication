package org.theplu.services;

import java.util.List;

import org.theplu.entities.Comment;
import org.theplu.entities.Rate;
import org.theplu.entities.User;

public interface IServiceUser extends IServiceAbstract<User>{

	Boolean login(User user);
	Boolean register(User user);
	Boolean checkIfUsernameExist(String username);
	List<Comment> getAllComments();
	List<Comment> getCommentsBySpecificUser(String comment);
	Boolean addComment(Comment comment);
	Integer photoNrInDays(Integer id, int days);
	Boolean ratePicture(Rate rate);
	User getUserByName(String username);
	List<User> getUsersByName(String username);
	boolean activateUser(String username);
	Boolean ban(String username);
}
