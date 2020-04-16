package com.koblan.taskManager.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koblan.taskManager.exceptions.NoSuchTaskException;
import com.koblan.taskManager.models.SharedTask;
import com.koblan.taskManager.models.Task;
import com.koblan.taskManager.models.User;
import com.koblan.taskManager.payload.TaskDTO;
import com.koblan.taskManager.converter.TaskDTOConverter;
import com.koblan.taskManager.repositories.SharedTaskRepository;
import com.koblan.taskManager.repositories.TaskDALImpl;
import com.koblan.taskManager.repositories.UserRepository;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TaskController {
	
	@Autowired
	TaskDALImpl taskRepository;
	
	@Autowired
    SharedTaskRepository sharedRepository;
	
	@Autowired
    UserRepository userRepository;
	
	
	 @GetMapping(value = "/tasks")
	 public ResponseEntity<List<Task>> getAllTasks() throws NoSuchTaskException {
	        List<Task> taskList = taskRepository.getTasks();
	        return new ResponseEntity<>(taskList, HttpStatus.OK);
	    }
	 
	 @GetMapping(value = "/tasks/user/{userId}")
	 public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable String userId) throws NoSuchTaskException {
		    TaskDTOConverter converter=new TaskDTOConverter();
		    User user=userRepository.findById(userId).get();
		    List<TaskDTO> dtoList=new ArrayList<TaskDTO>();
		    if(user.getTasks()!=null) {
	        List<Task> taskList = taskRepository.getTasksByUser(user.getTasks());
	        List<TaskDTO> dtoList1=converter.convertTasks(taskList);
	        dtoList.addAll(dtoList1);
		    }
	        List<SharedTask> sharedTaskList = sharedRepository.findSharedTaskByUserId(userId);
	        List<TaskDTO> dtoList2=converter.convertSharedTasks(sharedTaskList);
	        dtoList.addAll(dtoList2);
	        return new ResponseEntity<>(dtoList, HttpStatus.OK);
	    }
	 
	 @GetMapping(value = "/tasks/{id}")
	 public ResponseEntity<Task> getTask(@PathVariable String id) throws NoSuchTaskException {
	        Task task=taskRepository.findById(id);
	        return new ResponseEntity<>(task, HttpStatus.OK);
	    }
	 
	 @PostMapping(value="/tasks")
     public ResponseEntity<Task> addTask(@RequestBody Task task) {
			 Task ntask=taskRepository.addTask(task);
			 return new ResponseEntity<>(ntask, HttpStatus.CREATED);
		}
	 
	 @PutMapping(value = "/tasks")
	 public  ResponseEntity<Task> updateTask(@RequestBody Task task) throws NoSuchTaskException {
		    //Task task1=taskRepository.findById(id);
		    //if(task1.getTaskId()!=id) throw new NoSuchTaskException();
		    Task ntask=taskRepository.updateTask(task);
		    
	        return new ResponseEntity<>(ntask, HttpStatus.OK);
	    }
	 
	 @DeleteMapping("/tasks/{id}")
	 public ResponseEntity deleteTask(@PathVariable String id) throws NoSuchTaskException {
		    taskRepository.deleteTask(id);
			return new ResponseEntity(HttpStatus.OK);
		}
	 
	 @PostMapping(value="/tasks/shared")
     public ResponseEntity<SharedTask> addSharedTask(@RequestBody SharedTask sharedTask) {
			 SharedTask stask=taskRepository.shareTask(sharedTask);
			 return new ResponseEntity<>(stask, HttpStatus.CREATED);
		}

}
