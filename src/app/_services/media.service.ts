import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import Swal from 'sweetalert2';
@Injectable({
  providedIn: 'root'
})
export class MediaService {
  private urlEndpoint: string = 'https://localhost:8080/media';//appicmetrologia.icmetrologia.com
  private rutaUpload: string ='upload';
  constructor(
    private http: HttpClient
  ) { }
  //Metodo para los errores y execepciones
  public handleError(error: any): Observable<any> {
    console.error(error);
    Swal.fire(error.mensaje, error.error, 'error');
    return throwError(error);
  }

//Metodo para subir archivos
uploadFile(formData: FormData):Observable<any>{
  return this.http.post(`${this.urlEndpoint}/${this.rutaUpload}`,formData).pipe(
    catchError(this.handleError)
  );

}
subirFile(formData: FormData, maxWidth: number, maxHeight: number): Observable<any> {
  const params = new HttpParams()
    .set('maxWidth', maxWidth.toString())
    .set('maxHeight', maxHeight.toString());

  return this.http.post(`${this.urlEndpoint}/subir`, formData, { params }).pipe(
    catchError(this.handleError)
  );
}

getFile(filename: string): Observable<any> {
  const url = `${this.urlEndpoint}/${filename}`;

  // Opcional: Si necesitas enviar encabezados (headers) con la solicitud, puedes hacerlo aquí
  /*const headers = new HttpHeaders({
    'Authorization': 'Bearer tu_token_jwt', // Si es necesario
  });*/

  // Realiza la solicitud GET
  return this.http.get(url, { responseType: 'blob'});
}

}
