package com.koblan.taskManager.controllers;

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
import com.koblan.taskManager.payload.TaskDTO;
import com.koblan.taskManager.converter.TaskDTOConverter;
import com.koblan.taskManager.repositories.SharedTaskRepository;
import com.koblan.taskManager.repositories.TaskDALImpl;

@RestController
@RequestMapping("/shared")
public class SharedTaskController {
	
	@Autowired
	TaskDALImpl taskRepository;
	
	@Autowired
    SharedTaskRepository sharedRepository;
	
	 @GetMapping(value = "/tasks")
	 public ResponseEntity<List<SharedTask>> getAllTasks() throws NoSuchTaskException {
	        List<SharedTask> taskList = sharedRepository.getTasks();
	        return new ResponseEntity<>(taskList, HttpStatus.OK);
	    }
	 
	 @GetMapping(value = "/tasks/{id}")
	 public ResponseEntity<SharedTask> getTask(@PathVariable String id) throws NoSuchTaskException {
	        SharedTask task=sharedRepository.findById(id);
	        return new ResponseEntity<>(task, HttpStatus.OK);
	    }
	 
	 @PostMapping(value="/tasks")
     public ResponseEntity<SharedTask> addTask(@RequestBody SharedTask task) {
			 SharedTask ntask=sharedRepository.addSharedTask(task);
			 return new ResponseEntity<>(ntask, HttpStatus.CREATED);
		}
	 
	 @PutMapping(value = "/tasks")
	 public  ResponseEntity<SharedTask> updateTask(@RequestBody SharedTask task) throws NoSuchTaskException {
		    //Task task1=taskRepository.findById(id);
		    //if(task1.getTaskId()!=id) throw new NoSuchTaskException();
		    SharedTask ntask=sharedRepository.updateSharedTask(task);
	        return new ResponseEntity<>(ntask, HttpStatus.OK);
	    }
	 
	 @DeleteMapping("/tasks/{id}")
	 public ResponseEntity deleteTask(@PathVariable String id) throws NoSuchTaskException {
		    sharedRepository.deleteSharedTask(id);
			return new ResponseEntity(HttpStatus.OK);
		}
	 
	 //no use
	 @DeleteMapping("/tasks/task/{id}")
	 public ResponseEntity deleteSharedTaskByTaskId(@PathVariable String id) throws NoSuchTaskException {
		    sharedRepository.deleteSharedTaskByTaskId(id);
			return new ResponseEntity(HttpStatus.OK);
		}
	 
	 @GetMapping(value = "/tasks/shareduser/{id}")
	 public ResponseEntity<List<SharedTask>> getSharedTaskByUserId(@PathVariable String id) throws NoSuchTaskException {
		    List<SharedTask> sharedTasks=sharedRepository.findSharedTaskByUserId(id);
	        return new ResponseEntity<>(sharedTasks, HttpStatus.OK);
	    }
	
}
