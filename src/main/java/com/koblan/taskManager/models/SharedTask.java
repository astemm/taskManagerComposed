package com.koblan.taskManager.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shared_tasks")
public class SharedTask {
	
	@Id
	private String sharedId;
	private String sendingUserId;
	private String receivingUserId;
	private Task task;
	
	public SharedTask() {}
	
	public SharedTask(String sharedId, String sendingUserId, 
			String receivingUserId, Task task) {
		this.sharedId = sharedId;
		this.sendingUserId = sendingUserId;
		this.receivingUserId = receivingUserId;
		this.task = task;
	}
	
	public SharedTask(String sendingUserId, 
			String receivingUserId, Task task) {
		this.sendingUserId = sendingUserId;
		this.receivingUserId = receivingUserId;
		this.task = task;
	}

	public String getSharedId() {
		return sharedId;
	}
	
	public void setSharedId(String sharedId) {
		this.sharedId = sharedId;
	}
	
	public String getSendingUserId() {
		return sendingUserId;
	}
	
	public void setSendingUserId(String sendingUserId) {
		this.sendingUserId = sendingUserId;
	}
	
	public String getReceivingUserId() {
		return receivingUserId;
	}
	
	public void setReceivingUserId(String receivingUserId) {
		this.receivingUserId = receivingUserId;
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}

}
