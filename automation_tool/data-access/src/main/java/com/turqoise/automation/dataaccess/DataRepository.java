package com.turqoise.automation.dataaccess;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.automation.dataaccess.model.ToolException;

public interface DataRepository<T> {
	
	T findById(String id, Class<T> cls, String[] includeFields, String[] excludeFields) throws ToolException;
	T findById(String id, Class<T> cls) throws ToolException;
	List<T> find(Query query, Class<T> cls, String[] includeFields, String[] excludeFields) throws ToolException;
	List<T> find(Query query, Class<T> cls) throws ToolException;
	void add(T t, Class<T> cls) throws ToolException;
	void addMany(List<T> t, Class<T> cls) throws ToolException;
	String update(Query query, Update update, Class<T> cls) throws ToolException;
	boolean updateMany(Query query, Update update, Class<T> cls) throws ToolException;
	boolean delete(Query query, Class<T> cls) throws ToolException;
	
}
