import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../_services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent {
  email: string = '';
  message: string = '';
  isSuccessful = false;
  isResetFailed = false;
  submitted = false;
  errorMessage = '';
  newPassword: string = '';


  constructor(private http: HttpClient, private reset: AuthService,
    private route: ActivatedRoute,
    private router: Router) {}

  onSubmit() {
    this.reset.resetPassword(this.email, this.newPassword)
    .subscribe(
      response => {
        // Manejar la respuesta exitosa, por ejemplo, mostrar un mensaje de éxito
        Swal.fire(
            'Restablecer Contraseña',
            `Contraseña restablecida con éxito!`,
            'success'
          );

        this.message='Password reset successfully!';
        console.log('Contraseña restablecida con éxito', response);
        this.isSuccessful = true;
         this.submitted = true; // Establece submitted en true cuando el formulario se envía
          this.isResetFailed = false;
          this.router.navigate(['login']);
      },
      error => {
        // Manejar errores, por ejemplo, mostrar un mensaje de error
        console.error('Error al restablecer la contraseña', error);
        this.errorMessage = error.error.message;
        this.isResetFailed = true;

      }
    );
    }


}
