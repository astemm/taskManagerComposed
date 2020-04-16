package com.koblan.taskManager.payload;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.koblan.taskManager.models.User;

public class TaskDTO {

	private String taskId;

	private String title;
	
	private String description;
	
	private Date endDate;
	
	private String username;
	
	public TaskDTO () {}
	
	public TaskDTO(String taskId, String title, String description, Date endDate, String username) {
		this.taskId = taskId;
		this.title = title;
		this.description = description;
		this.endDate = endDate;
		this.username = username;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
	
	