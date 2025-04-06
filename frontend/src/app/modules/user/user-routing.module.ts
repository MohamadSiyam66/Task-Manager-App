import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from '../user/components/dashboard/dashboard.component';
import { CreateTaskComponent } from './components/create-task/create-task.component';
import { UpdateTaskComponent } from './components/update-task/update-task.component';
import { ViewTaskComponent } from './components/view-task/view-task.component';

const routes: Routes = [
  {path:"dashboard",component:DashboardComponent},
  {path:"task",component:CreateTaskComponent},
  {path:"task/:id/edit",component:UpdateTaskComponent},
  {path:"task-details/:id",component:ViewTaskComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
