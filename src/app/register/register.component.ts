import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: FormGroup;
  roles: string[] = ['ADMIN', 'MODERATOR', 'USER'];
  selectedRole: string = '';
  isSuccessful = false;
  isSignUpFailed = false;
  submitted = false;
  errorMessage = '';
  constructor(private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private formBuilder: FormBuilder
    ) {
      this.form = this.formBuilder.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        role: ['', Validators.required]
      });
    }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    const { username, email, password } = this.form.value; // Accede a los valores del formulario
    this.selectedRole = this.form.get('role')?.value; // Actualizar selectedRole con el valor del campo role

    this.authService.register(username, email, password, this.selectedRole).subscribe(
      data => {
        this.form.patchValue({
        username: username,
        email: email,
        password,
        role: this.selectedRole
        })
        console.log(data);
        this.isSuccessful = true;
        this.submitted = true; // Establece submitted en true cuando el formulario se envía
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
