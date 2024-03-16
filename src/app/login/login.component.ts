import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { trigger, transition, style, animate } from '@angular/animations'; // Importa las animaciones

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('500ms ease-out', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('500ms ease-out', style({ opacity: 0 })),
      ])
    ])
  ]
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup; // Definimos un formulario Angular
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  username?: string;
  roles: string[] = ['ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER'];
  selectedRole: string = ''; // Variable para almacenar el rol seleccionado
  password: string = '';
  showPassword: boolean = false;

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }
  constructor(private authService: AuthService,
     private tokenStorage: TokenStorageService,
      private fb: FormBuilder,
      private router: Router,
      private activateRoute: ActivatedRoute) {
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
    this.username=this.loginForm.get('username')?.value;
    this.password=this.loginForm.get('password')?.value;
    this.authService.login(username, password).subscribe(
      data => {

        this.isLoggedIn = true;
        this.isLoginFailed = false;
        this.errorMessage = '';
     this.tokenStorage.saveToken(data.accessToken);
       this.tokenStorage.saveUser(data);

       this.reloadPage();
      // this.router.navigate(['/home']);
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
