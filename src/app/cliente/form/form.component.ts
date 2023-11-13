import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/_services/cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent implements OnInit {
  formulario: FormGroup = new FormGroup({});
  activeTab: number = 1;
  public cliente: Cliente = new Cliente();
  public titulo: string = 'Crear Cliente';
  public errores: string[] = [];

  constructor(
    private clienteService: ClienteService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    // Cargar la lista de clientes antes de inicializar el formulario
    this.cargarCliente();

    this.formulario = this.formBuilder.group({
      activo: [''],
      cod_cliente: [''],
      nombre: ['', Validators.required],
      apellido: [''],
      email: ['', [Validators.required, Validators.email]],
      nombre_comercial: ['', Validators.required],
      abreviatura: ['', Validators.required],
      razon_social: ['', Validators.required],
      ruc: ['', Validators.required],
      dv: ['', Validators.pattern(/^[1-9][0-9]{0,2}$/)],
      actividad_economica: [''],
      direccion: ['', Validators.required],
      correo_electronico: [''],
      nombre_contacto: ['', Validators.required],
      telefono_empresa: ['', Validators.required],
      telefono_servicio: [''],
      celular_servicio: ['', Validators.required],
      cargo_servicio: ['', Validators.required],
      correo_servicio: ['', Validators.email],
      celular_jefe: [''],
      telefono_jefe: [''],
      nombre_cobro: ['', Validators.required],
      cargo_cobro: ['', Validators.required],
      correo_cobro: ['', [Validators.required, Validators.email]],
      celular_cobro: ['', Validators.required],
      telefono_cobro: ['', Validators.required],
    });

    this.cargarCliente(); // Asegúrate de que esto no dependa de this.clientes
  }

  cargarCliente(): void {
    this.activateRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.clienteService.getCliente(id).subscribe((cliente) => {
          this.cliente = cliente;
          this.formulario?.patchValue({
            nombre: cliente.nombre,
            apellido: cliente.apellido,
            email: cliente.email,
            telefono_empresa: cliente.telefono_empresa,
            cod_cliente: cliente.cod_cliente,
            razon_social: cliente.razon_social,
            nombre_comercial: cliente.nombre_comercial,
            ruc: cliente.ruc,
            dv: cliente.dv,
            direccion: cliente.direccion,
            telefono_jefe: cliente.telefono_jefe,
            celular_jefe: cliente.celular_jefe,
            correo_electronico: cliente.correo_electronico,
            actividad_economica: cliente.actividad_economica,
            abreviatura: cliente.abreviatura,
            nombre_contacto: cliente.nombre_contacto,
            cargo_servicio: cliente.cargo_servicio,
            celular_servicio: cliente.celular_servicio,
            correo_servicio: cliente.correo_servicio,
            telefono_servicio: cliente.telefono_servicio,
            nombre_cobro: cliente.nombre_cobro,
            cargo_cobro: cliente.cargo_cobro,
            telefono_cobro: cliente.telefono_cobro,
            celular_cobro: cliente.celular_cobro,
            correo_cobro: cliente.correo_cobro,
            activo: cliente.activo,
            // Asigna los valores de los otros campos del formulario aquí
          });
        });
      }
    });
  }

  create(): void {
    this.clienteService.create(this.cliente).subscribe(
      (cliente) => {
        this.router.navigate(['/cliente']);
        Swal.fire(
          'Nuevo cliente',
          `El cliente ${this.cliente.nombre_comercial} ha sido creado con éxito!`,
          'success'
        );
      },
      (err) => {
        this.errores = err.error.errors ? (err.error.errors as string[]) : [];
        console.error('Código del error desde el backend: ' + err.status);
        Swal.fire(
          'Error al Crear cliente: ' + err.error.errors.mensaje + err.status,
          err.error.errors,
          'error'
        );
      }
    );
  }
  update(): void {
    this.clienteService.update(this.cliente).subscribe(
      (cliente) => {
        this.router.navigate(['/cliente']);
        Swal.fire(
          'Cliente actualizado',
          `El cliente ${this.cliente.nombre_comercial} ha sido actualizado con éxito!`,
          'success'
        );
      },
      (err) => {
        this.errores = err.error.errors ? (err.error.errors as string[]) : [];
        console.error('Código del error desde el backend: ' + err.status);
        Swal.fire(
          'Error al actualizar cliente: ' +
            err.error.errors.mensaje +
            err.status,
          err.error.errors,
          'error'
        );
      }
    );
  }

  onSubmit() {
    if (this.formulario) {
      if (this.formulario.valid) {
        // Realizar acciones con los datos del formulario
        this.create();

        console.log('Formulario válido. Enviar datos:', this.formulario.value);
      } else {
        // El formulario no es válido, mostrar errores o realizar acciones adicionales
        Swal.fire(
          'Oops...',
          `Ocurrio un error algunos campos no estan correctos!`,
          'error'
        );
        console.log('Formulario inválido. No se puede enviar.');
      }
    }
  }
}
