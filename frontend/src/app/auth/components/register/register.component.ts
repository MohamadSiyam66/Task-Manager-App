import { Component } from '@angular/core';

interface RegisterFormData {
  username: string;
  password: string;
  confirmpassword: string;
}
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/auth/auth.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent {
  registerForm: FormGroup;
  isLoading: boolean = false;
  hidePassword: boolean = true;

  constructor(private fb: FormBuilder,private authService: AuthService, private router: Router, private snackBar: MatSnackBar) {
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
          this.snackBar.open('Registered successfully', 'Close', { duration: 3000 });
          this.router.navigate(['/login']);
        },
        error: (err: { error: { message?: string } }): void => {
          this.snackBar.open(`Registration failed: ${err.error.message || 'Unknown error'}`, 'Close', { duration: 3000 });
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