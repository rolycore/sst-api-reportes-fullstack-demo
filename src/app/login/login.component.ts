import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup; // Definimos un formulario Angular
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = ['ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER'];
  selectedRole: string = ''; // Variable para almacenar el rol seleccionado
  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private fb: FormBuilder,private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['', Validators.required]
    });
  }

  ngOnInit(): void {

  }

  onSubmit(): void {
        if (this.loginForm.invalid) {
      return;
    }
  if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;

    }

    const { username, password} = this.loginForm.value;
    this.selectedRole = this.loginForm.get('role')?.value;// Actualizar selectedRole con el valor del campo role
    this.authService.login(username, password).subscribe(
      data => {
        this.isLoggedIn = true;
        this.isLoginFailed = false;
        this.errorMessage = '';
     this.tokenStorage.saveToken(data.accessToken);
       this.tokenStorage.saveUser(data);




        this.reloadPage();

      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }
  navigateToResetPassword() {
    // Navegar a la página de restablecimiento de contraseña
    this.router.navigate(['/reset-password']);
  }
}
