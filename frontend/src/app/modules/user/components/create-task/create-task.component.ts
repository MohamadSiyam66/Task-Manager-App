import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.scss']
})
export class CreateTaskComponent {

  taskForm: FormGroup;
  listOfUsers:any=[];

  constructor(private userService:UserService, 
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router) {

    this.taskForm = this.fb.group({
      title: [null,[Validators.required]],
      description: [null],
      status: [null],
    });
  }

  onSubmit() {
    console.log(this.taskForm.value);
    this.userService.createTask(this.taskForm.value).subscribe(res => {
      console.log(res);
      if (res.id != null) {
        this.snackBar.open("Task created successfully", "Close", { duration: 5000 });
        this.router.navigateByUrl('/user/dashboard');
      } else {
        this.snackBar.open("Erro Creating Task!", "Close", { duration: 5000 });
      }
    });

  }
}
