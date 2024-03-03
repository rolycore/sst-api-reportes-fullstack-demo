import { Component, OnInit } from '@angular/core';
import { EquipoCliente } from '../models/equipocliente';
import { EquipoclienteService } from '../_services/equipocliente.service';
import Swal from 'sweetalert2';
import { Observable, tap } from 'rxjs';
import { ClienteService } from '../_services/cliente.service';
import { Cliente } from '../models/cliente';

@Component({
  selector: 'app-equipocliente',
  templateUrl: './equipocliente.component.html',
  styleUrls: ['./equipocliente.component.css'],
})
export class EquipoclienteComponent implements OnInit {
  loading: boolean = false;
  equipos!: EquipoCliente[];
  equipo: EquipoCliente = new EquipoCliente();

  archivoParaImportar!: File;
  public errores: string[] = [];
  clientes: any[] = []; // Lista de clientes
  public cliente: Cliente = new Cliente();
  public nombreComercialCliente: string = ''; // Declara una variable para el nombre_comercial del cliente
   // Variable para almacenar las páginas
   pages!: number;
  constructor(
    private equipoClienteService: EquipoclienteService,
    private clienteService: ClienteService
  ) {}
  //Metodo para seleccionar archivo
  seleccionarArchivo(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.archivoParaImportar = input.files[0];
    }
  }

  //imagenes del equipo
  // Función para convertir un File en un ArrayBuffer
  dataURItoArrayBuffer(file: File): Observable<ArrayBuffer> {
    return new Observable<ArrayBuffer>((observer) => {
      const reader = new FileReader();

      reader.onload = (e) => {
        const arrayBuffer = e.target?.result as ArrayBuffer;
        observer.next(arrayBuffer);
        observer.complete();
      };

      reader.onerror = (e) => {
        observer.error(e);
      };

      reader.readAsArrayBuffer(file);
    });
  }

  ngOnInit(): void {
    this.loadEquiposClientes();


   // console.log('cliente equipo:', this.equipo.cliente.toString());
  }



  //cargar Equipo de Cliente
  loadEquiposClientes(): void {
    this.equipoClienteService
      .findAllEquipos()
      .pipe(
        tap((equipos) => {
          this.equipos = equipos;
          console.log('Equipos.Component: tap 3');
          equipos.forEach((equipos) => {
            console.log(equipos.nombre);
            console.log(this.nombreComercialCliente);
          });
        })
      )
      .subscribe();
    // this.obtenerNombreCliente();
  }
  /*  obtenerNombreCliente(idCliente: number): string {
    const cliente = this.clientes.find(cliente => cliente.idCliente === equipo.cliente);
    return cliente ? cliente.nombre_comercial : 'Cliente no encontrado';
  }*/

  //Borrar Equipo Cliente
  delete(equipo: EquipoCliente): void {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger',
      },
      buttonsStyling: false,
    });

    swalWithBootstrapButtons
      .fire({
        title: 'Está seguro?',
        text: `¿Seguro que desea eliminar el Equipo?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Si, eliminar!',
        cancelButtonText: 'No, cancelar!',
        reverseButtons: true,
      })
      .then((result) => {
        if (result.isConfirmed) {
          this.equipoClienteService.delete(equipo.idEquipo).subscribe(
            (response) => {
              this.equipos = this.equipos.filter((equi) => equi !== equipo);
            },
            (error) => {
              swalWithBootstrapButtons.fire(
                'Error',
                'Ocurrió un error al eliminar el Equipo.',
                'error'
              );
            },
            () => {
              swalWithBootstrapButtons.fire(
                'Equipo Eliminado!',
                `Equipo ${equipo.nombre} eliminado con éxito.`,
                'success'
              );
            }
          );
        }
      });
  }
  //Importar Clientes
  importar(): void {
    if (this.archivoParaImportar) {
      this.loading = true; // Activa la bandera de carga
      this.equipoClienteService
        .importarEquipos(this.archivoParaImportar)
        .subscribe(
          (response) => {
            console.log('Equipos importados con éxito', response);
            Swal.fire(
              'Importacion de Equipos',
              `La Importacion de los equipos ha sido exitosa! ${response}`,
              'success'
            );
            // this.reloadPage();
          },
          (error) => {
            console.error('Error al importar equipos', error);
            this.errores = error.error.errors
              ? (error.error.errors as string[])
              : [];
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Ocurrio un error al Importar! ',
            });
          }
        );
    } else {
      console.error('No se ha seleccionado un archivo para importar.');
      Swal.fire({
        icon: 'question',
        title: 'Selecciono el archivo?',
        text: 'No se ha seleccionado un archivo para importar ',
      });
    }
  }

  //Exportar Equiposs
  exportarEquipos() {
    this.equipoClienteService.exportarEquipos().subscribe(
      (data: Blob) => {
        // Código para descargar el archivo Excel en el navegador
        const blob = new Blob([data], {
          type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'equipos.xlsx';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        Swal.fire(
          'Exportacion de Equiposs',
          `La Exportacion de los equipos ha sido exitosa!`,
          'success'
        );
      },
      (error) => {
        // Manejo de errores en la exportación
        this.errores = error.error.errors
          ? (error.error.errors as string[])
          : [];
        console.error('Error al exportar equipos', error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Ocurrio un error al Exportar!',
        });
      }
    );
  }
  reloadPage(): void {
    window.location.reload();
  }
}
