import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.scss']
})
export class UpdateTaskComponent {

  id: number = this.route.snapshot.params['id'];
  updateTaskForm!: FormGroup;
  listOfUsers:any=[];

  constructor(private service:UserService, 
    private route:ActivatedRoute,
    private fb: FormBuilder,
    private userService:UserService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {

      this.getTaskById(this.id);
      this.updateTaskForm = this.fb.group({
            title: [null,[Validators.required]],
            description: [null],
            status: [null],
          });
  }

  getTaskById(id: number) {
    this.service.getTaskById(this.id).subscribe((res)=>{
      this.updateTaskForm.patchValue(res);
      console.log(res);
    });
  }

  onUpdate() {
    console.log(this.updateTaskForm.value);
    this.userService.updateTask(this.id,this.updateTaskForm.value).subscribe(res => {
      console.log("response",res);
      if (res.id != null) {
        this.snackBar.open("Task updated successfully", "Close", { duration: 5000 });
        this.router.navigateByUrl('/user/dashboard');
      } else {
        this.snackBar.open("Erro Creating Task!", "Close", { duration: 5000 });
      }
    });
  }
}
