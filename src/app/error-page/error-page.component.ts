import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { trigger, transition, style, animate } from '@angular/animations';
import { ErrorHandlerService } from '../_services/error-handler.service';
@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css'],
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
export class ErrorPageComponent implements OnInit{
  content?: string;
  errorMessage!: string;

  constructor(private route: ActivatedRoute,private errorHandlerService: ErrorHandlerService) { }

  ngOnInit(): void {
    // Obtener el mensaje de error de la ruta activa
    //this.errorMessage = this.route.snapshot.queryParams['message'];
    this.errorHandlerService.showErrorPage().subscribe(
      (response: any) => {
        // Manejar la respuesta recibida del servidor
        this.errorMessage = response.message;
      },
      (error) => {
        console.error('Error al obtener la página de error:', error);
      }
    );
  }
  }


