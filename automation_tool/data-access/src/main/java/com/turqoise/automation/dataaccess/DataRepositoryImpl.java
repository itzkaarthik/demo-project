package com.turqoise.automation.dataaccess;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.automation.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.turqoise.automation.dataaccess.config.MongoConfig;

public class DataRepositoryImpl<T> implements DataRepository<T>{
	
	@Autowired
	MongoTemplate mongoTemplate;

	public DataRepositoryImpl() throws Exception {
		MongoConfig config = new MongoConfig();
		mongoTemplate = config.mongoTemplate();
		
	}
	public T findById(String id, Class<T> cls) {
		//Class x = t.getClass();
		ApplicationContext context = 
	            new ClassPathXmlApplicationContext("beans.xml");
		
		System.out.println(context);
		//mongoTemplate  = (MongoTemplate) context.getBean(MongoTemplate.class);
		//System.out.println(mongoTemplate);
		return (T) mongoTemplate.findById(id, cls);
		
	}

	public List<T> find(Query query, Class<T> cls,String[] includeFields, String[] excludeFields) {
		if((includeFields!=null && includeFields.length>0)||
				(excludeFields!=null && excludeFields.length>0)) {
			query = prepareProjectionQuery(query, includeFields,excludeFields);
		};
		// TODO Auto-generated method stub
		return mongoTemplate.find(query, cls);
	}

	private Query prepareProjectionQuery(Query query,String[] includeFields, String[] excludeFields) {
		
		if(includeFields!= null && includeFields.length>0) {
			for(String proj: includeFields) {
				query.fields().include(proj);
			}
		}
		if(excludeFields!= null && excludeFields.length>0) {
			for(String proj: excludeFields) {
				query.fields().exclude(proj);
			}
		}
		
		
		return query;
	}
	public static void main(String[] args) throws ClassNotFoundException {
		
	}
	public T findById(String id, Class<T> cls, String[] includeFields, String[] excludeFields) {
		
		
		if((includeFields!=null && includeFields.length>0)||
				(excludeFields!=null && excludeFields.length>0)) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			query = prepareProjectionQuery(query, includeFields,excludeFields);
			return mongoTemplate.findOne(query, cls);
		}else {
			
			return findById(id, cls);
		}
		
		
	}
	public List<T> find(Query query, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}
}
