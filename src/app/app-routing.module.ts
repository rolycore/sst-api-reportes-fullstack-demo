import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { ClienteComponent } from './cliente/cliente.component';
import { FormComponent } from './cliente/form/form.component';
import { EquipoclienteComponent } from './equipocliente/equipocliente.component';
import { EquipoformComponent } from './equipocliente/equipoform/equipoform.component';
import { OrdentrabajoComponent } from './ordentrabajo/ordentrabajo.component';
import { ReportetecnicooComponent } from './reportetecnicoo/reportetecnicoo.component';
import { OrdenesformComponent } from './ordentrabajo/ordenesform/ordenesform.component';
import { ReporteformComponent } from './reportetecnicoo/reporteform/reporteform.component';
import { ManejoarchivosComponent } from './manejoarchivos/manejoarchivos.component';
import { CapacidadmedicionComponent } from './capacidadmedicion/capacidadmedicion.component';
import { CmcformComponent } from './capacidadmedicion/cmcform/cmcform.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { ReportemantenimientoComponent } from './reportemantenimiento/reportemantenimiento.component';
import { ReportemantformComponent } from './reportemantenimiento/reportemantform/reportemantform.component';
import { AdminformComponent } from './board-admin/adminform/adminform.component';
import { ErrorPageComponent } from './error-page/error-page.component';
const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  {path:'usuarios/adminform',component: AdminformComponent},
  {path: 'usuarios/adminform/:id', component: AdminformComponent},
  { path: 'reset-password', component: ResetpasswordComponent},
  {path:'cliente',component:ClienteComponent },
  {path:'clientes/form',component: FormComponent },
  {path:'clientes/form/:id',component: FormComponent },
  {path:'equipocliente',component:EquipoclienteComponent },
  {path:'equipoclientes/equipoform',component:EquipoformComponent },
  {path:'equipoclientes/equipoform/:id',component:EquipoformComponent },
  {path:'ordentrabajo',component:OrdentrabajoComponent},
  {path:'ordenes/ordenesform',component:OrdenesformComponent},
  {path:'ordenes/ordenesform/:id',component:OrdenesformComponent},
  {path:'reportetecnico',component:ReportetecnicooComponent },
  {path:'reportes/reportesform',component:ReporteformComponent },
  {path:'reportes/reportesform/:id',component:ReporteformComponent },
  {path:'mantenimiento',component:ReportemantenimientoComponent},
  {path:'mantenimientos/reportemantform',component:ReportemantformComponent },
  {path:'mantenimientos/reportemantform/:id',component:ReportemantformComponent },
  {path:'capacidadmedicion',component:CapacidadmedicionComponent },
  {path:'capacidadmedicion/cmcform',component:CmcformComponent},
  {path:'capacidadmedicion/cmcform/:id',component:CmcformComponent},
  {path:'manejoarchivo',component: ManejoarchivosComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full' },
   // Ruta para manejar errores
   { path: 'error', component: ErrorPageComponent },

   // Ruta por defecto para redirigir errores 404
   { path: '**', redirectTo: '/error' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
