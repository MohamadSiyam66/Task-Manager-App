import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth/auth.service';

interface RegisterFormData {
  username: string;
  password: string;
  confirmpassword: string;
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent {
  registerForm: FormGroup;
  isLoading: boolean = false;
  hidePassword: boolean = true;

  constructor(private fb: FormBuilder,
    private authService: AuthService, 
    private router: Router, 
    private snackBar: MatSnackBar
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmpassword: ['', [Validators.required]]
    },);
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.isLoading = true;
      this.authService.register(this.registerForm.value as RegisterFormData).subscribe({
        next: (): void => {
          this.snackBar.open('Registered successfully', 'Close', {
            duration: 3000,
            panelClass: 'success-snackbar'
          });
          this.router.navigate(['/login']);
        },
        error: (err: { status: number; error: { message?: string } }): void => {
          let errorMessage = 'Registration failed!';
  
          if (err.status === 406) {
            errorMessage = 'Registration failed! User Already Exists';
          } else if (err.error?.message) {
            errorMessage = `Registration failed: ${err.error.message}`;
          }
  
          this.snackBar.open(errorMessage, 'Close', {
            duration: 3000,
            panelClass: 'error-snackbar'
          });
  
          this.registerForm.reset();
          this.isLoading = false;
        }
      });
    }
  }
  

  togglePaswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
}
}

export const confirmPasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password');
  const confirmPassword = control.get('confirmpassword');
  return password && confirmPassword && password.value !== confirmPassword.value
    ? { passwordsMismatch: true }
    : null;
};