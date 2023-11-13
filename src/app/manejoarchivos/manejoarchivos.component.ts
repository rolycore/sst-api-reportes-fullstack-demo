import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FileHandlerService } from '../_services/file-handler.service';
import * as saveAs from 'file-saver';
import { MediaService } from '../_services/media.service';
import { DomSanitizer } from '@angular/platform-browser';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-manejoarchivos',
  templateUrl: './manejoarchivos.component.html',
  styleUrls: ['./manejoarchivos.component.css']
})
export class ManejoarchivosComponent implements OnInit{
  public imageUrl!:string;
  public titulo: string='Archivos';
   public previsualizacion!: string;
  public archivos: any = []
  public loading!: boolean
// Especifica el tipo de datos para selectedFiles como un array de File
public selectedFiles: File[] = [];
  public filenames: string[] = [];
  public filenameToDownload: any;
  public files: File[] = [];
  public finish?: false;
  public fileStatus = {
    status: "",
    requestType: "",
    percent: 0};
  constructor(private fileHandlerService: FileHandlerService,
  private mediaservice: MediaService,
  private sanitizer: DomSanitizer) {

  }

  ngOnInit(): void {

  }
  onFileSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement && inputElement.files && inputElement.files.length > 0) {
      // Limpiamos la lista de archivos seleccionados y la llenamos con los nuevos archivos
      this.selectedFiles = [];
      for (let i = 0; i < inputElement.files.length; i++) {
        this.selectedFiles.push(inputElement.files[i]);
      }
    }
  }

  public onUploadFiles(files: File[]): void {

    const formData: FormData = new FormData();
    files.forEach(f => formData.append("files", f, f.name));
    this.fileHandlerService.upload(formData).subscribe(
      (response) => {
        console.log(response);
        this.reportProgress(response);
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
        alert(error.message);
      }
    );
  }

  private reportProgress(httpEvent: HttpEvent<string[] | Blob>): void {
    switch (httpEvent.type) {
      case HttpEventType.UploadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, "Uploading...");
        break;
      case HttpEventType.DownloadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, "Downloading...");
        break;
      case HttpEventType.ResponseHeader:
        console.log("header returned...", httpEvent);
        break;
      case HttpEventType.Response:
        if (httpEvent.body instanceof Array) {
          httpEvent.body.forEach(filename => this.filenames.unshift(filename));
        }
        else {
          saveAs(new File([httpEvent.body!], httpEvent.headers.get('File-Name')!, { type: httpEvent.headers.get('Content-Type') + ";charset=utf-8"} ));
        }
        break;
        default:
          console.log(httpEvent);
    }
  }

  private updateStatus(loaded: number, total: number, requestType: string) {
    this.finish=false;
    this.fileStatus.status = "progress";
    this.fileStatus.requestType = requestType;
    this.fileStatus.percent = Math.round(100 * loaded / total);
    if(this.fileStatus.percent=100){
      this.fileStatus.status= "";
      this.fileStatus.requestType= "";
      this.fileStatus.percent= 0;
    }

  }

  public onDownloadFiles(filename: string): void {

    this.fileHandlerService.download(filename).subscribe(
      (response) => {
        console.log(response);
        this.reportProgress(response);
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
        alert(error.message);
      }
    );
  }
  capturarFile(event: any){
    const archivoCapturado = event.target.files[0]
    this.extraerBase64(archivoCapturado).then((imagen: any) => {
      this.previsualizacion = imagen.base;
      console.log(imagen);

    })
    this.archivos.push(archivoCapturado)
    //
    // console.log(event.target.files);

  }

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


  /**
   * Limpiar imagen
   */

  clearImage(): any {
    this.previsualizacion = '';
    this.archivos = [];
  }
  subirArchivo(): any {
    try {
      this.loading = true;
      const formularioDeDatos = new FormData();
      this.archivos.forEach(() => {
        formularioDeDatos.append('file', this.archivos)
      })
      // formularioDeDatos.append('_id', 'MY_ID_123')
      this.mediaservice.uploadFile( formularioDeDatos)
        .subscribe(res => {
          this.loading = false;
          console.log('Respuesta del servidor', res);

        }, () => {
          this.loading = false;
          alert('Error');
        })
    } catch (e) {
      this.loading = false;
      console.log('ERROR', e);

    }
  }
  downloadImage(imageUrl: string): void {
    if (imageUrl) {
      this.loading = true; // Activa la bandera de carga

      // Realiza una solicitud HTTP para obtener la imagen desde la URL
      this.mediaservice.getFile(imageUrl).subscribe(data => {
        if (data instanceof Blob) {
          const blob = new Blob([data], { type: 'image/jpeg' });
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = 'imagen_descargada.jpg'; // Nombre del archivo
          document.body.appendChild(a);
          a.click();
          window.URL.revokeObjectURL(url);
          document.body.removeChild(a);

          Swal.fire(
            'Imagen descargada',
            `La Imagen ha sido descargada exitosamente!`,
            'success'
          );

          this.loading = false; // Desactiva la bandera de carga
        } else {
          this.loading = false; // Desactiva la bandera de carga
          console.error('La respuesta no es un Blob válido');
          Swal.fire({ icon: 'error', title: 'Oops...', text: 'La respuesta no es un Blob válido' });
        }
      });
    } else {
      // Muestra un mensaje de error si no se ha ingresado una URL
      Swal.fire({ icon: 'error', title: 'Oops...', text: 'Por favor, ingrese una URL de imagen antes de descargarla' });
    }
  }


}
