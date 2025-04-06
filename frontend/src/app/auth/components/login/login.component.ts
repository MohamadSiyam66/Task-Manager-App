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

  constructor(private fb: FormBuilder,
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
      console.log(this.loginForm.value);
      this.authService.login(this.loginForm.value).subscribe(res => {
        console.log(res);
        if (res.userId != null) {  
          const user = {  
            id: res.userId,  
            role: res.userRole  
          }; 
          StorageService.saveUser(user);  
          StorageService.saveToken(res.jwt);  
          if (StorageService.isUserLoggedIn()) {  
            this.router.navigateByUrl('/user/dashboard');  
          } else {  
            this.snackBar.open("Login successful", "Close", { duration: 5000 });  
          }  
        } else {  
          this.snackBar.open("Invalid credentials", "Close", { duration: 5000, panelClass: "error-snackbar" });  
        }}); 
          
    }

  togglePaswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }
}
