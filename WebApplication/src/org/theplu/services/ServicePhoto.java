package org.theplu.services;

import java.util.List;

import org.theplu.dao.IDAOPhoto;
import org.theplu.dao.IDAOUser;
import org.theplu.entities.Buying;
import org.theplu.entities.Category;
import org.theplu.entities.Filter;
import org.theplu.entities.Photo;
import org.theplu.entities.User;

public class ServicePhoto extends ServiceAbstract<Photo, IDAOPhoto> implements IServicePhoto{

	public ServicePhoto(IDAOPhoto dao) {
		super(dao);
	}

	@Override
	public Boolean addPhoto(Photo photo) {
		return this.dao.addPhoto(photo);
	}

	@Override
	public List<Photo> searchNextFive(int n, String username, int id) {
		// TODO Auto-generated method stub
		return this.dao.searchNextFive(n, username, id);
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return this.dao.getAllCategories();
	}

	@Override
	public List<Photo> getFilteredPhoto(Filter filter) {
		// TODO Auto-generated method stub
		return this.dao.getFilteredPhoto(filter);
	}

	@Override
	public Boolean buy(Buying buying) {
		// TODO Auto-generated method stub
		return this.dao.buy(buying);
	}
}
