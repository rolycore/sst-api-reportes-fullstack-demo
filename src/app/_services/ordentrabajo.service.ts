import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, throwError} from 'rxjs';
import { catchError, finalize, map } from 'rxjs/operators';
import { OrdenTrabajo } from '../models/ordentrabajo';
import swal from 'sweetalert2'
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class OrdentrabajoService {
  private baseUrl = 'https://appicmetrologia.icmetrologia.com:8080/api/v1/ordenes-trabajos';//appicmetrologia.icmetrologia.com
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient,private router: Router) { }
  //Metodo para los errores y execepciones
  private handleError(error: any): Observable<any> {
    console.error(error);
    swal.fire(error.mensaje, error.error, 'error');
    return throwError(error);
  }
// listar Ordenes de Trabajos
listarOrdenes(): Observable<OrdenTrabajo[]> {
  return this.http.get<OrdenTrabajo[]>(this.baseUrl).pipe(
    catchError(this.handleError)
  );
}
//listar Ordenes de Trabajos por ID
listarOrden(id: any):Observable<OrdenTrabajo>{
  return this.http.get<OrdenTrabajo>(`${this.baseUrl}/${id}`).pipe(
    catchError(e =>{
      this.router.navigate(['/ordentrabajo']);
      console.error(e.error.mensaje);

      Swal.fire('Error al editar',e.error.mensaje,'error');
      return throwError(e);
    }),
    map((orden: OrdenTrabajo) => {
      orden.no_ordent = orden.no_ordent;

      // Realiza cualquier otra modificación necesaria en los campos del cliente aquí
      return orden;
    })
  );
}
  //Creacion de Ordenes de Trabajo
  create(ordenTrabajo: OrdenTrabajo): Observable<any> {
    return this.http.post<any>(this.baseUrl, ordenTrabajo, {
      headers: this.httpHeaders
    }).pipe(
      catchError(this.handleError)
    );
  }
//Actualizar Ordenes por ID
  update(ordenTrabajo: OrdenTrabajo): Observable<any> {

    return this.http.put<any>(
      `${this.baseUrl}/${ordenTrabajo.idOT}`, ordenTrabajo, {
      headers: this.httpHeaders
    }).pipe(        catchError(e =>{
      if(e.status==400){
        return throwError(e);
      }
      this.router.navigate(['/ordentrabajo']);
      console.error(e.error.mensaje);
      Swal.fire( e.error.mensaje, e.error.error,'error');
      return throwError(e);
    })
  );
  }
//Imprimir Ordenes

/*exportPdf(ordenTrabajo: OrdenTrabajo): Observable<Blob> {
  const url = `${this.baseUrl}/export-pdf-orden-trabajo`;

  // Construir los parámetros de la URL
  const params = new HttpParams()
    .set('id', ordenTrabajo.idOT.toString()); // Ajusta 'id' según el nombre del parámetro en tu API

  // Guardar la configuración actual de responseType
  const currentResponseType = this.httpHeaders.get('responseType');

  // Configurar responseType como 'blob' solo para esta solicitud
  this.httpHeaders = this.httpHeaders.set('responseType', 'blob');

  return this.http.get<Blob>(url, {
    headers: this.httpHeaders,
    params: params
  }).pipe(
    catchError(this.handleError),
    // Restaurar la configuración original de responseType después de la solicitud
    finalize(() => {
      if (currentResponseType !== null) {
        this.httpHeaders = this.httpHeaders.set('responseType', currentResponseType);
      }
    })
  );
}*/
exportPdf(idOT: number): Observable<HttpResponse<ArrayBuffer>> {
  const url = `https://localhost:8080/api/v1/export-pdf-orden-trabajo/${idOT}`;
  return this.http.get(url, {
    responseType: 'arraybuffer',
    observe: 'response',
  });
}
exportOrden(idOT: number): Observable<Blob> {
  return this.http.get(`https://localhost:8080/api/v1/ordenes-trabajos/exportOrden?idOT=${idOT}`, {
    responseType: 'blob',
  });
}

//imprimir y guardar orden
generarPdf(ordenTrabajo: OrdenTrabajo): Observable<any> {
  const url = `${this.baseUrl}/generar-pdf`;
  const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  return this.http.post<any>(url, ordenTrabajo, httpOptions);
}
generateReport(orderId: number): Observable<HttpResponse<Blob>> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/pdf',
    });

    const params = new HttpParams().set('orderId', orderId.toString());

    return this.http.get(`${this.baseUrl}/generate`, {
      responseType: 'blob',
      headers,
      params,
      observe: 'response',
    });
  }


}
