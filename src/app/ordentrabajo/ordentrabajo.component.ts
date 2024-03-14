import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { EquipoCliente } from '../models/equipocliente';
import { EquipoclienteService } from '../_services/equipocliente.service';
import { OrdenTrabajo } from '../models/ordentrabajo';
import { OrdentrabajoService } from '../_services/ordentrabajo.service';
import { ClienteService } from '../_services/cliente.service';
import { Cliente } from '../models/cliente';
import { tap } from 'rxjs';
import { trigger, transition, style, animate } from '@angular/animations'; // Importa las animaciones

@Component({
  selector: 'app-ordentrabajo',
  templateUrl: './ordentrabajo.component.html',
  styleUrls: ['./ordentrabajo.component.css'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('500ms ease-out', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('500ms ease-out', style({ opacity: 0 })),
      ])
    ])
  ]
})
export class OrdentrabajoComponent implements OnInit {
  loading: boolean = false;
  ordenes!: OrdenTrabajo[];
orden: OrdenTrabajo=new OrdenTrabajo();

  constructor(private ordenesService: OrdentrabajoService,
    private clienteService: ClienteService,
    ) {

    }

    ngOnInit(): void {
      this.getOrdenes();
    }

    //Metodo para lista las ordenes
    getOrdenes(){

      this.ordenesService.listarOrdenes().pipe(
        tap((ordenes) => {
          this.ordenes = ordenes;

          console.log('Orden.Component: ');
          ordenes.forEach((ordenes) => {
            console.log(ordenes.no_ordent);

          });
        })
      )
      .subscribe();
    // this.obtenerNombreCliente();

    }
    delete(orden:OrdenTrabajo):void{

    }
/*delete(ordenes: OrdenTrabajo): void{
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger'
    },
    buttonsStyling: false
  });

  swalWithBootstrapButtons.fire({
    title: 'Está seguro?',
    text: `¿Seguro que desea eliminar la Orden?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Si, eliminar!',
    cancelButtonText: 'No, cancelar!',
    reverseButtons: true
  }).then((result) => {
    if (result.isConfirmed) {
      this.ordenesService.delete(ordenes.idOt).subscribe(
        response => {
          this.ordenes = this.ordenes.filter(ot => ot !== ordenes);
        },
        error => {
          swalWithBootstrapButtons.fire(
            'Error',
            'Ocurrió un error al eliminar el Equipo.',
            'error'
          );
        },
        () => {
          swalWithBootstrapButtons.fire(
            'Equipo Eliminado!',
            `Equipo${equipo.nombre} ${equipo.cliente} eliminado con éxito.`,
            'success'
          );
        }
      );
    }
  });
}*/


exportarPDF(idOT: number) {
  this.loading = true; // Activa la bandera de carga
  this.ordenesService.generateReport(idOT).subscribe(
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
