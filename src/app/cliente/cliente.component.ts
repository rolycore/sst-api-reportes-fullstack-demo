import { Component, OnInit } from '@angular/core';
import { Cliente } from '../models/cliente';
import { ClienteService } from '../_services/cliente.service';
import swal from 'sweetalert2';
import { tap } from 'rxjs/operators';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent  implements OnInit{
  loading: boolean = false;
  clientes!: Cliente[];
  nombre: string = ''; // Definir una propiedad para el nombre
  archivoParaImportar!: File;
   public errores: string[] = [];
   progress = 0;
   // Variable para almacenar las páginas
pages!: number;
  constructor(private clientesService: ClienteService) {}
//Metodo para seleccionar archivo
seleccionarArchivo(event: Event) {
  const input = event.target as HTMLInputElement;
  if (input.files && input.files.length > 0) {
    this.archivoParaImportar = input.files[0];
  }
}
  ngOnInit(): void {
    this.clientesService.getClientes().pipe(
      tap(clientes=>{
        this.clientes = clientes;
        console.log('Clientes.Component: tap 3')
        clientes.forEach(cliente=>{
          console.log(cliente.nombre);
        });
      })
    ).subscribe();
  }
  //buscar cliente por Nombre
  buscarClientes(): void {
    this.clientesService.buscarClientesPorNombre(this.nombre).subscribe((clientes) => {
      // Aquí puedes manejar la lista de clientes obtenida
      this.clientes = clientes;
    });
  }
  //Metodo delete
  delete(cliente: Cliente): void {
    const swalWithBootstrapButtons = swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    });

    swalWithBootstrapButtons.fire({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar al cliente?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.clientesService.delete(cliente.idCliente).subscribe(
          response => {
            this.clientes = this.clientes.filter(cli => cli !== cliente);
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
              'Cliente Eliminado!',
              `Cliente ${cliente.nombre} ${cliente.apellido} eliminado con éxito.`,
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

      this.clientesService.importarClientes(this.archivoParaImportar).subscribe(
        response => {
          console.log('Clientes importados con éxito', response);
          Swal.fire(
            'Importación de Clientes',
            `La importación de los clientes ha sido exitosa!`,
            'success'
          );
          this.reloadPage();
        },
        error => {
          console.error('Error al importar clientes', error);
          this.errores = error.error.errors ? (error.error.errors as string[]) : [];
          Swal.fire({ icon: 'error', title: 'Oops...', text: 'Ocurrio un error al Importar!' });
        }
      ).add(() => {
        this.loading = false; // Desactiva la bandera de carga cuando la operación se completa
      });
    } else {
      console.error('No se ha seleccionado un archivo para importar.');
      Swal.fire({ icon: 'question', title: 'Selecciono el archivo?', text: 'No se ha seleccionado un archivo para importar ' });
    }
  }


  //Exportar Clientes
  exportarClientes() {
    this.clientesService.exportarClientes().subscribe(
      (data: Blob) => {
        // Código para descargar el archivo Excel en el navegador
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'clientes.xlsx';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        Swal.fire(
          'Exportacion de Clientes',
          `La Exportacion de los clientes ha sido exitosa!`,
          'success'
        );
      },
      error => {
        // Manejo de errores en la exportación
        this.errores = error.error.errors ? (error.error.errors as string[]) : [];
        console.error('Error al exportar clientes', error);
        Swal.fire({icon: 'error',title: 'Oops...',text:'Ocurrio un error al Exportar!'});
      }
    );
  }
  reloadPage(): void {
    window.location.reload();
  }
}
