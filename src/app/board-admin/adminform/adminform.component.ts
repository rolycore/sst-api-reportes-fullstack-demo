import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { trigger, transition, style, animate } from '@angular/animations'; // Importa las animaciones
import { User } from 'src/app/models/user';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Roles } from 'src/app/models/roles';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-adminform',
  templateUrl: './adminform.component.html',
  styleUrls: ['./adminform.component.css'],
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
export class AdminformComponent implements OnInit{
  formulario: FormGroup = new FormGroup({});
  public usuario: User = new User();
  public titulo: string = 'Panel Administrador de usuarios';
  public titulo1: string = 'Permisos por ROLES'
  roles$!: Observable<Roles[]>; // Observable para cargar los roles

  selectedRole: Roles | null = null;
  isSuccessful = false;
  isSignUpFailed = false;
  submitted = false;
  public errores: string[] = [];
  password: string = '';
  showPassword: boolean = false;

  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }
  constructor(private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private formBuilder: FormBuilder,
    private router: Router,
    private activateRoute: ActivatedRoute) {}

    ngOnInit(): void {
      this.formulario = this.formBuilder.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        role: ['', Validators.required] // Actualizado a 'role'
      });

      // Cargar roles usando el servicio getAllRoles
      this.roles$ = this.authService.getAllRoles();

      this.cargarUsuario();
    }

    cargarUsuario(): void {
      this.activateRoute.params.subscribe((params) => {
        let id = params['id'];
        if (id) {
          this.authService.getUserById(id).subscribe((usuario) => {
            this.usuario = usuario;
            this.formulario.patchValue({
              username: usuario.username,
              password: usuario.password,
              email: usuario.email,
              role: usuario.roles // Asegúrate de que 'role' sea el campo correcto en tu modelo User
            });
          });
        }
      });
    }

    updateUser(id: number, updatedUser: User): void {
      const roleId = this.formulario.get('role')?.value;

      // Obtener el rol por su ID utilizando el servicio getRoleById
      this.authService.getRoleById(roleId).subscribe(
        (selectedRole) => {
          if (selectedRole) {
            this.selectedRole = selectedRole;
            updatedUser.roles = [selectedRole];
          } else {
            console.error('Role not found');
            return;
          }

          // Llamar al servicio para actualizar el usuario
          this.authService.updateUser(id, updatedUser).subscribe(
            response => {
              this.router.navigate(['/admin']);
              console.log('Usuario actualizado exitosamente', response);
            },
            error => {
              this.errores = error.error.errors ? (error.error.errors as string[]) : [];
              console.error('Código del error desde el backend: ' + error.status);
              console.error('Error al actualizar usuario:', error);
            }
          );
        },
        (error) => {
          console.error('Error al obtener el rol:', error);
        }
      );
    }
}
