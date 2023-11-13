import { Component, OnInit } from '@angular/core';
import { EquipoclienteService } from 'src/app/_services/equipocliente.service';
import { ClienteService } from 'src/app/_services/cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import Swal from 'sweetalert2';
import { OrdentrabajoService } from 'src/app/_services/ordentrabajo.service';
import { OrdenTrabajo } from 'src/app/models/ordentrabajo';
import { EquipoCliente } from 'src/app/models/equipocliente';
import { Cliente } from 'src/app/models/cliente';
interface ClienteId {
  idCliente: any;
}
interface EquipoId {
  idEquipo: any;
}
interface EquipoFile {
  // ...
  imagen_equipo?: File;
}
@Component({
  selector: 'app-ordenesform',
  templateUrl: './ordenesform.component.html',
  styleUrls: ['./ordenesform.component.css'],
})
export class OrdenesformComponent implements OnInit {
   loading: boolean = false;
  equipo: EquipoCliente = new EquipoCliente();
  cliente: Cliente = new Cliente();
  public formulario: FormGroup = new FormGroup({});
  activeTab: number = 1;
  public orden: OrdenTrabajo = new OrdenTrabajo();
  public titulo: string = 'Crear Orden';
  public titulocliente: string = 'Datos del Cliente';
  public tituloequipo: string = 'Datos del Equipo';
  clientes: any[] = []; // Lista de clientes
  clienteSeleccionado: number | null = null; // ID del cliente seleccionado
  clienteData: any; // Variable para almacenar los datos del cliente seleccionado
  equipos: any[] = []; // Lista de clientes
  equipoSeleccionado: number | null = null; // ID del equipo seleccionado
  equipoData: any; // Variable para almacenar los datos del cliente seleccionado

  public errores: string[] = [];
  // Propiedades para almacenar los datos del equipo seleccionado
  fabricante_ind!: string;
  modelo_ind!: string;
  serie_ind!: string;
  fabricande_rec!: string;
  modelo_rec!: string;
  serie_rec!: string;
  resolucion!: string;
  fabricante_sen!: string;
  modelo_sen!: string;
  serie_sen!: string;
  nombre_cliente!: string;
  direccion_cliente!: string;
  contacto_cliente!: string;
  telefono_contacto!: string;
  correo!: string;
  contacto_cargo!: string;
  erroresValidacion: any;
  pdfBytes: null | undefined;
  ordenGenerada: any;

  constructor(
    private ordenTrabajoService: OrdentrabajoService,
    private equipoClienteService: EquipoclienteService,
    private clienteService: ClienteService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}
  cargarOrden(): void {
    //cargando datos de equipo a la orden
    this.orden.equipo = this.equipo;
    //cargando datos del cliente a la orden
    this.orden.cliente = this.cliente;
    //activando parametros en el formulario

    // Ahora puedes acceder al nombre_comercial directamente desde el objeto cliente

    const direccionCliente = this.orden.cliente.direccion;
    const cargocontactoCliente = this.orden.cliente.cargo_servicio;
    const contactoCliente = this.orden.contacto_cliente;
    const telefonocontactoCliente = this.orden.telefono_contacto;

    //informacion del equipo


    this.activateRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.ordenTrabajoService.listarOrden(id).subscribe((orden) => {
          this.orden = orden;
          this.formulario?.patchValue({
            activo: this.orden.activo,
            cliente: this.orden.nombre_cliente,
            equipo: this.orden.nombre_equipo,
            no_ordent: this.orden.no_ordent,
            no_solicitud: this.orden.no_solicitud,
            no_ordencompra: this.orden.no_ordencompra,
            no_cotizacion: this.orden.no_cotizacion,
            fecha_entrega_certificado: this.orden.fecha_entrega_certificado,
            no_certificado: this.orden.no_certificado,
            no_reporte_tecnico: this.orden.no_reporte_tecnico,
            metrologo_responsable: this.orden.metrologo_responsable,
            fecha_calibracion: this.orden.fecha_calibracion,
            prox_calibracion: this.orden.prox_calibracion,
            direccion_cliente: direccionCliente,
            usuario_final: this.orden.usuario_final,
            lugar_calibracion: this.orden.lugar_calibracion,
            contacto_cliente: contactoCliente,
            telefono_contacto: telefonocontactoCliente,
            cargo: this.orden.cargo,
            correo: this.orden.correo,
            contacto_cargo: cargocontactoCliente,
            descripcion_recep: this.orden.descripcion_recep,
            fabricante_ind: this.orden.fabricante_ind,
            modelo_ind: this.orden.modelo_ind,
            serie_ind: this.orden.serie_ind,
            clase_ind: this.orden.clase_ind,
            alcance: this.orden.alcance,
            fabricande_rec: this.orden.fabricande_rec,
            modelo_rec: this.orden.modelo_rec,
            serie_rec: this.orden.serie_rec,
            resolucion: this.orden.resolucion,
            fabricante_sen: this.orden.fabricante_sen,
            modelo_sen: this.orden.modelo_sen,
            serie_sen: this.orden.serie_sen,
            observaciones: this.orden.observaciones,
            rechazotecno: this.orden.rechazotecno,
            motivo: this.orden.motivo,
            nombre_entrega: this.orden.nombre_entrega,
            cedula: this.orden.cedula,
            fecha_entrega: this.orden.fecha_entrega,
            metrologo_entrega: this.orden.metrologo_entrega,
            recibe_ibc: this.orden.recibe_ibc,
            entrego_con_ibc: this.orden.entrego_con_ibc,
            observaciones_entrega: this.orden.observaciones_entrega,
            fecha_entrega_ibc: this.orden.fecha_entrega_ibc,
            persona_entrega_ibc: this.orden.persona_entrega_ibc,
            recibe_certificado: this.orden.recibe_certificado,
            recibe_cedula: this.orden.recibe_cedula,
          });
        });
      }
    });
  }
  ngOnInit(): void {
    this.cargarOrden();
    this.formulario = this.formBuilder.group({
      activo: [''],
       cliente: ['', Validators.required],
  equipo: ['', Validators.required],
  nombre_equipo: ['', Validators.required],
  no_ordent: ['', Validators.required],
  no_solicitud: ['', Validators.required],
  no_ordencompra:['', Validators.required],
  no_cotizacion: ['', Validators.required],
  fecha_entrega_certificado: ['', Validators.required],
  no_certificado: ['', Validators.required],
  no_reporte_tecnico: ['', Validators.required],
  metrologo_responsable: ['', Validators.required],
  fecha_calibracion: ['', Validators.required],
  prox_calibracion: [''],
  nombre_cliente: ['', Validators.required],
  direccion_cliente: ['', Validators.required],
  usuario_final: ['', Validators.required],
  lugar_calibracion: ['', Validators.required],
  contacto_cliente: ['', Validators.required],
  telefono_contacto: ['', Validators.required],
  cargo: ['', Validators.required],
  correo: ['', [Validators.required, Validators.email]],
  contacto_cargo: ['', Validators.required],
  descripcion_recep: ['', Validators.required],
  fabricante_ind: ['', Validators.required],
  modelo_ind: ['', Validators.required],
  serie_ind: ['', Validators.required],
  clase_ind: ['', Validators.required],
  alcance: ['', Validators.required],
  fabricande_rec: ['', Validators.required],
  modelo_rec: ['', Validators.required],
  serie_rec: ['', Validators.required],
  resolucion: ['', Validators.required],
  fabricante_sen: ['', Validators.required],
  modelo_sen: ['', Validators.required],
  serie_sen: ['', Validators.required],
  observaciones: ['', Validators.required],
  rechazotecno: ['', Validators.required],
  motivo: [''],
  nombre_entrega: ['', Validators.required],
  cedula: ['', Validators.required],
  fecha_entrega: ['', Validators.required],
  metrologo_entrega: ['', Validators.required],
  recibe_ibc: ['', Validators.required],
  entrego_con_ibc: ['', Validators.required],
  observaciones_entrega: [''],
  fecha_entrega_ibc: [''],
  persona_entrega_ibc: ['', Validators.required],
  recibe_certificado:['', Validators.required],
  recibe_cedula: ['', Validators.required]

    });
    // Mover la obtención de la lista de clientes aquí
    this.clienteService.getClientes().subscribe((data: any[]) => {
      this.clientes = data;
    });
    // Mover la obtención de la lista de equipos aquí
    this.equipoClienteService.findAllEquipos().subscribe((data: any[]) => {
      this.equipos = data;
    });
    //this.cargarOrden();

  }
  // Función que se ejecuta cuando se selecciona un cliente en el select
  onSelectCliente(): void {
    // Obtener el ID del cliente seleccionado
    const idClienteSeleccionado = this.clienteSeleccionado;

    // Llamar al servicio para obtener los datos del cliente por su ID
    this.clienteService
      .getCliente(idClienteSeleccionado)
      .subscribe((cliente: Cliente) => {
        // Actualizar los campos de entrada con los datos del cliente
        this.clienteData = cliente;
        this.orden.nombre_cliente = cliente.nombre_comercial;
        this.orden.direccion_cliente = cliente.direccion;
        this.orden.contacto_cliente = cliente.nombre_contacto;
        this.orden.telefono_contacto = cliente.telefono_servicio;
        this.orden.correo = cliente.correo_servicio;
        this.orden.contacto_cargo = cliente.cargo_servicio;

        console.log('Data cliente:', this.clienteData);
      });
    console.log('ID cliente seleccionado: ', idClienteSeleccionado);
  }

  // Función que se ejecuta cuando se selecciona un equipocliente en el select
  onSelectEquipocliente(): void {
    // Obtener el ID del equipocliente seleccionado
    const idEquipoSeleccionado = this.equipoSeleccionado;

    // Llamar al servicio para obtener los datos del equipocliente por su ID
    this.equipoClienteService
      .getEquipo(idEquipoSeleccionado)
      .subscribe((equipo: EquipoCliente) => {
        this.equipoData = equipo; // Almacenar los datos del equipocliente seleccionado
        // Asignar los datos a las propiedades correspondientes
       this.orden.nombre_equipo=equipo.nombre;
        this.orden.fabricante_ind = equipo.fabricante_indicador;
        this.orden.modelo_ind = equipo.modelo_indicador;
        this.orden.serie_ind = equipo.serie_indicador;
        this.orden.fabricande_rec = equipo.fabricante_receptor;
        this.orden.modelo_rec = equipo.modelo_receptor;
        this.orden.serie_rec = equipo.serie_receptor;
        this.orden.resolucion = equipo.resolucion;
        this.orden.fabricante_sen = equipo.fabricante_sensor;
        this.orden.modelo_sen = equipo.modelo_sensor;
        this.orden.serie_sen = equipo.serie_sensor;
        console.log('Data equipo: ',this.equipoData);
      });
      console.log('ID equipo seleccionado: ',idEquipoSeleccionado);
  }
  crearOrden():void {
     // Crear un objeto ClienteId con solo el idCliente
      const idClienteSeleccionado = this.formulario.get('cliente')?.value;

    if (!idClienteSeleccionado) {
      console.error('Debes seleccionar un cliente.');
      return;
    }
     const clienteId: ClienteId = { idCliente: idClienteSeleccionado };

     // Luego, puedes convertir este objeto a un objeto de tipo Cliente si es necesario
  const cliente: Cliente = {
    ...clienteId, nombre: '', apellido: '', email: '',
    createAt:  new Date(),
    telefono_empresa: '',
    cod_cliente: '',
    razon_social: '',
    nombre_comercial: '',
    ruc: '',
    dv: '',
    direccion: '',
    telefono_jefe: '',
    celular_jefe: '',
    correo_electronico: '',
    actividad_economica: '',
    abreviatura: '',
    nombre_contacto: '',
    cargo_servicio: '',
    celular_servicio: '',
    correo_servicio: '',
    telefono_servicio: '',
    nombre_cobro: '',
    cargo_cobro: '',
    telefono_cobro: '',
    celular_cobro: '',
    correo_cobro: '',
    activo: false,
    equipos: [],
    ordenTrabajos: [],
    reporteTecnicos: []
  };
  // Asignar el cliente a la Orden
  this.orden.cliente=cliente;

  const idEquipoSeleccionado = this.formulario.get('equipo')?.value;
  if (!idEquipoSeleccionado) {
    console.error('Debes seleccionar un cliente.');
    return;
  }
   // Crear un objeto ClienteId con solo el idCliente
const equipoId: EquipoId = { idEquipo: idEquipoSeleccionado };
const equipo: EquipoCliente={
  ...equipoId,
  codigoequipo: '',
  codigoequipocliente: '',
  nombre: '',
  nombrecliente:'',
  marca:'',
  modelo:'',
  numero_serie:'',
  capacidad:'',
  categoria_equipo: '',
  capacidad_maxima: '',
  capacidad_minima: '',
  resolucion: '',
  divisiones: '',
  observaciones: '',
 // imagen_equipo: File = new File(fileBits, fileName, options),
  unidad_medida: '',
  instrumento: '',
  mide: '',
  lista_precio: '',
  cmc_equipo: '',
  fabricante_receptor: '',
  modelo_receptor: '',
  serie_receptor: '',
  id_interno_receptor: '',
  fabricante_sensor: '',
  modelo_sensor: '',
  id_interno_sensor: '',
  serie_sensor: '',
  fabricante_indicador: '',
  modelo_indicador: '',
  serie_indicador: '',
  id_interno_indicador: '',
  createAt: new Date(),
  activo: false,
  cliente: new Cliente
}

  this.orden.equipo=equipo;
  // Aquí puedes realizar la validación adicional del formulario antes de proceder

  // Luego, puedes enviar una solicitud a tu servicio para crear la orden de trabajo
  // Asegúrate de que este servicio maneje adecuadamente la creación en el backend
  this.ordenTrabajoService.create(this.orden).subscribe(
      (orden) => {
        this.router.navigate(['/ordentrabajo']);
        Swal.fire(
          'Nueva Orden de Trabajo',
          `La Orden de Trabajo con numero ${orden.no_ordent} ha sido creada con éxito!`,
          'success'
        );
      },
      (err) => {
        this.errores = err.error.errors ? (err.error.errors as string[]) : [];
        console.error('Código del error desde el backend: ' + err.status);
        Swal.fire(
          'Error al Crear orden: ' + err.error.errors.mensaje + err.status,
          err.error.errors,
          'error'
        );
      }
    );
  }
  updateOrden():void {
     // Crear un objeto ClienteId con solo el idCliente
     const idClienteSeleccionado = this.formulario.get('cliente')?.value;

     if (!idClienteSeleccionado) {
       console.error('Debes seleccionar un cliente.');
       return;
     }
      const clienteId: ClienteId = { idCliente: idClienteSeleccionado };

      // Luego, puedes convertir este objeto a un objeto de tipo Cliente si es necesario
   const cliente: Cliente = {
     ...clienteId, nombre: '', apellido: '', email: '',
     createAt:  new Date(),
     telefono_empresa: '',
     cod_cliente: '',
     razon_social: '',
     nombre_comercial: '',
     ruc: '',
     dv: '',
     direccion: '',
     telefono_jefe: '',
     celular_jefe: '',
     correo_electronico: '',
     actividad_economica: '',
     abreviatura: '',
     nombre_contacto: '',
     cargo_servicio: '',
     celular_servicio: '',
     correo_servicio: '',
     telefono_servicio: '',
     nombre_cobro: '',
     cargo_cobro: '',
     telefono_cobro: '',
     celular_cobro: '',
     correo_cobro: '',
     activo: false,
     equipos: [],
     ordenTrabajos: [],
     reporteTecnicos: []
   };
   // Asignar el cliente a la Orden
   this.orden.cliente=cliente;

   const idEquipoSeleccionado = this.formulario.get('equipo')?.value;
   if (!idEquipoSeleccionado) {
     console.error('Debes seleccionar un cliente.');
     return;
   }
    // Crear un objeto ClienteId con solo el idCliente
 const equipoId: EquipoId = { idEquipo: idEquipoSeleccionado };
 const equipo: EquipoCliente={
   ...equipoId,
   codigoequipo: '',
   codigoequipocliente: '',
   nombre: '',
   nombrecliente:'',
   marca:'',
   modelo:'',
   numero_serie:'',
   capacidad:'',
   categoria_equipo: '',
   capacidad_maxima: '',
   capacidad_minima: '',
   resolucion: '',
   divisiones: '',
   observaciones: '',
  // imagen_equipo: File = new File(fileBits, fileName, options),
   unidad_medida: '',
   instrumento: '',
   mide: '',
   lista_precio: '',
   cmc_equipo: '',
   fabricante_receptor: '',
   modelo_receptor: '',
   serie_receptor: '',
   id_interno_receptor: '',
   fabricante_sensor: '',
   modelo_sensor: '',
   id_interno_sensor: '',
   serie_sensor: '',
   fabricante_indicador: '',
   modelo_indicador: '',
   serie_indicador: '',
   id_interno_indicador: '',
   createAt: new Date(),
   activo: false,
   cliente: new Cliente
 }

   this.orden.equipo=equipo;
   // Aquí puedes realizar la validación adicional del formulario antes de proceder

   // Luego, puedes enviar una solicitud a tu servicio para crear la orden de trabajo
   // Asegúrate de que este servicio maneje adecuadamente la creación en el backend
    this.ordenTrabajoService.update(this.orden).subscribe(
      (orden) => {
        this.router.navigate(['/ordentrabajo']);
        Swal.fire(
          'Orden de Trabajo Actualizada',
          `La Orden de Trabajo con numero ${this.orden.no_ordent} ha sido actualizada con éxito!`,
          'success'
        );
      },
      (err) => {
        this.errores = err.error.errors ? (err.error.errors as string[]) : [];
        console.error('Código del error desde el backend: ' + err.status);
        Swal.fire(
          `'Oops...'
          'Ocurrió un error al actualizar la orden!'
          'error'` + err.error.errors.mensaje + err.status,
          err.error.errors,
          'error'
        );
      }
    );
  }
 /* imprimirOrden():void{
    this.ordenTrabajoService.exportPdf(this.orden).subscribe(
      (orden) => {
        this.router.navigate(['/ordentrabajo']);
        Swal.fire(
          'Orden de Trabajo Impresa',
          `La Orden de Trabajo con numero ${this.orden.no_ordent} ha sido impresa con éxito!`,
          'success'
        );
      },
      (err) => {
        this.errores = err.error.errors ? (err.error.errors as string[]) : [];
        console.error('Código del error desde el backend: ' + err.status);
        Swal.fire(
          `'Oops...'
          'Ocurrió un error al imprimir la orden!'
          'error'` + err.error.errors.mensaje + err.status,
          err.error.errors,
          'error'
        );
      }
    );
  }*/
  generarPdf() {
    this.loading = true; // Activa la bandera de carga
    this.ordenTrabajoService.generarPdf(this.orden).subscribe(
      (response) => {
        if (response.errors) {
          this.erroresValidacion = response.errors;
          this.pdfBytes = null;
          this.ordenGenerada = null;
        } else {
          this.erroresValidacion = null;
          this.ordenGenerada = response.ordenTrabajo;
          this.pdfBytes = response.pdfBytes;
        }
      },
      (error) => {
        console.error('Error al generar el PDF:', error);
        this.erroresValidacion = null;
        this.ordenGenerada = null;
        this.pdfBytes = null;
      }
    );
  }

descargarPDF(idOT: number) {
  this.loading = true; // Activa la bandera de carga
    this.ordenTrabajoService.exportOrden(idOT).subscribe((data) => {
      const blob = new Blob([data], { type: 'application/pdf' });

      // Crear una URL para el Blob y abrir una nueva ventana o pestaña para descargar el PDF
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });
  }
  exportarPDF(idOT: number) {
    this.loading = true; // Activa la bandera de carga
    this.ordenTrabajoService.generateReport(idOT).subscribe(
      (response) => {
        if (response.body instanceof Blob) {
          // Convierte el Blob a una URL
          const blob = new Blob([response.body], { type: 'application/pdf' });
          const url = window.URL.createObjectURL(blob);
          window.open(url); // Abre el PDF en una nueva ventana del navegador
          Swal.fire(
            'Reporte Orden de trabajo',
            `El reporte ha sido impreso exitosamente!`,
            'success'
          );
          this.loading = false; // se desactiva la bandera de carga
        } else {
          console.error('El resultado de la solicitud no es un Blob válido');
          Swal.fire({ icon: 'error', title: 'Oops...', text: 'El resultado de la solicitud no es un Blob válido' });
        }
      },
      (error) => {
        console.error('Error al exportar el PDF', error);
        Swal.fire({ icon: 'error', title: 'Oops...', text: 'Ocurrio un error al Imprimir el reporte!' });

        // Maneja el error aquí (por ejemplo, muestra un mensaje al usuario)
      }
    );
  }








}
