import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.scss']
})
export class ViewTaskComponent {

  taskId:number = this.activatedRoute.snapshot.params['id'];
  taskData:any;
  isLoading = false;
  taskList:any=[];
  errorMessage = '';

  constructor(private service:UserService,
    private activatedRoute:ActivatedRoute,
    private snackBar: MatSnackBar,
    private router: Router
  ) { }

  ngOnInit(){
    this.getTaskById();
  }

  getTaskById(){
    this.service.getTaskById(this.taskId).subscribe((res)=>{
      this.taskData = res;
    })
  }

  getTasks(): void {
    this.isLoading = true;
    
    this.service.getAllTasks().subscribe({
      next: (res) => {
        this.taskList = res;
        this.isLoading = false;
        
      },
      error: (err) => {
        this.errorMessage = 'Failed to load tasks';
        this.isLoading = false;
        this.snackBar.open(this.errorMessage, 'Close', { duration: 5000 });
      }
    });
  }

  deleteTask(id: number): void {
    const snackBarRef = this.snackBar.open('Delete this task?', 'Delete', {
      duration: 4000
    });
  
    snackBarRef.onAction().subscribe(() => {
      this.isLoading = true;
      this.service.deleteTask(id).subscribe({
        next: () => {
          this.snackBar.open('Task deleted successfully', 'Close', { duration: 3000 });
          this.router.navigate(['/user/dashboard']);
        },
        error: (err) => {
          this.isLoading = false;
          this.snackBar.open('Failed to delete task', 'Close', { duration: 3000 });
        }
      });
    });
  }

}
