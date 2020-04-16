package com.koblan.taskManager.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

//import java.time.LocalDate;
import java.util.List;
import java.util.Date;

@Document(collection = "tasks")
public class Task {
	
	@Id
	private String taskId;

	private String title;
	
	private String description;
	
	private Date endDate;
	
	private User user;
	
	private List<User> users_shared;
	
	public Task() {}
	
	public Task(String title, String description, Date endDate, User user) {
		this.title = title;
		this.description = description;
		this.endDate = endDate;
		this.user = user;
	}
	
	public Task(String taskId, String title, String description, Date endDate, User user) {
		this.taskId=taskId;
		this.title = title;
		this.description = description;
		this.endDate = endDate;
		this.user = user;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers_shared() {
		return users_shared;
	}

	public void setUsers_shared(List<User> users_shared) {
		this.users_shared = users_shared;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", title=" + title + ", description=" + description + ", endDate=" + endDate
				+ ", user=" + user + "]";
	}
	

}
