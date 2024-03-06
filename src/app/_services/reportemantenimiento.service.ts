import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import swal from 'sweetalert2'
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { ReporteMantenimiento } from '../models/reportemantenimiento';
@Injectable({
  providedIn: 'root'
})
export class ReportemantenimientoService {
  private baseUrl = 'https://appicmetrologia.icmetrologia.com:8080/api/v1/reporte-mantenimiento';//http://appicmetrologia.icmetrologia.com:8080/api/v1/reporte-tecnico
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient, private router: Router) { }
//Metodo para los errores y execepciones
private handleError(error: any): Observable<any> {
  console.error(error);
  swal.fire(error.mensaje, error.error, 'error');
  return throwError(error);
}
  //Listar todos los reportes
  //Listar todos los reportes
  listAll(): Observable<ReporteMantenimiento[]> {
    return this.http.get<ReporteMantenimiento[]>(this.baseUrl).pipe(
      catchError(this.handleError)
    );
  }
  //Listar Por ID
  listById(id: number): Observable<ReporteMantenimiento> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<ReporteMantenimiento>(url).pipe(
      catchError(this.handleError)
    );
  }

  //Crear Reporte

  create(reporteTecnico: ReporteMantenimiento): Observable<any> {
    return this.http.post(this.baseUrl, reporteTecnico).pipe(
      catchError(this.handleError)
    );
  }
  //Actualizar Reporte
  update(reporteTecnico: ReporteMantenimiento):Observable<any>{
    return this.http.put<any>(
      `${this.baseUrl}/${reporteTecnico.idrepmant}`,reporteTecnico,{headers: this.httpHeaders }).pipe(
        catchError(e =>{
          if(e.status==400){
            return throwError(e);
          }
          this.router.navigate(['/mantenimiento']);
          console.error(e.error.mensaje);
          Swal.fire( e.error.mensaje, e.error.error,'error');
          return throwError(e);
        })
      );
  }

  //Eliminar Reporte
  deleteById(id: number): Observable<any> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url).pipe(
      catchError(this.handleError)
    );
  }
  //Imprimir Reporte
  generateReport(reportId: number): Observable<HttpResponse<Blob>> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/pdf',
    });

    const params = new HttpParams().set('reportId', reportId.toString());

    return this.http.get(`${this.baseUrl}/generate`, {//http://62.72.24.222:8080/api/v1/reporte-tecnico/generate
      responseType: 'blob',
      headers,
      params,
      observe: 'response',
    });
  }
}
