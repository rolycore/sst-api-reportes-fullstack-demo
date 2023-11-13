import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ResetpasswordService {
  private baseUrl = 'https://appicmetrologia.icmetrologia.com:8080/api/v1/reset-password'; //http://appicmetrologia.icmetrologia.com:8080/api/v1/reset-password La URL de tu backend
  constructor(private http: HttpClient) { }

  resetPassword(token: string, newPassword: string) {
    const url = `${this.baseUrl}/reset-password`;

    // Puedes enviar el token y la nueva contraseña al backend
    return this.http.post(url, { token, newPassword });
  }
}
