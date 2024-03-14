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
import { OrdenTrabajo } from 'src/app/models/ordentrabajo';
import { EquipoCliente } from 'src/app/models/equipocliente';
import { Cliente } from 'src/app/models/cliente';
import { ReporteTecnico } from 'src/app/models/reportetecnico';
import { ReportetecnicoService } from 'src/app/_services/reportetecnico.service';
import { MediaService } from 'src/app/_services/media.service';
import { DomSanitizer } from '@angular/platform-browser';
interface ClienteId {
  idCliente: any;
}
interface EquipoId {
  idEquipo: any;
}
@Component({
  selector: 'app-reporteform',
  templateUrl: './reporteform.component.html',
  styleUrls: ['./reporteform.component.css']
})
export class ReporteformComponent implements OnInit{
  loading: boolean = false;
  reporte: ReporteTecnico = new ReporteTecnico();
  equipo: EquipoCliente = new EquipoCliente();
  cliente: Cliente = new Cliente();
  public formulario: FormGroup = new FormGroup({});
  activeTab: number = 1;
  public orden: OrdenTrabajo = new OrdenTrabajo();
  public titulo: string = 'Crear Reporte';
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
  isImagenUrl=false;
  imagenUrl?:string;
    public previsualizacion!: string;
    public previsualizacion1!: string;
    public previsualizacion2!: string;
    public previsualizacion3!: string;
  public archivos: any = [];
  archivoCapturado: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado1: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado2: File | undefined; // Declarar la propiedad archivoCapturado
  archivoCapturado3: File | undefined; // Declarar la propiedad archivoCapturado
  nombreArchivo: string | undefined; // Declarar la propiedad nombreArchivo
  nombreArchivo1: string | undefined; // Declarar la propiedad nombreArchivo
   nombreArchivo2: string | undefined; // Declarar la propiedad nombreArchivo
   nombreArchivo3: string | undefined; // Declarar la propiedad nombreArchivo
   rutasImagenes: string[] = [];
  previsualizaciones: any;

  constructor(

    private reproteTecnicoService: ReportetecnicoService,
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
            this.previsualizacion = imagen.base;
            break;
          case 1:
            this.previsualizacion1 = imagen.base;
            break;
          case 2:
            this.previsualizacion2 = imagen.base;
            break;
          case 3:
            this.previsualizacion3 = imagen.base;
            break;
          default:
            break;
        }

        const formData = new FormData();
        formData.append('file', archivoCapturado);

        const maxWidth = 800; // Cambia esto a tu ancho máximo deseado
        const maxHeight = 600; // Cambia esto a tu alto máximo deseado

        this.mediaservice.subirFile(formData,maxWidth,maxHeight).subscribe(
          (response) => {
            console.log('response', response);

            // Almacena la ruta de la imagen en el campo correspondiente de ReporteTecnico
            switch (indice) {
              case 0:
                this.reporte.rutaImagen1 = response.uri;
                console.log('fileName1: ', this.reporte.rutaImagen1);
                break;
              case 1:
                this.reporte.rutaImagen2 = response.uri;
                console.log('fileName2: ', this.reporte.rutaImagen2);
                break;
              case 2:
                this.reporte.rutaImagen3 = response.uri;
                console.log('fileName3: ', this.reporte.rutaImagen3);
                break;
              case 3:
                this.reporte.rutaImagen4 = response.uri;
                console.log('fileName4: ', this.reporte.rutaImagen4);
                break;
              default:
                break;
            }
          }
        );
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
    this.previsualizacion3= '';
    this.archivos = [];
  }

 /* extraerBase64 = async ($event: any) => {
    try {
      const unsafeImg = window.URL.createObjectURL($event);
      const image = this.sanitizer.bypassSecurityTrustUrl(unsafeImg);
      const reader = new FileReader();

      return new Promise((resolve, reject) => {
        reader.readAsDataURL($event);
        reader.onload = () => {
          resolve({
            base: reader.result
          });
        };
        reader.onerror = error => {
          resolve({
            base: null
          });
        };
      });
    } catch (e) {
      return {
        base: null
      };
    }
  }*/
  cargarReporte(): void {
    //cargando datos de equipo a la orden
    this.reporte.equipo = this.equipo;
    //cargando datos del cliente a la orden
    this.reporte.cliente = this.cliente;
    //activando parametros en el formulario

    // Ahora puedes acceder al nombre_comercial directamente desde el objeto cliente



    //informacion del equipo


    this.activateRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.reproteTecnicoService.listById(id).subscribe((reporte) => {
          this.reporte = reporte;
          this.formulario?.patchValue({
            activo: this.reporte.activo,
            cliente: this.reporte.cliente,
            equipo: this.reporte.equipo,
            nombrecliente:this.reporte.nombrecliente,
            nombreequipo: this.reporte.nombreequipo,
            no_reporte_tecnico: this.reporte.no_reporte_tecnico,
            no_cotizacion: this.reporte.no_cotizacion,
            tecnico: this.reporte.tecnico,
            horaentrada: this.reporte.horaentrada,
            horasalida: this.reporte.horasalida,
            horaviajes: this.reporte.horaviajes,
            fechareporte: this.reporte.fechareporte, // this.datePipe.transform(this.reporte.fechareporte, 'yyyy/MM/dd')
            contacto: this.reporte.contacto,
            direccion: this.reporte.direccion,
            marca: this.reporte.marca,
            no_serie: this.reporte.no_serie,
            modelo: this.reporte.modelo,
            ubicacion_equipo: this.reporte.ubicacion_equipo,
            idinterno: this.reporte.idinterno,
            capacidad: this.reporte.capacidad,
            resolucion: this.reporte.resolucion,
            calibracion: this.reporte.calibracion,
            instalacion: this.reporte.instalacion,
            verificacion: this.reporte.verificacion,
            entregaequipo: this.reporte.entregaequipo,
            gestionmetrologica: this.reporte.gestionmetrologica,
            retiroequipo: this.reporte.retiroequipo,
            inspeccion: this.reporte.inspeccion,
            otros: this.reporte.otros,
            observaciones: this.reporte.observaciones,
            desnivel: this.reporte.desnivel,
            vibraciones: this.reporte.vibraciones,
            averias: this.reporte.averias,
            erroresindicador: this.reporte.erroresindicador,
            soporteinadecuadas: this.reporte.soporteinadecuadas,
            faltacomponente: this.reporte.faltacomponente,
            suceidad: this.reporte.suceidad,
            corrienteaire: this.reporte.corrienteaire,
            insectos: this.reporte.insectos,
            golpe: this.reporte.golpe,
            fuentexternacalor: this.reporte.fuentexternacalor,
            configuracion: this.reporte.configuracion,
            observaciones2: this.reporte.observaciones2,
            nivelacion: this.reporte.nivelacion,
            limpieza: this.reporte.limpieza,
            ajusteslinealidad: this.reporte.ajusteslinealidad,
            configuracion1: this.reporte.configuracion1,
            ajusteexcentricidad: this.reporte.ajusteexcentricidad,
            reemplazo: this.reporte.reemplazo,
            observaciones3: this.reporte.observaciones3,
            completo: this.reporte.completo,
            incompleto: this.reporte.incompleto,
            observaciones4: this.reporte.observaciones4,
            nota: this.reporte.nota,
            recibidopor: this.reporte.recibidopor,
            fecha:this.reporte.fecha,//this.datePipe.transform(this.reporte.fecha, 'yyyy/MM/dd')

            descripcion1:this.reporte.descripcion_1,
            descripcion2:this.reporte.descripcion_2,
            descripcion3:this.reporte.descripcion_4,
            descripcion4:this.reporte.descripcion_4,
            rutaImagen1:this.reporte.rutaImagen1,
            rutaImagen2:this.reporte.rutaImagen2,
            rutaImagen3:this.reporte.rutaImagen3,
            rutaImagen4:this.reporte.rutaImagen4,
          });
        });
      }
    });
  }

  ngOnInit(): void {
    this.cargarReporte();
    this.formulario = this.formBuilder.group({
      activo: [''],
      cliente: ['', Validators.required],
      equipo: ['', Validators.required],
      nombrecliente:['', Validators.required],
      nombreequipo: ['', Validators.required],
      no_reporte_tecnico: [''],
      no_cotizacion: ['', Validators.required],
      tecnico: ['', Validators.required],
      horaentrada: ['', Validators.required],
      horasalida: ['', Validators.required],
      horaviajes: ['', Validators.required],
      fechareporte: ['', Validators.required],
      contacto: ['', Validators.required],
      direccion: ['', Validators.required],
      marca: ['', Validators.required],
      no_serie: ['', Validators.required],
      modelo: ['', Validators.required],
      ubicacion_equipo: ['', Validators.required],
      idinterno: ['', Validators.required],
      capacidad: ['', Validators.required],
      resolucion: ['', Validators.required],
      calibracion: [''],
      instalacion: [''],
      verificacion: [''],
      entregaequipo: [''],
      gestionmetrologica: [''],
      retiroequipo: [''],
      inspeccion: [''],
      otros: [''],
      observaciones: [''],
      desnivel: [''],
      vibraciones: [''],
      averias: [''],
      erroresindicador: [''],
      soporteinadecuadas: [''],
      faltacomponente: [''],
      suceidad: [''],
      corrienteaire: [''],
      insectos: [''],
      golpe: [''],
      fuentexternacalor: [''],
      configuracion: [''],
      observaciones2: [''],
      nivelacion: [''],
      limpieza: [''],
      ajusteslinealidad: [''],
      configuracion1: [''],
      ajusteexcentricidad: [''],
      reemplazo: [''],
      observaciones3: [''],
      completo: [''],
      incompleto: [''],
      observaciones4: [''],
      nota: [''],
      recibidopor: [''],
      fecha: [''],

      descripcion1: [''],
      descripcion2: [''],
      descripcion3: [''],
      descripcion4: [''],
      rutaImagen1: [''],
      rutaImagen2: [''],
      rutaImagen3:[''],
      rutaImagen4: [''],

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
        this.reporte.nombrecliente = cliente.nombre_comercial;
        this.reporte.direccion = cliente.direccion;
        this.reporte.contacto = cliente.nombre_contacto;


        console.log('Data cliente:', this.clienteData);

      });
    console.log('ID cliente seleccionado: ', idClienteSeleccionado);
     // Filtrar la lista de equipos para mostrar solo los equipos del cliente seleccionado
     if (idClienteSeleccionado) {
      this.equipoClienteService.getEquiposPorCliente(idClienteSeleccionado).subscribe((equipos) => {
        this.equiposFiltrados = equipos;
      });
    } else {
      this.equiposFiltrados = []; // Limpia la lista de equipos si no se ha seleccionado un cliente.
    }
     console.log('ID Equipo filtrado: ',this.equiposFiltrados)

  }

  // Función que se ejecuta cuando se selecciona un equipocliente en el select
  onSelectEquipocliente(): void {
    // Obtener el ID del equipocliente seleccionado
    const idEquipoSeleccionado = this.equipoSeleccionado;

    if (idEquipoSeleccionado) {
      this.equipoClienteService.getEquipo(idEquipoSeleccionado).subscribe((equipo) => {
        this.equipoData = equipo;
             // Asignar los datos a las propiedades correspondientes
       this.reporte.nombreequipo=equipo.nombre;
          this.reporte.marca = equipo.marca;
          this.reporte.modelo= equipo.modelo;
          this.reporte.no_serie = equipo.numero_serie;
          this.reporte.idinterno= equipo.codigoequipocliente;
          this.reporte.capacidad = equipo.capacidad;
          this.reporte.resolucion = equipo.resolucion;

        console.log('Data equipo: ', this.equipoData);
      });
    }
  }
  crearReporte(): void{
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
  this.reporte.cliente=cliente;

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
  codigoequipocliente:'',
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

  this.reporte.equipo=equipo;
  // Aquí puedes realizar la validación adicional del formulario antes de proceder

  // Luego, puedes enviar una solicitud a tu servicio para crear la orden de trabajo
  // Asegúrate de que este servicio maneje adecuadamente la creación en el backend
  this.reproteTecnicoService.create(this.reporte).subscribe(
      (reporte) => {
        this.router.navigate(['/reportetecnico']);
        Swal.fire(
          'Nuevo Reporte Creado',
          `El reporte tenico con numero ${reporte.no_reporte_tecnico} ha sido creada con éxito!`,
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
  updateReporte():void{
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
this.reporte.cliente=cliente;

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
codigoequipocliente:'',
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

this.reporte.equipo=equipo;
// Aquí puedes realizar la validación adicional del formulario antes de proceder

// Luego, puedes enviar una solicitud a tu servicio para crear la orden de trabajo
// Asegúrate de que este servicio maneje adecuadamente la creación en el backend
this.reproteTecnicoService.update(this.reporte).subscribe(
  (reporte) => {
    this.router.navigate(['/reportetecnico']);
    Swal.fire(
      'Reporte Actualizado',
      `El reporte tenico con numero ${this.reporte.no_reporte_tecnico} ha sido actualizado con éxito!`,
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
  exportarPDF(idreptec:number) {
    this.loading = true; // Activa la bandera de carga
    this.reproteTecnicoService.generateReport(idreptec).subscribe(
      (response) => {
        console.log('respuesta: ',response)
        if (response.body instanceof Blob) {
          // Convierte el Blob a una URL
          const blob = new Blob([response.body], { type: 'application/pdf' });
          const url = window.URL.createObjectURL(blob);
          window.open(url); // Abre el PDF en una nueva ventana del navegador
          Swal.fire(
            'Reporte Tecnico',
            `El reporte ha sido impreso exitosamente!`,
            'success'
          );
          this.loading = false; // se desactiva la bandera de carga
        } else {
          this.loading = false; // se desactiva la bandera de carga
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
