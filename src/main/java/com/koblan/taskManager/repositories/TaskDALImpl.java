package com.koblan.taskManager.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.koblan.taskManager.models.SharedTask;
import com.koblan.taskManager.models.Task;
import com.koblan.taskManager.models.User;

@Repository
public class TaskDALImpl implements TaskDAL {
	
	@Autowired
	private MongoTemplate taskTemplate;
	
	@Autowired
	private SharedTaskRepository sharedRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<Task> getTasks() {
		return taskTemplate.findAll(Task.class);
	}
	
	public List<Task> getTasksByUser(List<String> idList) {
		System.out.println("list.."+idList);
		List<ObjectId> objIdList=new ArrayList<ObjectId>();
		for(String id:idList) {objIdList.add(new ObjectId(id)); }
		
		List<Task> taskList=new ArrayList<Task>();
		Query query = new Query();
		query.addCriteria(Criteria.where("taskId").in(objIdList));
		taskList= taskTemplate.find(query, Task.class);
		
		return taskList;
	}

	@Override
	public Task findById(String taskId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("taskId").is(new ObjectId(taskId)));
		return taskTemplate.findOne(query, Task.class);
	}

	@Override
	public Task addTask(Task task) {
		Task newTask=taskTemplate.save(task);
		User user=userRepo.findById(task.getUser().getId()).get();
		List<String> utasks;
		if (user.getTasks()==null) {utasks=new ArrayList<String>();}
		else utasks=user.getTasks();
		utasks.add(newTask.getTaskId());
		user.setTasks(utasks);
		userRepo.save(user);
		return newTask;
	}
	
	@Override
	public Task updateTask(Task task) {
	        Query query = new Query(Criteria.where("taskId").is(new ObjectId(task.getTaskId())));
	        Update update = new Update();
	        update.set("title", task.getTitle());
	        update.set("description", task.getDescription());
	        update.set("endDate", task.getEndDate());
	        taskTemplate.updateFirst(query, update, Task.class);
	        sharedRepo.updateSharedTaskByTask(task); //update sharedtask
	        return task;
	}
	
	@Override
	public void deleteTask(String taskId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("taskId").is(new ObjectId(taskId)));
		Task task=taskTemplate.findOne(query, Task.class); //
		System.out.println("task... - " + task);
		String userId=task.getUser().getId();
		taskTemplate.remove(query, Task.class);
		
		List<String> utasks;
		User user=userRepo.findById(userId).get();
		if (user.getTasks()==null) {return;}
		else utasks=user.getTasks();
		utasks.remove(taskId);
		user.setTasks(utasks);
		userRepo.save(user);
		
		sharedRepo.deleteSharedTaskByTaskId(taskId);
	}
	
	@Override
	public SharedTask shareTask (SharedTask sharedTask) {
		SharedTask newSharedTask=taskTemplate.save(sharedTask);
		return newSharedTask;
	}

}
