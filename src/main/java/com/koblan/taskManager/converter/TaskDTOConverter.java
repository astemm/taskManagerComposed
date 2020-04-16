package com.koblan.taskManager.converter;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.koblan.taskManager.models.SharedTask;
import com.koblan.taskManager.models.Task;
import com.koblan.taskManager.models.User;
import com.koblan.taskManager.payload.TaskDTO;
import com.koblan.taskManager.repositories.UserRepository;

public class TaskDTOConverter {
	
	//@Autowired
	//UserRepository userRepo;
	//Inject Spring Component to non Bean POJO class using SpringContext 
	private UserRepository getUserRepo() {
        return SpringContext.getBean(UserRepository.class);
    }
	
	public List<TaskDTO> convertTasks (List<Task> tasks) {
		List<TaskDTO> taskDtoList=new ArrayList<TaskDTO>();
		for(Task t:tasks) {TaskDTO task=this.convertTaskDTO(t);
		taskDtoList.add(task);
		}
		return taskDtoList;
	}
	
	public List<TaskDTO> convertSharedTasks (List<SharedTask> sharedTasks) {
		List<TaskDTO> taskDtoList=new ArrayList<TaskDTO>();
		for(SharedTask t:sharedTasks) {TaskDTO task=this.convertSharedTaskDTO(t);
		taskDtoList.add(task);
		}
		return taskDtoList;
	}
	
	public TaskDTO convertTaskDTO (Task task) {
		TaskDTO taskDto=new TaskDTO(task.getTaskId(),task.getTitle(),
				task.getDescription(),task.getEndDate(),task.getUser().getUsername());
	    return taskDto;
	}
	
	public TaskDTO convertSharedTaskDTO (SharedTask sharedTask) {
		Task task=sharedTask.getTask();
		User sendingUser=getUserRepo().findById((sharedTask.getSendingUserId())).get();
		TaskDTO taskDto=new TaskDTO(task.getTaskId(),task.getTitle(),
				task.getDescription(),task.getEndDate(),sendingUser.getUsername());
	    return taskDto;
	}
	

}
