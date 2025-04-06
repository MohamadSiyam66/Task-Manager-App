import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  
  taskList:any=[];
  searchForm:FormGroup
  isLoading = false;
  errorMessage = '';

  constructor(
    private service: UserService,
    private snackBar: MatSnackBar, 
    private fb: FormBuilder
  ) {
    this.searchForm = this.fb.group({
      status: [null],
    });
  }

  onStatusChange(newStatus: string): void {
    this.isLoading = true;
    this.errorMessage = 'No Such Tasks';
    
    if (newStatus === 'ALL') {
      this.getTasks();
      return;
    }

    this.service.filterTask(newStatus).subscribe({
      next: (res) => {
        this.taskList = res;
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to filter tasks';
        this.isLoading = false;
        this.snackBar.open(this.errorMessage, 'Close', { duration: 5000 });
      }
    });
  }

  ngOnInit(): void {
    this.getTasks();
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
          this.getTasks();
        },
        error: (err) => {
          this.isLoading = false;
          this.snackBar.open('Failed to delete task', 'Close', { duration: 3000 });
        }
      });
    });
  }

}
