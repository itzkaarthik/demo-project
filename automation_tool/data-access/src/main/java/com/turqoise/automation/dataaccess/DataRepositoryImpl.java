package com.turqoise.automation.dataaccess;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.automation.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.automation.dataaccess.model.ToolException;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.turqoise.automation.dataaccess.config.MongoConfig;

public class DataRepositoryImpl<T> implements DataRepository<T> {

	MongoTemplate mongoTemplate;

	public DataRepositoryImpl() throws Exception {
		MongoConfig config = new MongoConfig();
		mongoTemplate = config.mongoTemplate();

	}

	public T findById(String id, Class<T> cls) throws ToolException {
		T returnValue = null;
		try {
			returnValue = mongoTemplate.findById(id, cls);
		} catch (DataAccessException ex) {
			throw new ToolException(ex);
		}
		return returnValue;
	}

	public List<T> find(Query query, Class<T> cls, String[] includeFields, String[] excludeFields)
			throws ToolException {
		List<T> returnValue = null;
		try {
			if ((includeFields != null && includeFields.length > 0)
					|| (excludeFields != null && excludeFields.length > 0)) {
				query = prepareProjectionQuery(query, includeFields, excludeFields);
			}
			returnValue = mongoTemplate.find(query, cls);
		} catch (DataAccessException ex) {
			throw new ToolException(ex);
		}
		return returnValue;
	}

	private Query prepareProjectionQuery(Query query, String[] includeFields, String[] excludeFields) {

		if (includeFields != null && includeFields.length > 0) {
			for (String proj : includeFields) {
				query.fields().include(proj);
			}
		}
		if (excludeFields != null && excludeFields.length > 0) {
			for (String proj : excludeFields) {
				query.fields().exclude(proj);
			}
		}

		return query;
	}

	public T findById(String id, Class<T> cls, String[] includeFields, String[] excludeFields) throws ToolException {
		T returnValue = null;
		try {
			if ((includeFields != null && includeFields.length > 0)
					|| (excludeFields != null && excludeFields.length > 0)) {
				Query query = new Query();
				query.addCriteria(Criteria.where("id").is(id));
				query = prepareProjectionQuery(query, includeFields, excludeFields);
				returnValue = mongoTemplate.findOne(query, cls);
			} else {

				returnValue = findById(id, cls);
			}
		} catch (DataAccessException ex) {
			throw new ToolException(ex);
		}
		return returnValue;

	}

	public List<T> find(Query query, Class<T> cls) throws ToolException {

		return find(query, cls, null, null);
	}

	public void add(T t, Class<T> cls) throws ToolException {
		try {
			mongoTemplate.insert(t, cls.getSimpleName().toLowerCase());

		} catch (DataAccessException ex) {
			throw new ToolException(ex);
		}
	}

	public void addMany(List<T> t, Class<T> cls) throws ToolException {
		try {
			mongoTemplate.insert(t, cls.getSimpleName().toLowerCase());
		} catch (DataAccessException ex) {
			throw new ToolException(ex);
		}
	}

	public String update(Query query, Update update, Class<T> cls) throws ToolException {

		String retValue = null;
		WriteResult wr = null;
		try {
			wr = mongoTemplate.updateFirst(query, update, cls.getSimpleName().toLowerCase());
			retValue = wr.getUpsertedId().toString();
		} catch (DataAccessException ex) {
			throw new ToolException(ex);
		}
		return retValue;
	}

	public boolean updateMany(Query query, Update update, Class<T> cls) throws ToolException {
		boolean retValue = false;
		WriteResult wr = null;
		try {
			wr = mongoTemplate.updateMulti(query, update, cls.getSimpleName().toLowerCase());
			retValue = wr.wasAcknowledged();
		} catch (DataAccessException ex) {
			throw new ToolException(ex);
		}
		return retValue;

	}

	public boolean delete(Query query, Class<T> cls) throws ToolException {
		boolean retValue = false;
		WriteResult wr = null;
		try {
			wr = mongoTemplate.remove(query, cls.getSimpleName().toLowerCase());
			retValue = wr.wasAcknowledged();
		} catch (DataAccessException ex) {
			throw new ToolException(ex);
		}
		return retValue;
	}
}
