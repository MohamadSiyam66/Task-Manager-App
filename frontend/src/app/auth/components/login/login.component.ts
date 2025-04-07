import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoading: boolean = false;
  hidePassword: boolean = true;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private storageService: StorageService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }
  
    this.isLoading = true;
    this.errorMessage = null;
  
    this.authService.login(this.loginForm.value).subscribe({
      next: (res) => {
        if (res.userId != null) {  
          const user = {  
            id: res.userId,  
            username: res.username,
            email: this.loginForm.value.username,
          }; 
          StorageService.saveUser(user);  
          StorageService.saveToken(res.jwt);  
          this.router.navigateByUrl('/user/dashboard');  
          this.snackBar.open("Login successful", "Close", { duration: 5000 });
        }
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.message;
        this.snackBar.open(err.message, "Close", { 
          duration: 5000, 
          panelClass: ['error-snackbar'] 
        });
      },
      complete: () => {
        this.isLoading = false;
      }
    }); 
  }

  togglePaswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }
}