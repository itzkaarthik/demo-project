package com.turqoise.automation.dataaccess;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepository<T> {
	
	T findById(String id, Class<T> cls, String[] includeFields, String[] excludeFields);
	T findById(String id, Class<T> cls);
	List<T> find(Query query, Class<T> cls, String[] includeFields, String[] excludeFields);
	List<T> find(Query query, Class<T> cls);

	

	
}
