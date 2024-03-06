import { Component, OnInit } from '@angular/core';
import { EquipoclienteService } from 'src/app/_services/equipocliente.service';
import { ClienteService } from 'src/app/_services/cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import Swal from 'sweetalert2';
import { MediaService } from 'src/app/_services/media.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ReportemantenimientoService } from 'src/app/_services/reportemantenimiento.service';
import { ReporteMantenimiento } from 'src/app/models/reportemantenimiento';
import { EquipoCliente } from 'src/app/models/equipocliente';
import { Cliente } from 'src/app/models/cliente';

interface ClienteId {
  idCliente: any;
}
interface EquipoId {
  idEquipo: any;
}
@Component({
  selector: 'app-reportemantform',
  templateUrl: './reportemantform.component.html',
  styleUrls: ['./reportemantform.component.css'],
})
export class ReportemantformComponent implements OnInit {
  loading: boolean = false;
  reportem: ReporteMantenimiento = new ReporteMantenimiento();
  equipo: EquipoCliente = new EquipoCliente();
  cliente: Cliente = new Cliente();
  public formulario: FormGroup = new FormGroup({});
  activeTab: number = 1;

  public titulo: string = 'Crear Reporte Mantenimiento';
  public titulocliente: string = 'Datos del Cliente';
  public tituloequipo: string = 'Datos del Equipo';
  clientes: any[] = []; // Lista de clientes
  clienteSeleccionado: number | null = null; // ID del cliente seleccionado
  clienteData: any; // Variable para almacenar los datos del cliente seleccionado
  equipos: any[] = []; // Lista de clientes
  equipoSeleccionado: number | null = null; // ID del equipo seleccionado
  equipoData: any; // Variable para almacenar los datos del cliente seleccionado
  public errores: string[] = [];
  equiposFiltrados: any[] = [];
  isImagenUrl = false;
  imagenUrl?: string;
  public previsualizacion!: string;
  public previsualizacion1!: string;
  public previsualizacion2!: string;
  public previsualizacion3!: string;
  public previsualizacion4!: string;
  public previsualizacion5!: string;
  public previsualizacion6!: string;
  public previsualizacion7!: string;
  public previsualizacion8!: string;
  public previsualizacion9!: string;
  public previsualizacion10!: string;
  public previsualizacion11!: string;
  public archivos: any = [];
  archivoCapturado: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado1: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado2: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado3: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado4: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado5: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado6: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado7: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado8: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado9: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado10: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado11: File | undefined; // Declarar la propiedad archivoCapturado
  nombreArchivo: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo1: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo2: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo3: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo4: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo5: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo6: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo7: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo8: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo9: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo10: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo11: string | undefined; // Declarar la propiedad nombreArchivo
  rutasImagenes: string[] = [];
  previsualizaciones: any;

  constructor(
    private reproteMantenimientoService: ReportemantenimientoService,
    private mediaservice: MediaService,
    private sanitizer: DomSanitizer,
    private equipoClienteService: EquipoclienteService,
    private clienteService: ClienteService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}
  capturarFile(event: any, indice: number) {
    const archivoCapturado = event.target.files[0];

    if (archivoCapturado) {
      this.extraerBase64(archivoCapturado).then((imagen: any) => {
        // Asignar la imagen previsualizada a la variable correspondiente según el índice
        switch (indice) {
          case 0:
            this.reportem.imagen_1 = archivoCapturado.name ,
            this.previsualizacion = imagen.base;
            break;
          case 1:
            this.reportem.imagen_2 = archivoCapturado.name,
            this.previsualizacion1 = imagen.base;
            break;
          case 2:
            this.reportem.imagen_3  = archivoCapturado.name,
            this.previsualizacion2 = imagen.base;
            break;
          case 3:
            this.reportem.imagen_4 = archivoCapturado.name,
            this.previsualizacion3 = imagen.base;
            break;
          case 4:
            this.reportem.imagen_5 =  archivoCapturado.name,
            this.previsualizacion4 = imagen.base;
            break;
          case 5:
            this.reportem.imagen_6 = archivoCapturado.name,
            this.previsualizacion5 = imagen.base;
            break;
          case 6:
            this.reportem.imagen_7 = archivoCapturado.name,
            this.previsualizacion6 = imagen.base;
            break;
          case 7:
            this.reportem.imagen_8  = archivoCapturado.name,
            this.previsualizacion7 = imagen.base;
            break;
          case 8:
            this.reportem.imagen_9 = archivoCapturado.name,
            this.previsualizacion8 = imagen.base;
            break;
          case 9:
            this.reportem.imagen_10  = archivoCapturado.name,
            this.previsualizacion9 = imagen.base;
            break;
          case 10:
            this.reportem.imagen_11 = archivoCapturado.name,
            this.previsualizacion10 = imagen.base;
            break;
          case 11:
            this.reportem.imagen_12 = archivoCapturado.name,
            this.previsualizacion11 = imagen.base;
            break;
          default:
            break;
        }

        const formData = new FormData();
        formData.append('file', archivoCapturado);

        const maxWidth = 800; // Cambia esto a tu ancho máximo deseado
        const maxHeight = 600; // Cambia esto a tu alto máximo deseado

        this.mediaservice
          .subirFile(formData, maxWidth, maxHeight)
          .subscribe((response) => {
            console.log('response', response);

            // Almacena la ruta de la imagen en el campo correspondiente de ReporteTecnico
            switch (indice) {
              case 0:
                this.reportem.rutaImagen1 = response.uri;
                console.log('fileName1: ', this.reportem.rutaImagen1);
                break;
              case 1:
                this.reportem.rutaImagen2 = response.uri;
                console.log('fileName2: ', this.reportem.rutaImagen2);
                break;
              case 2:
                this.reportem.rutaImagen3 = response.uri;
                console.log('fileName3: ', this.reportem.rutaImagen3);
                break;
              case 3:
                this.reportem.rutaImagen4 = response.uri;
                console.log('fileName4: ', this.reportem.rutaImagen4);
                break;
              case 4:
                this.reportem.rutaImagen5 = response.uri;
                console.log('fileName5: ', this.reportem.rutaImagen5);
                break;
              case 5:
                this.reportem.rutaImagen6 = response.uri;
                console.log('fileName6: ', this.reportem.rutaImagen6);
                break;
              case 6:
                this.reportem.rutaImagen7 = response.uri;
                console.log('fileName7: ', this.reportem.rutaImagen7);
                break;
              case 7:
                this.reportem.rutaImagen8 = response.uri;
                console.log('fileName8: ', this.reportem.rutaImagen8);
                break;
              case 8:
                this.reportem.rutaImagen9 = response.uri;
                console.log('fileName9: ', this.reportem.rutaImagen9);
                break;
              case 9:
                this.reportem.rutaImagen10 = response.uri;
                console.log('fileName10: ', this.reportem.rutaImagen10);
                break;
              case 10:
                this.reportem.rutaImagen11 = response.uri;
                console.log('fileName11: ', this.reportem.rutaImagen11);
                break;
              case 11:
                this.reportem.rutaImagen12 = response.uri;
                console.log('fileName12: ', this.reportem.rutaImagen12);
                break;
              default:
                break;
            }
          });
      });
    }
  }

  extraerBase64(archivo: File): Promise<any> {
    return new Promise((resolve) => {
      const reader = new FileReader();
      reader.onload = (event: any) => {
        resolve({ base: event.target.result });
      };
      reader.readAsDataURL(archivo);
    });
  }

  clearImage(): any {
    this.previsualizacion = '';
    this.archivos = [];
  }
  clearImage1(): any {
    this.previsualizacion1 = '';
    this.archivos = [];
  }
  clearImage2(): any {
    this.previsualizacion2 = '';
    this.archivos = [];
  }
  clearImage3(): any {
    this.previsualizacion3 = '';
    this.archivos = [];
  }
  clearImage4(): any {
    this.previsualizacion4 = '';
    this.archivos = [];
  }
  clearImage5(): any {
    this.previsualizacion5 = '';
    this.archivos = [];
  }
  clearImage6(): any {
    this.previsualizacion6 = '';
    this.archivos = [];
  }
  clearImage7(): any {
    this.previsualizacion7 = '';
    this.archivos = [];
  }
  clearImage8(): any {
    this.previsualizacion8 = '';
    this.archivos = [];
  }
  clearImage9(): any {
    this.previsualizacion9 = '';
    this.archivos = [];
  }
  clearImage10(): any {
    this.previsualizacion10 = '';
    this.archivos = [];
  }
  clearImage11(): any {
    this.previsualizacion11 = '';
    this.archivos = [];
  }

  cargarReporte(): void {
    //cargando datos de equipo a la orden
    this.reportem.equipo = this.equipo;
    //cargando datos del cliente a la orden
    this.reportem.cliente = this.cliente;
    this.activateRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.reproteMantenimientoService.listById(id).subscribe((reportem) => {
          this.reportem = reportem;
          this.formulario?.patchValue({
            cliente: this.reportem.cliente,
            equipo: this.reportem.equipo,
            nombrecliente: this.reportem.nombrecliente,
            nombreequipo: this.reportem.nombreequipo,
            no_reporte: this.reportem.no_reporte,
            no_cotizacion: this.reportem.no_cotizacion,
            tecnico: this.reportem.tecnico,
            horaentrada: this.reportem.horaentrada,
            horasalida: this.reportem.horasalida,
            horaviajes: this.reportem.horaviajes,
            fechareporte: this.reportem.fechareporte, // this.datePipe.transform(this.reporte.fechareporte, 'yyyy/MM/dd')
            contacto: this.reportem.contacto,
            cargo: this.reportem.cargo,
            direccion: this.reportem.direccion,
            ubicacionequipo: this.reportem.ubicacionequipo,
            fabricanteindicador: this.reportem.fabricanteindicador,
            fabricantemarco: this.reportem.fabricantemarco,
            fabricantetransductor: this.reportem.fabricantetransductor,
            modeloindicador: this.reportem.modeloindicador,
            modelomarco: this.reportem.modelomarco,
            modelotransductor: this.reportem.modelotransductor,
            serieindicador: this.reportem.serieindicador,
            seriemarco: this.reportem.seriemarco,
            serietransductor: this.reportem.serietransductor,
            capacidadindicador: this.reportem.capacidadindicador,
            capacidadmarco: this.reportem.capacidadmarco,
            capacidadtransductor: this.reportem.capacidadtransductor,
            notamantprevent: this.reportem.notamantprevent,
            notahallazgo: this.reportem.notahallazgo,
            recomendaciones: this.reportem.recomendaciones,
            imagen_1: this.reportem.imagen_1,
            imagen_2: this.reportem.imagen_2,
            imagen_3: this.reportem.imagen_3,
            imagen_4: this.reportem.imagen_4,
            imagen_5: this.reportem.imagen_5,
            imagen_6: this.reportem.imagen_6,
            imagen_7: this.reportem.imagen_7,
            imagen_8: this.reportem.imagen_8,
            imagen_9: this.reportem.imagen_9,
            imagen_10: this.reportem.imagen_10,
            imagen_11: this.reportem.imagen_11,
            imagen_12: this.reportem.imagen_12,
            descripcion1: this.reportem.descripcion1,
            descripcion2: this.reportem.descripcion2,
            descripcion3: this.reportem.descripcion3,
            descripcion4: this.reportem.descripcion4,
            descripcion5: this.reportem.descripcion5,
            descripcion6: this.reportem.descripcion6,
            descripcion7: this.reportem.descripcion7,
            descripcion8: this.reportem.descripcion8,
            descripcion9: this.reportem.descripcion9,
            descripcion10: this.reportem.descripcion10,
            descripcion11: this.reportem.descripcion11,
            descripcion12: this.reportem.descripcion12,
            rutaImagen1: this.reportem.rutaImagen1,
            rutaImagen2: this.reportem.rutaImagen2,
            rutaImagen3: this.reportem.rutaImagen3,
            rutaImagen4: this.reportem.rutaImagen4,
            rutaImagen5: this.reportem.rutaImagen5,
            rutaImagen6: this.reportem.rutaImagen6,
            rutaImagen7: this.reportem.rutaImagen7,
            rutaImagen8: this.reportem.rutaImagen8,
            rutaImagen9: this.reportem.rutaImagen9,
            rutaImagen10: this.reportem.rutaImagen10,
            rutaImagen11: this.reportem.rutaImagen11,
            rutaImagen12: this.reportem.rutaImagen12,
          });
        });
      }
    });
  }
  ngOnInit(): void {
    this.cargarReporte();
    this.formulario = this.formBuilder.group({
      no_reporte: [''],
      cliente: ['', Validators.required],
      equipo: ['', Validators.required],
      nombrecliente: [''],
      nombreequipo: [''],
      tecnico: [''],
      horaentrada: [''], // Puedes cambiar esto según tus necesidades de manejo de tiempo en TypeScript
      horasalida: [''], // Puedes cambiar esto según tus necesidades de manejo de tiempo en TypeScript
      horaviajes: [''],
      fechareporte: [''],
      fecha: [''],
      contacto: [''],
      cargo: [''],
      direccion: [''],
      no_cotizacion: [''],
      ubicacionequipo: [''],
      fabricanteindicador: [''],
      fabricantemarco: [''],
      fabricantetransductor: [''],
      modeloindicador: [''],
      modelomarco: [''],
      modelotransductor: [''],
      serieindicador: [''],
      seriemarco: [''],
      serietransductor: [''],
      capacidadindicador: [''],
      capacidadmarco: [''],
      capacidadtransductor: [''],
      notamantprevent: [''],
      notahallazgo: [''],
      recomendaciones: [''],
      imagen_1: [''],
      imagen_2: [''],
      imagen_3: [''],
      imagen_4: [''],
      imagen_5: [''],
      imagen_6: [''],
      imagen_7: [''],
      imagen_8: [''],
      imagen_9: [''],
      imagen_10: [''],
      imagen_11: [''],
      imagen_12: [''],
      descripcion1: [''],
      descripcion2: [''],
      descripcion3: [''],
      descripcion4: [''],
      descripcion5: [''],
      descripcion6: [''],
      descripcion7: [''],
      descripcion8: [''],
      descripcion9: [''],
      descripcion10: [''],
      descripcion11: [''],
      descripcion12: [''],
      rutaImagen1: [''],
      rutaImagen2: [''],
      rutaImagen3: [''],
      rutaImagen4: [''],
      rutaImagen5: [''],
      rutaImagen6: [''],
      rutaImagen7: [''],
      rutaImagen8: [''],
      rutaImagen9: [''],
      rutaImagen10: [''],
      rutaImagen11: [''],
      rutaImagen12: [''],
    });
    // Mover la obtención de la lista de clientes aquí
    this.clienteService.getClientes().subscribe((data: any[]) => {
      this.clientes = data;
    });
    // Mover la obtención de la lista de equipos aquí
    this.equipoClienteService.findAllEquipos().subscribe((data: any[]) => {
      this.equipos = data;
    });
    this.cargarReporte();
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
        this.reportem.nombrecliente = cliente.nombre_comercial;
        this.reportem.direccion = cliente.direccion;
        this.reportem.contacto = cliente.nombre_contacto;
        this.reportem.cargo = cliente.cargo_servicio;

        console.log('Data cliente:', this.clienteData);
      });
    console.log('ID cliente seleccionado: ', idClienteSeleccionado);
    // Filtrar la lista de equipos para mostrar solo los equipos del cliente seleccionado
    if (idClienteSeleccionado) {
      this.equipoClienteService
        .getEquiposPorCliente(idClienteSeleccionado)
        .subscribe((equipos) => {
          this.equiposFiltrados = equipos;
        });
    } else {
      this.equiposFiltrados = []; // Limpia la lista de equipos si no se ha seleccionado un cliente.
    }
    console.log('ID Equipo filtrado: ', this.equiposFiltrados);
  }

  // Función que se ejecuta cuando se selecciona un equipocliente en el select
  onSelectEquipocliente(): void {
    // Obtener el ID del equipocliente seleccionado
    const idEquipoSeleccionado = this.equipoSeleccionado;

    if (idEquipoSeleccionado) {
      this.equipoClienteService
        .getEquipo(idEquipoSeleccionado)
        .subscribe((equipo) => {
          this.equipoData = equipo;
          // Asignar los datos a las propiedades correspondientes
          this.reportem.nombreequipo = equipo.nombre;
          this.reportem.fabricanteindicador = equipo.fabricante_indicador;
          this.reportem.serieindicador = equipo.serie_indicador;
          this.reportem.fabricantemarco = equipo.fabricante_receptor;
          this.reportem.fabricantetransductor = equipo.fabricante_sensor;
          this.reportem.modeloindicador = equipo.modelo_indicador;
          this.reportem.modelomarco = equipo.modelo_receptor;
          this.reportem.modelotransductor = equipo.modelo_sensor;
          this.reportem.seriemarco = equipo.serie_receptor;
          this.reportem.serietransductor = equipo.serie_sensor;


          console.log('Data equipo: ', this.equipoData);
        });
    }
  }
  crearReporte(): void {
    // Crear un objeto ClienteId con solo el idCliente
    const idClienteSeleccionado = this.formulario.get('cliente')?.value;

    if (!idClienteSeleccionado) {
      console.error('Debes seleccionar un cliente.');
      return;
    }
    const clienteId: ClienteId = { idCliente: idClienteSeleccionado };

    // Luego, puedes convertir este objeto a un objeto de tipo Cliente si es necesario
    const cliente: Cliente = {
      ...clienteId,
      nombre: '',
      apellido: '',
      email: '',
      createAt: new Date(),
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
      reporteTecnicos: [],
    };
    // Asignar el cliente a la Orden
    this.reportem.cliente = cliente;

    const idEquipoSeleccionado = this.formulario.get('equipo')?.value;
    if (!idEquipoSeleccionado) {
      console.error('Debes seleccionar un cliente.');
      return;
    }
    // Crear un objeto ClienteId con solo el idCliente
    const equipoId: EquipoId = { idEquipo: idEquipoSeleccionado };
    const equipo: EquipoCliente = {
      ...equipoId,
      codigoequipo: '',
      codigoequipocliente: '',
      nombre: '',
      nombrecliente: '',
      marca: '',
      modelo: '',
      numero_serie: '',
      capacidad: '',
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
      cliente: new Cliente(),
    };

    this.reportem.equipo = equipo;
    // Aquí puedes realizar la validación adicional del formulario antes de proceder

    // Luego, puedes enviar una solicitud a tu servicio para crear la orden de trabajo
    // Asegúrate de que este servicio maneje adecuadamente la creación en el backend
    this.reproteMantenimientoService.create(this.reportem).subscribe(
      (reporte) => {
        this.router.navigate(['/mantenimiento']);
        Swal.fire(
          'Nuevo Reporte Creado',
          `El reporte mantenimiento con numero ${reporte.no_reporte} ha sido creada con éxito!`,
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

  updateReporte(): void {
    // Crear un objeto ClienteId con solo el idCliente
    const idClienteSeleccionado = this.formulario.get('cliente')?.value;

    if (!idClienteSeleccionado) {
      console.error('Debes seleccionar un cliente.');
      return;
    }
    const clienteId: ClienteId = { idCliente: idClienteSeleccionado };

    // Luego, puedes convertir este objeto a un objeto de tipo Cliente si es necesario
    const cliente: Cliente = {
      ...clienteId,
      nombre: '',
      apellido: '',
      email: '',
      createAt: new Date(),
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
      reporteTecnicos: [],
    };
    // Asignar el cliente a la Orden
    this.reportem.cliente = cliente;

    const idEquipoSeleccionado = this.formulario.get('equipo')?.value;
    if (!idEquipoSeleccionado) {
      console.error('Debes seleccionar un cliente.');
      return;
    }
    // Crear un objeto ClienteId con solo el idCliente
    const equipoId: EquipoId = { idEquipo: idEquipoSeleccionado };
    const equipo: EquipoCliente = {
      ...equipoId,
      codigoequipo: '',
      codigoequipocliente: '',
      nombre: '',
      nombrecliente: '',
      marca: '',
      modelo: '',
      numero_serie: '',
      capacidad: '',
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
      cliente: new Cliente(),
    };

    this.reportem.equipo = equipo;
    // Aquí puedes realizar la validación adicional del formulario antes de proceder

    // Luego, puedes enviar una solicitud a tu servicio para crear la orden de trabajo
    // Asegúrate de que este servicio maneje adecuadamente la creación en el backend
    this.reproteMantenimientoService.update(this.reportem).subscribe(
      (reporte) => {
        this.router.navigate(['/mantenimiento']);
        Swal.fire(
          'Reporte Actualizado',
          `El reporte mantenimiento con numero ${this.reportem.no_reporte} ha sido actualizado con éxito!`,
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

  exportarPDF(idrepmant: number) {
    this.loading = true; // Activa la bandera de carga
    this.reproteMantenimientoService.generateReport(idrepmant).subscribe(
      (response) => {
        console.log('respuesta: ', response);
        if (response.body instanceof Blob) {
          // Convierte el Blob a una URL
          const blob = new Blob([response.body], { type: 'application/pdf' });
          const url = window.URL.createObjectURL(blob);
          window.open(url); // Abre el PDF en una nueva ventana del navegador
          Swal.fire(
            'Reporte Mantenimiento',
            `El reporte ha sido impreso exitosamente!`,
            'success'
          );
          this.loading = false; // se desactiva la bandera de carga
        } else {
          this.loading = false; // se desactiva la bandera de carga
          console.error('El resultado de la solicitud no es un Blob válido');
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'El resultado de la solicitud no es un Blob válido',
          });
        }
      },
      (error) => {
        console.error('Error al exportar el PDF', error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Ocurrio un error al Imprimir el reporte!',
        });

        // Maneja el error aquí (por ejemplo, muestra un mensaje al usuario)
      }
    );
  }
}
