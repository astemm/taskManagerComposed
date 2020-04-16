package com.koblan.taskManager.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.koblan.taskManager.models.Task;
import com.koblan.taskManager.models.User;
import com.koblan.taskManager.models.SharedTask;

@Repository
public class SharedTaskRepository {
	
	@Autowired
	private MongoTemplate sharedTemplate;
	
	@Autowired
	private UserRepository userRepo;
	
	public List<SharedTask> getTasks() {
		return sharedTemplate.findAll(SharedTask.class);
	}

	public SharedTask findById(String sharedId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("sharedId").is(new ObjectId(sharedId)));
		return sharedTemplate.findOne(query, SharedTask.class);
	}

	public SharedTask addSharedTask(SharedTask task) {
		SharedTask newTask=sharedTemplate.save(task);
		return newTask;
	}
	
	public SharedTask updateSharedTask(SharedTask task) {
	        Query query = new Query(Criteria.where("sharedId").is(new ObjectId(task.getSharedId())));
	        Update update = new Update();
	        update.set("task", task.getTask());
	        sharedTemplate.updateFirst(query, update, SharedTask.class);
	        return task;
	}
	
	public Task updateSharedTaskByTask(Task task) {
        Query query = new Query(Criteria.where("task.taskId").is(new ObjectId(task.getTaskId())));
        Update update = new Update();
        update.set("task", task);
        sharedTemplate.updateMulti(query, update, SharedTask.class);
        return task;
    }
	
	
	public void deleteSharedTask(String sharedId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("sharedId").is(new ObjectId(sharedId)));
		sharedTemplate.remove(query, SharedTask.class);
	}
	
	public void deleteSharedTaskByTaskId(String taskId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("task.taskId").is(new ObjectId(taskId)));
		sharedTemplate.remove(query, SharedTask.class);
	}
	
	public List<SharedTask> findSharedTaskByUserId (String receivingUserId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("receivingUserId").is(receivingUserId));
		List<SharedTask> list=sharedTemplate.find(query, SharedTask.class);
		return list;
		
	}
	
}