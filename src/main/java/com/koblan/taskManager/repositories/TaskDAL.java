package com.koblan.taskManager.repositories;

import java.util.List;

import com.koblan.taskManager.models.SharedTask;
import com.koblan.taskManager.models.Task;
import com.koblan.taskManager.models.User;

public interface TaskDAL {
	
	List<Task> getTasks();
	Task findById(String taskId);
	Task addTask(Task task);
	Task updateTask(Task task);
	void deleteTask(String taskId);
	SharedTask shareTask (SharedTask shareTAsk);

}
