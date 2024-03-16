import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { FooterComponent } from './footer/footer.component';
import { ClienteComponent } from './cliente/cliente.component';
import { EquipoclienteComponent } from './equipocliente/equipocliente.component';
import { OrdentrabajoComponent } from './ordentrabajo/ordentrabajo.component';
import { ReportetecnicooComponent } from './reportetecnicoo/reportetecnicoo.component';
import { FormComponent } from './cliente/form/form.component';
import { EquipoformComponent } from './equipocliente/equipoform/equipoform.component';
import { ClienteService } from './_services/cliente.service';
import { EquipoclienteService } from './_services/equipocliente.service';
import { OrdentrabajoService } from './_services/ordentrabajo.service';
import { ReportetecnicoService } from './_services/reportetecnico.service';
import { OrdenesformComponent } from './ordentrabajo/ordenesform/ordenesform.component';
import { ReporteformComponent } from './reportetecnicoo/reporteform/reporteform.component';
import { MediaService } from './_services/media.service';
import { ManejoarchivosComponent } from './manejoarchivos/manejoarchivos.component';
import { FileHandlerService } from './_services/file-handler.service';
import { CapacidadmedicionComponent } from './capacidadmedicion/capacidadmedicion.component';
import { CmcformComponent } from './capacidadmedicion/cmcform/cmcform.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { ResetpasswordService } from './_services/resetpassword.service';
import { DatePipe } from '@angular/common';
import { ReportemantenimientoComponent } from './reportemantenimiento/reportemantenimiento.component';
import { ReportemantenimientoService } from './_services/reportemantenimiento.service';
import { ReportemantformComponent } from './reportemantenimiento/reportemantform/reportemantform.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { AdminformComponent } from './board-admin/adminform/adminform.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { ErrorHandlerService } from './_services/error-handler.service';
import { LocationStrategy, Location, HashLocationStrategy } from '@angular/common';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    FooterComponent,
    ClienteComponent,
    EquipoclienteComponent,
    OrdentrabajoComponent,
    ReportetecnicooComponent,
    FormComponent,
    EquipoformComponent,
    OrdenesformComponent,
    ReporteformComponent,
    ManejoarchivosComponent,
    CapacidadmedicionComponent,
    CmcformComponent,
    ResetpasswordComponent,
    ReportemantenimientoComponent,
    ReportemantformComponent,
    AdminformComponent,
    ErrorPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxPaginationModule,
  ],
  providers: [authInterceptorProviders,
    ClienteService,
    EquipoclienteService,
    OrdentrabajoService,
    ReportetecnicoService,
    ReportemantenimientoService,
    MediaService,
    FileHandlerService,
    ResetpasswordService,
    DatePipe,
    ErrorHandlerService,
    { provide: LocationStrategy, useClass: HashLocationStrategy }
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
