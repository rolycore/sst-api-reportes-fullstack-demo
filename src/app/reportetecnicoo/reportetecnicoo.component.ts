import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { ReportetecnicoService } from '../_services/reportetecnico.service';
import { ReporteTecnico } from '../models/reportetecnico';
import { tap } from 'rxjs';
import { ClienteService } from 'src/app/_services/cliente.service';
import { Cliente } from 'src/app/models/cliente';
import { EquipoCliente } from 'src/app/models/equipocliente';
import { EquipoclienteService } from 'src/app/_services/equipocliente.service';
@Component({
  selector: 'app-reportetecnicoo',
  templateUrl: './reportetecnicoo.component.html',
  styleUrls: ['./reportetecnicoo.component.css']
})
export class ReportetecnicooComponent implements OnInit{
  loading: boolean = false;
  isloadingFailed = false;
  errorMessage = '';
  reportes!:ReporteTecnico[];
  reporte: ReporteTecnico = new ReporteTecnico();
  nombreCliente!: string; // Propiedad para almacenar el nombre del cliente a filtrar
  nombreEquipo!:string;
  fechaReporte = '';
  cliente: Cliente = new Cliente();
  clientes: any[] = []; // Lista de clientes
  clienteSeleccionado: number | null = null; // ID del cliente seleccionado
  clienteData: any; // Variable para almacenar los datos del cliente seleccionado
  equiposFiltrados: any[] = [];
  equipos: any[] = []; // Lista de clientes
  equipoSeleccionado: number | null = null; // ID del equipo seleccionado
  equipoData: any; // Variable para almacenar los datos del cliente seleccionado
  equipo: EquipoCliente = new EquipoCliente();
// Variable para almacenar todos los reportes
allReportes: any[] = [];

// Variable para almacenar los reportes a mostrar en la página actual
reportesEnPagina: any[] = [];

// Variables para gestionar la paginación
currentPage = 1;
itemsPerPage = 5;
// Variable para almacenar el número total de páginas
totalPages!: number;

// Variable para almacenar las páginas
pages!: number;
  constructor(private reporteService: ReportetecnicoService,
    private clienteService: ClienteService,
    private equipoClienteService: EquipoclienteService,
    ) {}

    ngOnInit(): void {
      this.nombreCliente = '';
      this.nombreEquipo= '';
      this.fechaReporte = '';
      this.getReportes();
      this.filtrarequipo();
      this.filtrarfechareporte();


       // Mover la obtención de la lista de clientes aquí
       this.clienteService.getClientes().subscribe((data: any[]) => {
        this.clientes = data.filter(cliente => cliente.nombre_comercial); // Filtra los clientes con un nombre_comercial definido
      });
          // Mover la obtención de la lista de equipos aquí
    this.equipoClienteService.findAllEquipos().subscribe((data: any[]) => {
      this.equipos = data.filter(equipo => equipo.nombre);
    });
    }

    ajustarFormatoFecha(fecha: string): string {
      // La fecha original es "aaaa/mm/dd", la ajustamos a "mm/dd/aaaa"
      const partes = fecha.split('/');
      if (partes.length === 3) {
        const aaaa = partes[0];
        const mm = partes[1];
        const dd = partes[2];
        return mm + '/' + dd + '/' + aaaa;
      }
      return fecha;
    }


    getReportes() {
      this.reporteService.listAll().pipe(
        tap((reportes) => {
          // Filtrar por nombre del cliente
          if (this.nombreCliente) {
            reportes = reportes.filter((reporte) => reporte.nombrecliente === this.nombreCliente);
          }
                    // Filtrar por Equipo del cliente
                   if (this.nombreEquipo) {
                      reportes = reportes.filter((reporte) => reporte.nombreequipo === this.nombreEquipo);
                    }
          this.reportes = reportes;
          console.log('Reporte.Component: ');
          reportes.forEach((reporte) => {
            console.log(reporte.no_reporte_tecnico);
          });
        })
      )
      .subscribe();
    }
    filtrarequipo(){
      this.reporteService.listAll().pipe(
        tap((reportes) => {

                    // Filtrar por Equipo del cliente
                   if (this.nombreEquipo) {
                      reportes = reportes.filter((reporte) => reporte.nombreequipo === this.nombreEquipo);
                    }
          this.reportes = reportes;
          console.log('Reporte.Component: ');
          reportes.forEach((reporte) => {
            console.log(reporte.no_reporte_tecnico);
          });
        })
      )
      .subscribe();

    }

    filtrarfechareporte() {
      // Ajusta el formato de fecha antes de llamar a getReportes()
      if (this.fechaReporte) {
        this.fechaReporte = this.ajustarFormatoFecha(this.fechaReporte);
      }

      this.reporteService.listAll().pipe(
        tap((reportes) => {
          // Filtrar por fecha de reporte
          if (this.fechaReporte) {
            const selectedDate = new Date(this.fechaReporte);
            reportes = reportes.filter((reporte) => {
              const reporteDate = new Date(reporte.fechareporte);

              return (
                reporteDate.getFullYear() === selectedDate.getFullYear() &&
                reporteDate.getMonth() === selectedDate.getMonth() &&
                reporteDate.getDate() === selectedDate.getDate()
              );
            });
          }
          this.reportes = reportes;
          console.log('Reporte.Component: ');
          reportes.forEach((reporte) => {
            console.log(reporte.no_reporte_tecnico);
          });
        })
      )
      .subscribe();
    }

    limpiarFiltro() {
      this.nombreCliente = ''; // Restablecer el filtro
      this.nombreEquipo= '';
      this.getReportes(); // Actualizar la lista de reportes sin el filtro
      this.filtrarequipo();
      this.filtrarfechareporte();
    }

delete(reporte: ReporteTecnico):void{

  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger'
    },
    buttonsStyling: false
  });

  swalWithBootstrapButtons.fire({
    title: 'Está seguro?',
    text: `¿Seguro que desea eliminar el reporte?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Si, eliminar!',
    cancelButtonText: 'No, cancelar!',
    reverseButtons: true
  }).then((result) => {
    if (result.isConfirmed) {
      this.reporteService.deleteById(reporte.idreptec).subscribe(
        response => {
          this.reportes= this.reportes.filter(rep => rep !== reporte);
        },
        error => {
          swalWithBootstrapButtons.fire(
            'Error',
            'Ocurrió un error al eliminar el cliente.',
            'error'
          );
        },
        () => {
          swalWithBootstrapButtons.fire(
            'Reporte Eliminado!',
            `El Reporte ${reporte.no_reporte_tecnico} eliminado con éxito.`,
            'success'
          );
        }
      );
    }
  });

}
exportarPDF(idreptec:number) {
  this.loading = true; // Activa la bandera de carga
  this.errorMessage = '';
  this.isloadingFailed = false;
  this.reporteService.generateReport(idreptec).subscribe(
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
      this.errorMessage = 'Ocurrio un fallo! en la impresión favor verificar reporte, y vuelva a intentarlo';
      this.isloadingFailed = true;
      this.loading = false; // se desactiva la bandera de carga
      console.error('Error al exportar el PDF', error);
      Swal.fire({ icon: 'error', title: 'Oops...', text: 'Ocurrio un error al Imprimir el reporte!' });

      // Maneja el error aquí (por ejemplo, muestra un mensaje al usuario)
    }
  );
}

}
