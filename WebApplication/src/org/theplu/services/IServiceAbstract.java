package org.theplu.services;

import java.util.List;

import org.theplu.entities.BasicEntity;

public interface IServiceAbstract<T extends BasicEntity> {
	boolean add(T object);
	boolean removeById(int id);
	boolean update(T object);
	List<T> getAll();
	T getById(int id);
}
