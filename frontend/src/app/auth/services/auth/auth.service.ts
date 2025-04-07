import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

interface LoginResponse {
  jwt: string;
  userId: number;
  username: string;
}

interface RegisterResponse {
  id: number;
  username: string;
  email: string;
  // Add other user properties as needed
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth/';

  constructor(private http: HttpClient) {}

  register(registerRequest: any): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.baseUrl}register`, registerRequest).pipe(
      catchError(this.handleError)
    );
  }

  login(loginRequest: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}login`, loginRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'An unknown error occurred';
        
        if (error.status === 403) {
          errorMessage = error.error || 'Invalid username or password';
        } else if (error.status === 0) {
          errorMessage = 'Unable to connect to server. Please check your network connection.';
        } else if (error.error && typeof error.error === 'string') {
          errorMessage = error.error;
        }
        
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred';
    
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      if (error.status === 0) {
        errorMessage = 'Unable to connect to server. Please check your network connection.';
      } else if (error.status === 401) {
        errorMessage = error.error || 'Invalid username or password';
      } else if (error.status === 406) {
        errorMessage = error.error || 'Username already exists';
      } else if (error.error && typeof error.error === 'string') {
        errorMessage = error.error;
      } else {
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
    }
    
    return throwError(() => new Error(errorMessage));
  }
}