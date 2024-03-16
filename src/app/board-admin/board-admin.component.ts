import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { trigger, transition, style, animate } from '@angular/animations';
import { User } from '../models/user';
import { AuthService } from '../_services/auth.service';
import { tap } from 'rxjs';
import { Roles } from '../models/roles';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css'],
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
export class BoardAdminComponent implements OnInit {
  loading: boolean = false;
  content?: string;
  username: string = '';
  users!: User[]; // Modificado para usar la clase User
  pages!: number;

  constructor(private userService: UserService,
              private tokenStorageService: TokenStorageService,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.userService.getAdminBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );

    // Obtener el nombre del usuario del TokenStorageService
    const user = this.tokenStorageService.getUser();
    this.username = user.username;
    this.loadUsers();
  }


  loadUsers(): void {
    this.authService.getUsersWithRoles().subscribe(users => {
      if (users && Array.isArray(users)) {
        this.users = users.map((user: any) => {
          if (Array.isArray(user) && user.length >= 2) {
            // Si user es un array y tiene al menos 2 elementos, asumimos que el primer elemento es el objeto de usuario y el segundo es el rol
            user = user[0];
          }

          if (user.roles && Array.isArray(user.roles)) {
            const roles: Roles[] = user.roles.map((role: any) => {
              const roleObject: Roles = new Roles();
              roleObject.id = role.id;
              roleObject.name = role.name;
              return roleObject;
            });

            const newUser: User = new User();
            newUser.id = user.id;
            newUser.creado_por = user.creadoPor;
            newUser.fecha_hora_creacion = new Date(user.fechaHoraCreacion);
            newUser.fecha_hora_ultima_actualizacion = new Date(user.fechaHoraUltimaActualizacion);
            newUser.ultima_actualizacion_por = user.ultimaActualizacionPor;
            newUser.email = user.email;
            newUser.password = user.password;
            newUser.reset_password_token = user.resetPasswordToken;
            newUser.username = user.username;
            newUser.roles = roles;

            return newUser;
          } else {
            console.warn('La propiedad roles no está definida o no es un array para el usuario:', user);
            return null;
          }
        }).filter((user: User | null) => user !== null) as User[]; // Aserción no nulo aquí
      } else {
        console.error('La respuesta del servicio no es un array de usuarios:', users);
      }
    });
  }


  getRoleNames(user: User): string {
    return user.roles.map(role => role.name).join(', ');
  }


 /* deleteUser(id: number): void {
    this.authService.deleteUser(id).subscribe(response => {
      console.log('Usuario eliminado exitosamente', response);
      this.loadUsers(); // Opcional: recargar la lista de usuarios después de eliminar uno
    });
  }*/
  deleteUser(id: number): void {
    // Mostrar la alerta de confirmación
    Swal.fire({
      title: '¿Está seguro?',
      text: '¿Seguro que desea eliminar al cliente?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        // Si el usuario confirma la eliminación, llamar al servicio para eliminar el usuario
        this.authService.deleteUser(id).subscribe(
          () => {
            // Si la eliminación es exitosa, mostrar una alerta de éxito
            Swal.fire(
              '¡Eliminado!',
              'El Usuario ha sido eliminado correctamente.',
              'success'
            );
            // Actualizar la lista de usuarios después de eliminar uno
            this.loadUsers();
          },
          (error) => {
            // Si hay un error al eliminar el usuario, mostrar una alerta de error
            Swal.fire(
              '¡Error!',
              'Ocurrió un error al eliminar el usuario.',
              'error'
            );
          }
        );
      }
    });
  }
}
