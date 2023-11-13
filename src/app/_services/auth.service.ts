import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError  } from 'rxjs';
import { catchError } from 'rxjs/operators';

const AUTH_API = 'https://appicmetrologia.icmetrologia.com:8080/api/auth/';//https://appicmetrologia.icmetrologia.com:8080/api/auth/

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, email: string, password: string, role: string): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username,
      email,
      password,
      role
    }, httpOptions);
  }
  resetPassword(email: string, newPassword: string): Observable<any> {
    const resetRequest = { email, newPassword };

    return this.http.put(AUTH_API + 'reset-password', resetRequest)
      .pipe(
        catchError((error) => {
          return throwError(error);
        })
      );
  }
}
