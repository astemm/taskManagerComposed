package com.koblan.taskManager.payload;

import com.koblan.taskManager.models.User;

public class UserDTO {
	String id;
	String name;
	String email;
	String username;
	
	public UserDTO() {}
	
	public UserDTO(User user) {
		this.id=user.getId();
		this.name=user.getName();
		this.username=user.getUsername();
		this.email=user.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
