import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  taskList:any=[];
  constructor(private service:UserService,private snackBar: MatSnackBar) { 
    this.getTasks();
  }

  getTasks() {
    this.service.getAllTasks().subscribe(res => {
      console.log(res);
      this.taskList = res;
    });
  }
  
  deleteTask(id: number) {
    this.service.deleteTask(id).subscribe(res => {
      this.snackBar.open("Task deleted successfully", "Close", { duration: 5000 });
      this.getTasks();
    });
  }

}
