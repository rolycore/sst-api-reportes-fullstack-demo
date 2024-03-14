import { ErrorHandler, Injectable } from '@angular/core';
import { Cliente } from '../models/cliente';
import { Observable, throwError,of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2'
import { Router } from '@angular/router';
import {catchError,map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private urlEndpoint: string = 'https://localhost:8080/api/v1/clientes'; //http://appicmetrologia.icmetrologia.com:8080/api/v1/clientes
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  private httpHeadersMultipart = new HttpHeaders({'Content-Type': 'multipart/form-data'});
  constructor(private http: HttpClient, private router: Router) {}
  //Metodo para los errores y execepciones
  public handleError(error: any): Observable<any> {
    console.error(error);
    Swal.fire(error.mensaje, error.error, 'error');
    return throwError(error);
  }
  //Metodo get que muestra todos los clientes
  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.urlEndpoint).pipe(
      tap(response =>{
        let clientes=response as Cliente[];
        clientes.forEach(cliente=>{
          console.log('ClienteService : tap 1');
          console.log(cliente.nombre);

        })
      }),
      map(response=> {
        let clientes=response as Cliente[];
        return clientes.map(cliente=> {
          cliente.nombre = cliente.nombre.toUpperCase();
          //cliente.apellido = cliente.apellido.toUpperCase();
          //let datePipe = new DatePipe('en-US');

          //cliente.createAt = formatDate(cliente.createAt,'EEEE dd, MMMM yyyy','es-PA');//formatDate(cliente.createAt,'dd-MM-yyyy','en-US');
          return cliente;
        });
      }),
      tap(response =>{
          console.log('ClienteService : tap 2');
          response.forEach(cliente=>{
           console.log(cliente.nombre);
          })
      })
    ); //forma de retornar el verbo get
    //return this.http.get(this.urlEndpoint).pipe(map((response) => as Cliente[])); //esta forma esta deprecada
  }
  //metodo get buscar Cliente por ID
  /*getCliente(id: any): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndpoint}/${id}`).pipe(
      catchError(e =>{
        this.router.navigate(['/clientes']);
        console.error(e.error.mensaje);

        swal.fire('Error al editar',e.error.mensaje,'error');
        return throwError(e);
      })
    );
  }*/
  obtenerClientePorId(id: any): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.urlEndpoint}/${id}`).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);

        Swal.fire('Error al encontrar id',e.error.mensaje,'error');
        return throwError(e);
      }),
      map((cliente: Cliente) => {
        cliente.nombre_comercial = cliente.nombre.toUpperCase();

        return cliente;
      })
    );
  }

  getCliente(id: any): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndpoint}/${id}`).pipe(
      catchError(e =>{
        this.router.navigate(['/cliente']);
        console.error(e.error.mensaje);

        Swal.fire('Error al editar',e.error.mensaje,'error');
        return throwError(e);
      }),
      map((cliente: Cliente) => {
        cliente.nombre = cliente.nombre.toUpperCase();
       // cliente.apellido = cliente.apellido.toUpperCase();
        // Realiza cualquier otra modificación necesaria en los campos del cliente aquí
        return cliente;
      })
    );
  }
  /*En este ejemplo, utilizamos el operador map para modificar los campos del cliente, en este caso, convirtiendo el nombre y el apellido en mayúsculas. Puedes realizar cualquier otra modificación o procesamiento que necesites dentro del bloque map.

  De esta manera, cuando llames al método getCliente(id) desde tu componente FormComponent, obtendrás el objeto completo del cliente, incluyendo los campos adicionales, como el apellido, el email y cualquier otro campo definido en la clase Cliente.

  Recuerda que también debes asegurarte de que los campos adicionales estén definidos correctamente en la clase Cliente para que coincidan con la estructura de los datos devueltos por el servicio.

  Espero que esto te ayude a obtener los otros campos del cliente en tu servicio ClienteService. Si tienes alguna otra pregunta, no dudes en preguntar.*/






  //Metodo create que crea los clientes
  create(cliente: Cliente): Observable<any> {
    return this.http.post<any>(this.urlEndpoint, cliente, {
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
      map((response: Cliente | null) => {
        if (response === null) {
          this.router.navigate(['/cliente']);
          throw new Error('Null response received');
        }
        return response;
      })
    );
  }
  //Metodo update que actualizalos clientes por ID
  update(cliente: Cliente): Observable<any> {
    return this.http.put<any>(
      `${this.urlEndpoint}/${cliente.idCliente}`,cliente,{headers: this.httpHeaders }).pipe(
        catchError(e =>{
          if(e.status==400){
            return throwError(e);
          }
          this.router.navigate(['/cliente']);
          console.error(e.error.mensaje);
          Swal.fire( e.error.mensaje, e.error.error,'error');
          return throwError(e);
        })
      );
  }
  //Metodo delete que borra el cliente por ID
  delete(idCliente: number):Observable<Cliente>{
    return this.http.delete<Cliente>(`${this.urlEndpoint}/${idCliente}`,{headers: this.httpHeaders })
  }
  //Bucar cliente por nombre
  buscarClientesPorNombre(nombre: string): Observable<Cliente[]> {
    const url = `${this.urlEndpoint}/search?nombre=${nombre}`;
    return this.http.post<Cliente[]>(url, {}).pipe(
      catchError(this.handleError)
    );
  }
  //Impoirtar Clientes

  importarClientes(archivo: File): Observable<any> {
    const formData = new FormData();
    formData.append('archivo', archivo);

    return this.http.post<any>(`${this.urlEndpoint}/importar`, formData);
  }


  //Exportar Clientes
  exportarClientes(): Observable<Blob> {
    const headers = new HttpHeaders({ 'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

    return this.http.get(`${this.urlEndpoint}/exportar`, { responseType: 'blob', headers }).pipe(
      catchError(this.handleError)
    );
  }
}
