import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { EquipoCliente } from '../models/equipocliente';
import Swal from 'sweetalert2'
import { Router } from '@angular/router';
import { MediaService } from './media.service';

@Injectable({
  providedIn: 'root'
})
export class EquipoclienteService {


  private baseUrl = 'https://localhost:8080/api/v1/equipos-clientes'; // http://appicmetrologia.icmetrologia.com:8080/api/v1/equipos-clientesReemplaza con la URL de tu backend
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  private httpHeadersMultipart = new HttpHeaders({'Content-Type': 'multipart/form-data'});
  private headersBoundary = new HttpHeaders({
    'Content-Type': 'multipart/form-data; boundary=---------------------------1234567890'
  });
  constructor(private http: HttpClient,
    private router: Router,
    private uploadImagen: MediaService
    ) {

    }
  //Metodo para los errores y execepciones
  public handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('Error del cliente:', error.error.message);
    } else {
      console.error(`Código de error: ${error.status}, ` + `Error: ${error.error}`);
    }
    return throwError('Ocurrió un error. Por favor, inténtalo de nuevo más tarde.');
  }
 //Metodo get que muestra todos los equiposclientes

 findAllEquipos(): Observable<EquipoCliente[]> {
  return this.http.get<EquipoCliente[]>(this.baseUrl).pipe(
    catchError(this.handleError)
  );
}

 // Método para crear un equipo con o sin imagen
 createEquipo(equipo: EquipoCliente): Observable<any> {
  return this.http.post<any>(this.baseUrl, equipo, {
    headers: this.httpHeaders,
  }).pipe(
    catchError(e => {
      if(e.status==400){
        return throwError(e);
      }
      console.error(e.error.mensaje);
      Swal.fire(e.error.mensaje, e.error.error, 'error');
      return throwError(e);
    }),
    map((response: EquipoCliente | null) => {
      if (response === null) {
        this.router.navigate(['/equipocliente']);
        throw new Error('Null response received');
      }
      return response;
    })
  );
}
//Bucar por ID
getEquipo(id: any): Observable<EquipoCliente> {
  return this.http.get<EquipoCliente>(`${this.baseUrl}/${id}`).pipe(
    catchError(e =>{
      this.router.navigate(['/equipocliente']);
      console.error(e.error.mensaje);

      Swal.fire('Error al editar',e.error.mensaje,'error');
      return throwError(e);
    }),
    map((equipo: EquipoCliente) => {
      equipo.nombre = equipo.nombre.toUpperCase();
     // equipo.categoria_equipo = equipo.categoria_equipo.toUpperCase();
      // Realiza cualquier otra modificación necesaria en los campos del cliente aquí
      return equipo;
    })
  );
}
//Actualizar Equipo por ID
update(equipo: EquipoCliente): Observable<any> {

  return this.http.put<any>(`${this.baseUrl}/${equipo.idEquipo}`,equipo,{headers: this.httpHeaders }).pipe(
    catchError(e =>{
      if(e.status==400){
        return throwError(e);
      }
      this.router.navigate(['/equipocliente']);
      console.error(e.error.mensaje);
      Swal.fire( e.error.mensaje, e.error.error,'error');
      return throwError(e);
    })
  );
}
//Borrar Equipo
delete(id: number): Observable<any> {
  const url = `${this.baseUrl}/${id}`;
  return this.http.delete(url, { headers: this.headersBoundary  }).pipe(
    catchError(this.handleError)
  );
}

//Impoirtar Equipos

importarEquipos(archivo: File): Observable<any> {
  const formData = new FormData();
  formData.append('archivo', archivo);

  return this.http.post<any>(`${this.baseUrl}/importar`, formData);
}


//Exportar Clientes
exportarEquipos(): Observable<Blob> {
  const headers = new HttpHeaders({ 'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

  return this.http.get(`${this.baseUrl}/exportar`, { responseType: 'blob', headers }).pipe(
    catchError(this.handleError)
  );
}
//buscar equipo por id cliente
getEquiposPorCliente(idCliente: number): Observable<any> {
  return this.http.get(`${this.baseUrl}/${idCliente}/equipos`).pipe(
    catchError(this.handleError)
  );
}

}
