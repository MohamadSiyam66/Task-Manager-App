import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateTaskComponent } from './components/create-task/create-task.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AngularMaterialModule } from 'src/app/AngularMaterialModule';
import { UpdateTaskComponent } from './components/update-task/update-task.component';
import { ViewTaskComponent } from './components/view-task/view-task.component';


@NgModule({
  declarations: [
    DashboardComponent,
    CreateTaskComponent,
    UpdateTaskComponent,
    ViewTaskComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AngularMaterialModule
  ]
})
export class UserModule { }
