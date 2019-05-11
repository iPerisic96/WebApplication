package org.theplu.services;

import java.util.List;

import org.theplu.dao.IDAOUser;
import org.theplu.entities.Comment;
import org.theplu.entities.Rate;
import org.theplu.entities.User;

public class ServiceUser extends ServiceAbstract<User, IDAOUser> implements IServiceUser{

	public ServiceUser(IDAOUser dao) {
		super(dao);
	}

	@Override
	public Boolean login(User user) {
		// TODO Auto-generated method stub
		return this.dao.login(user);
	}

	@Override
	public Boolean register(User user) {
		// TODO Auto-generated method stub
		return this.dao.register(user);
	}

	@Override
	public Boolean checkIfUsernameExist(String username) {
		// TODO Auto-generated method stub
		return this.dao.checkIfUsernameExist(username);
	}
	
	public List<Comment> getAllComments(){
		return this.dao.getAllComments();
	}
	
	public List<Comment> getCommentsBySpecificUser(String comment){
		return this.dao.getCommentsBySpecificUser(comment);
	}
	
	public Boolean addComment(Comment comment) {
		return this.dao.addComment(comment);
	}

	@Override
	public Integer photoNrInDays(Integer id, int days) {
		return this.dao.photoNrInDays(id, days);
	}
	
	public Boolean ratePicture(Rate rate) {
		return this.dao.ratePicture(rate);
	}

	@Override
	public User getUserByName(String username) {
		return this.dao.getUserByName(username);
	}
	
	@Override
	public boolean activateUser(String username) {
		return this.dao.activateUser(username);
	}

	@Override
	public List<User> getUsersByName(String username) {
		// TODO Auto-generated method stub
		return this.dao.getUsersByName(username);
	}

	@Override
	public Boolean ban(String username) {
		// TODO Auto-generated method stub
		return this.dao.ban(username);
	}
}
