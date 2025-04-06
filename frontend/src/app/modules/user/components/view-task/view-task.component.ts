import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.scss']
})
export class ViewTaskComponent {

  taskId:number = this.activatedRoute.snapshot.params['id'];
  taskData:any;

  constructor(private service:UserService,
    private activatedRoute:ActivatedRoute,
  ) { }

  ngOnInit(){
    this.getTaskById();
  }

  getTaskById(){
    this.service.getTaskById(this.taskId).subscribe((res)=>{
      this.taskData = res;
    })
  }

}
