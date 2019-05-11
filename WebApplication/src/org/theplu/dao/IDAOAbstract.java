package org.theplu.dao;

import java.util.List;

import org.theplu.entities.BasicEntity;

public interface IDAOAbstract<T extends BasicEntity> {
	boolean add(T object);
	boolean removeById(int id);
	boolean update(T object);
	List<T> getAll();
	T getById(int id);
}
