import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from 'src/app/auth/services/storage/storage.service';

const BASE_URL = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})

export class UserService {

  constructor(private http:HttpClient) { }

  getUsers():Observable<any>{ {
      return this.http.get(BASE_URL + "api/users");
    }
  }

  createTask(task:any):Observable<any> {
    return this.http.post(BASE_URL + "api/user/task",task ,{
      headers: this.createAuthorizationHeader()
    });
  }

  getAllTasks(): Observable<any> {
    return this.http.get(BASE_URL + "api/user/tasks", {
      headers: this.createAuthorizationHeader()
    });
  }

  getTaskById(id:number):Observable<any> {
    return this.http.get(BASE_URL + "api/user/task/" + id ,{
      headers: this.createAuthorizationHeader()
    });
  }

  deleteTask(id:number):Observable<any> {
    return this.http.delete(BASE_URL + "api/user/task/" + id ,{
      headers: this.createAuthorizationHeader()
    });
  }

  updateTask(id:number, task:any):Observable<any> {
    return this.http.put(BASE_URL + `api/user/task/${id}`,task ,{
      headers: this.createAuthorizationHeader()
    });
  }

private createAuthorizationHeader(): HttpHeaders {
  return new HttpHeaders().set('Authorization', 'Bearer ' + StorageService.getToken());
}

}
