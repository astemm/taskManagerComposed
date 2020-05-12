import { Component, OnInit, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { SseService } from '../../services/sse.service';
import { TaskService } from '../../services/task.service';
import { TaskInfo} from '../../models/task-info';
import { Task} from '../../models/task.model';
import { SharedTask} from '../../models/shared-task.model';
import { User } from 'src/app/models/user.model';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  num:number;
  user: User;
  sse:string;
  tasksList: Observable<TaskInfo[]>;
  newTask: Task;
  editedTask:Task;
  isCreated:boolean=false;
  isEdited:boolean=false;
  isShared:boolean=false;
  taskToShare:Task;
  newSharedTask: SharedTask;
  sharedEmail:string;
  createMessage:string;
  source:EventSource;

  constructor(private taskService: TaskService, private sseService:SseService,
    private tokenStorageService: TokenStorageService, private zone:NgZone) {
    }

  ngOnInit() {
    this.sseService
      .getServerSentEvent('https://tasks-management-system-app.herokuapp.com/events/sse')
    .subscribe(data => {if(data==this.user.id) this.reloadData();
      console.log(11+data); }); 
   
    this.taskService.getUserByName(this.tokenStorageService.getUsername()).subscribe(user=>{this.user=user;
      this.reloadData();}
      ); 
  }

  reloadData() {
   this.tasksList=this.taskService.getTaskList(this.user.id);
  this.tasksList.subscribe(result=>{this.num=result.length;}) //}
  }

  createTask() {
    this.newTask=new Task();
    this.isCreated=true;
  }

  addTask() {
    this.createMessage="";
    this.newTask.user=this.user;
    this.taskService.createTask(this.newTask).subscribe(
      data=>{this.createMessage="Task added"; this.isCreated=false;
  this.reloadData();
    },
       error=>this.createMessage=error
    )
    
  }

  deleteTask(id: string) {
    this.taskService.deleteTask(id)
      .subscribe(
        data => {this.createMessage="Task deleted";
         this.reloadData();
          console.log(data);
        },
        error => this.createMessage=error)
  }

  editTask(id: string) {
    this.isEdited=true;
    this.taskService.getTask(id).subscribe(task=>{this.editedTask=task;});
  }

  updateTask() {
     this.createMessage="";
     this.taskService.updateTask(this.editedTask).subscribe(
     data=>{this.createMessage="Task updated";
     this.isEdited=false; this.reloadData();},error=>this.createMessage=error
     )
  }

  share(id:string) {
    this.isShared=true;
    this.taskService.getTask(id).subscribe(task=>{this.taskToShare=task;}) 
  }

  shareTask() {
    var receivingUser:User;
    this.taskService.getUserByEmail(this.sharedEmail).subscribe(user=>{receivingUser=user;
      this.newSharedTask=new SharedTask(this.user.id,receivingUser.id,this.taskToShare);
      this.taskService.createSharedTask(this.newSharedTask).subscribe(
        data=>{this.createMessage="Task was shared"}, 
        error=>this.createMessage="Error in sharig task"
      ); this.isShared=false;
    },
      error=>this.createMessage="Enter correct email");
  }
}
