import { HttpClient, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileHandlerService {
  private baseUrl = 'https://appicmetrologia.icmetrologia.com:8080/api/v1/files'; // Reemplaza con la URL de tu backend
  constructor(private http: HttpClient) { }

  public upload(formData: FormData): Observable<HttpEvent<string[]>> {
    return this.http.post<string[]>(this.baseUrl + "/upload", formData, {
      reportProgress: true,
      observe: "events"
    });
  }

  public download(filename: string): Observable<HttpEvent<Blob>> {
    return this.http.get(this.baseUrl + "/download" + "/" + filename, {
      reportProgress: true,
      observe: "events",
      responseType: "blob"
    });
  }

}
