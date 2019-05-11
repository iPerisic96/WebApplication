package org.theplu.services;

import java.util.List;

import org.theplu.entities.Buying;
import org.theplu.entities.Category;
import org.theplu.entities.Filter;
import org.theplu.entities.Photo;
import org.theplu.entities.User;

public interface IServicePhoto extends IServiceAbstract<Photo>{

	Boolean addPhoto(Photo photo);
	List<Photo> searchNextFive(int n, String username, int id);
	List<Category> getAllCategories();
	List<Photo> getFilteredPhoto(Filter filter);
	Boolean buy(Buying buying);
}
