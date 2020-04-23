package com.koblan.taskManager.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.util.List;

@Document(collection = "users")
public class User {
	
	private String id;

    private String name;
    
    private String username;
    
    private String email;

    @JsonProperty(access=Access.WRITE_ONLY)
    private String password;
    
    private List<String> tasks;
    
//    private List<SharedTask> shared_tasks;

	public User() {}
    
   public User(String name, String username, String email, String password) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> getTasks() {
		return tasks;
	}

	public void setTasks(List<String> tasks) {
		this.tasks = tasks;
	}
/*
	public List<SharedTask> getShared_tasks() {
		return shared_tasks;
	}

	public void setShared_tasks(List<SharedTask> shared_tasks) {
		this.shared_tasks = shared_tasks;
	}
*/
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", tasks=" + tasks + "]";
	}
	
	

}
