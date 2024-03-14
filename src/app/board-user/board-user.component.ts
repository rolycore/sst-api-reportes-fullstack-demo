import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css'],
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
export class BoardUserComponent implements OnInit {
  content?: string;
  username: string = '';

  constructor(
    private userService: UserService,
    private tokenStorageService: TokenStorageService
  ) { }

  ngOnInit(): void {
    this.userService.getUserBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );

    // Obtener el nombre del usuario del TokenStorageService
    const user = this.tokenStorageService.getUser();
    this.username = user.username;
  }
}
