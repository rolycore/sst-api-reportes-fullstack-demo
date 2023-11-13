import { Component, OnInit } from '@angular/core';
import { EquipoCliente } from 'src/app/models/equipocliente';
import { EquipoclienteService } from 'src/app/_services/equipocliente.service';
import { ClienteService } from 'src/app/_services/cliente.service';
import { Router, ActivatedRoute } from '@angular/router';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';


import swal from 'sweetalert2';
import { MediaService } from 'src/app/_services/media.service';
import Swal from 'sweetalert2';
import { HttpHeaders } from '@angular/common/http';
import { Cliente } from 'src/app/models/cliente';
interface ClienteId {
  idCliente: any;
}
@Component({
  selector: 'app-equipoform',
  templateUrl: './equipoform.component.html',
  styleUrls: ['./equipoform.component.css']
})
export class EquipoformComponent implements OnInit {

  public cliente: Cliente=new Cliente();

  selectedImage: File | undefined;
//arreglo de imagenes
public imagenes: any = [];
  public formulario: FormGroup= new FormGroup({});
  activeTab: number = 1;
  public equipo: EquipoCliente = new EquipoCliente();
  public titulo: string = 'Crear Equipo';
  clientes: any[] = []; // Lista de clientes
  clienteSeleccionado: number | null = null; // ID del cliente seleccionado
  public errores: string[] = [];
  isImagenUrl=false;
  imagenUrl?:string;
    public previsualizacion!: string;
  public archivos: any = []
  public loading!: boolean;
  archivoCapturado: File | undefined; // Declarar la propiedad archivoCapturado
  nombreArchivo: string | undefined; // Declarar la propiedad nombreArchivo
  unidadmedida: string[] = ['kg','g','mg','ºC','K','ºF','N','kN','MN','m','km','cm','mm'];
  selectedUnidad: string = ''; // Variable para almacenar el rol seleccionado
  instrumento: string[] = ['Balanza','Báscula','Espectrómetro de masa','Cámara Climática','Transductor de fuerza','Máquina de ensayos','Horno','Anillo Dinamométrico','Catarómetro','Cinta métrica','Regla graduada','Calibre o Pie de Rey','Micrómetro','Interferómetro','Odómetro'];
  selectedInstrumento:string= '';
  magnitud:string[] = ['MASA','FUERZA','TEMPERATURA','LONGITUD'];
  selectedMagnitud:string='';
  categorias: string[] = [
    'Calibración',
    'Oficina',
    'Electrónica de Consumo',
    'Laboratorio',
    'Herramientas Manuales',
    'Maquinaria Pesada',
    'Salud y Cuidado Personal',
    'Cocina y Electrodomésticos',
    'Automoción',
    'Deportes y Recreación',
    'Electrodomésticos Pequeños',
    'Instrumentos Musicales',
    'Seguridad y Vigilancia',
    'Electrónica Industrial',
    'Equipos de Construcción',
    'Electrónica de Comunicación'
  ];
  selectedCategoria:string='';

  constructor(
    private sanitizer: DomSanitizer,
    private equipoClienteService: EquipoclienteService,
    private clienteService: ClienteService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private mediaService: MediaService
  ) {

  }
  capturarFile(event: any) {
    const archivoCapturado = event.target.files[0];
    this.archivoCapturado = archivoCapturado; // Asignar el archivo capturado
    this.nombreArchivo = archivoCapturado ? archivoCapturado.name : ''; // Asignar el nombre del archivo
    this.extraerBase64(archivoCapturado).then((imagen: any) => {
      this.previsualizacion = imagen.base;
      console.log(imagen);

      if (archivoCapturado) {
        // Obtener el nombre del archivo
       const nombreArchivo = archivoCapturado.name;
        this.archivos = archivoCapturado; // Almacenar la imagen seleccionada en archivos
        const formData = new FormData();
        formData.append('file', archivoCapturado);

        this.mediaService.uploadFile(formData).subscribe(
          (response) => {
            console.log('response', response);
            this.imagenUrl = response.imagenUrl;
            // this.equipo.imagen_equipo = this.imagenUrl;
          }
        );
      }
    });

    // No es necesario agregar archivoCapturado nuevamente a this.archivos aquí
  }
   clearImage(): any {
    this.previsualizacion = '';
    this.archivos = [];
  }

// Método para cargar la imagen
/*upload(event: any) {
  const file = event.target.files[0];
  if (file) {
    this.selectedImage = file; // Almacenar la imagen seleccionada en selectedImage
    const formData = new FormData();
    formData.append('file', file);

  }
}*/
extraerBase64 = async ($event: any) => {
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
}

cargarEquipo(): void {
  // Supongamos que tienes el objeto cliente completo en this.cliente y el ID del cliente en this.equipo.cliente.idCliente
// Relaciona el ID del cliente con el equipo.cliente
this.equipo.cliente = this.cliente;
//
// Ahora puedes acceder al nombre_comercial directamente desde el objeto cliente
//const nombreComercialCliente = this.equipo.cliente.nombre_comercial;

  this.activateRoute.params.subscribe((params) => {
    let id = params['id'];
    if (id) {
      this.equipoClienteService.getEquipo(id).subscribe((equipo) => {
        this.equipo=equipo;
        this.formulario?.patchValue({
          activo: this.equipo.activo,
          cliente: this.equipo.cliente,
          codigoequipocliente: this.equipo.codigoequipocliente,
          nombrecliente:this.equipo.nombrecliente,
          nombre: this.equipo.nombre,
          marca: this.equipo.marca,
          modelo: this.equipo.modelo,
          numero_serie:this.equipo.numero_serie,
          capacidad:this.equipo.capacidad,
          categoria_equipo: this.equipo.categoria_equipo,
          capacidad_maxima: this.equipo.capacidad_maxima,
          capacidad_minima: this.equipo.capacidad_minima,
          resolucion: this.equipo.resolucion,
          divisiones: this.equipo.divisiones,
          observaciones: this.equipo.observaciones,
          unidad_medida: this.equipo.unidad_medida,
          instrumento: this.equipo.instrumento,
          mide: this.equipo.mide,
          lista_precio: this.equipo.lista_precio,
          cmc_equipo: this.equipo.cmc_equipo,
          fabricante_receptor: this.equipo.fabricante_receptor,
          modelo_receptor: this.equipo.modelo_receptor,
          serie_receptor: this.equipo.serie_receptor,
          id_interno_receptor: this.equipo.id_interno_receptor,
          fabricante_sensor: this.equipo.fabricante_sensor,
          modelo_sensor: this.equipo.modelo_sensor,
          id_interno_sensor: this.equipo.id_interno_sensor,
          serie_sensor: this.equipo.serie_sensor,
          fabricante_indicador: this.equipo.fabricante_indicador,
          modelo_indicador: this.equipo.modelo_indicador,
          serie_indicador: this.equipo.serie_indicador,
          id_interno_indicador: this.equipo.id_interno_indicador,
        });
      });
}
});
}
  ngOnInit(): void {
    /*if(this.isImagenUrl){
      this.imagenUrl
      console.log(this.imagenUrl)

    }*/
    this.onSelectCategoria();
    this.onSelectUnidad();
    this.onSelectUnidad();
    this.onSelectInstrumento();
    this.cargarEquipo();
    // Luego, puedes generar el código del cliente
  //  const codigoGenerado = this.generarCodigoEquipo();

    this.formulario = this.formBuilder.group({
      activo: [''],
      codigoequipo: [''],
      codigoequipocliente: ['', Validators.required],
      nombre: ['', Validators.required],
      cliente: ['', Validators.required],
      nombrecliente: ['', Validators.required],
      marca:['', Validators.required],
      modelo:['', Validators.required],
      numero_serie:['', Validators.required],
      capacidad:['', Validators.required],
      categoria_equipo: [''],
      capacidad_maxima: ['', Validators.required],
      capacidad_minima: ['', Validators.required],
      resolucion: ['', Validators.required],
      divisiones: ['', Validators.required],
      observaciones: ['', Validators.required],
      imagen_equipo: [null],
      unidad_medida: ['', Validators.required],
      instrumento: ['', Validators.required],
      mide: ['', Validators.required],
      lista_precio: [''],
      cmc_equipo: [''],
      fabricante_receptor: ['', Validators.required],
      modelo_receptor: ['', Validators.required],
      serie_receptor: ['', Validators.required],
      id_interno_receptor: ['', Validators.required],
      fabricante_sensor: ['', Validators.required],
      modelo_sensor: ['', Validators.required],
      id_interno_sensor: ['', Validators.required],
      serie_sensor: ['', Validators.required],
      fabricante_indicador: ['', Validators.required],
      modelo_indicador: ['', Validators.required],
      serie_indicador: ['', Validators.required],
      id_interno_indicador: ['', Validators.required]


    });

    // Mover la obtención de la lista de clientes aquí
    this.clienteService.getClientes().subscribe((data: any[]) => {
      this.clientes = data;
    });
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
      this.cliente = cliente;
      this.equipo.nombrecliente = cliente.nombre_comercial;


      console.log('Data cliente:', this.cliente);
    });
  console.log('ID cliente seleccionado: ', idClienteSeleccionado);
}
onSelectUnidad():void{
  const unidadSeleccionada = this.selectedUnidad;
  this.equipo.unidad_medida=unidadSeleccionada;
  console.log('unidad: ',this.equipo.unidad_medida);

}
onSelectInstrumento():void{
  const instrumentoSeleccionado=this.selectedInstrumento;
  this.equipo.instrumento = instrumentoSeleccionado;
  console.log('instrumento: ',this.equipo.instrumento);

}

onSelectMagnitud():void{
  const magnitudSeleccionada = this.selectedMagnitud;
  this.equipo.mide=magnitudSeleccionada;
  console.log('mide: ',this.equipo.mide);
}
onSelectCategoria():void{
    // Manejar la selección de la categoría aquí
    const categoriaSeleccionada = this.selectedCategoria;
    this.equipo.categoria_equipo = categoriaSeleccionada;
    console.log('categoria: ',this.equipo.categoria_equipo);
  }
  crearEquipo(): void {
    const idClienteSeleccionado = this.formulario.get('cliente')?.value;

    if (!idClienteSeleccionado) {
      console.error('Debes seleccionar un cliente.');
      return;
    }
     // Crear un objeto ClienteId con solo el idCliente
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

    // Asignar el cliente al equipo
    this.equipo.cliente = cliente;

    this.equipoClienteService.createEquipo(this.equipo).subscribe(
      (equipo) => {
        this.router.navigate(['/equipocliente']);
        Swal.fire(
          'Nuevo equipo',
          `El Equipo ${this.equipo.nombre} ha sido creado con éxito!`,
          'success'
        );
      },
      (err) => {
        this.errores = err.error.errors ? (err.error.errors as string[]) : [];
        console.error('Código del error desde el backend: ' + err.status);
        Swal.fire('Error al Crear equipo: ' + err.error.errors.mensaje + err.status, err.error.errors, 'error');
      }
    );
  }

 // console.log('imprimiendo funcion crear equipo:',this.crearEquipo(equipo, formData));



  updateEquipo(): void {
    const idClienteSeleccionado = this.formulario.get('cliente')?.value;

    if (!idClienteSeleccionado) {
      console.error('Debes seleccionar un cliente.');
      return;
    }
     // Crear un objeto ClienteId con solo el idCliente
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

    // Asignar el cliente al equipo
    this.equipo.cliente = cliente;
    console.log('Codigo Interno no seleccionado:',this.equipo.codigoequipocliente);
    this.equipoClienteService.update(this.equipo).subscribe(

      (equipo) => {
         this.router.navigate(['/equipocliente']);
        console.log('Equipo actualizado con éxito', equipo.codigoequipocliente);
        Swal.fire(
          'Equipo Actualizado',
          `El Equipo ${this.equipo.nombre} ha sido actualizado con éxito!`,
          'success'
        );
      },
      (error) => {
        Swal.fire(
          'Oops...',
          'Ocurrió un error al actualizar el Equipo!',
          'error'
        );
        console.error('Error al actualizar el equipo', error);
      }
    );
  }
}




