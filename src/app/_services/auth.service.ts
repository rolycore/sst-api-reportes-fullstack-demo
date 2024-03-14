import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError  } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../models/user';
import Swal from 'sweetalert2';
const AUTH_API = 'https://localhost:8080/api/auth/';//https://appicmetrologia.icmetrologia.com:8080/api/auth/

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }
  //Metodo para los errores y execepciones
  public handleError(error: any): Observable<any> {
    console.error(error);
    Swal.fire(error.mensaje, error.error, 'error');
    return throwError(error);
  }

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
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(AUTH_API + 'usuarios');
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(AUTH_API + `usuario/${id}`);
  }

  updateUser(id: number, user: User): Observable<any> {
    return this.http.put<any>(AUTH_API + `usuario/${id}`, user);
  }

  deleteUser(id: number): Observable<User> {
    return this.http.delete<User>(AUTH_API + `usuario/${id}`);
  }

  getUsersWithRoles(): Observable<any[]> {
    return this.http.get<any[]>(AUTH_API + `usersWithRoles`);
  }
}
