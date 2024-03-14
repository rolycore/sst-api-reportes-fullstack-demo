import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {
  private baseUrl = 'https://localhost:8080/api/v1'; // Cambia esta URL por la URL de tu servidor Spring Boot

  constructor(private http: HttpClient) { }

  showErrorPage(): Observable<any> {
    return this.http.get(`${this.baseUrl}/error-page`);
  }
}
