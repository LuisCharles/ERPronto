import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

/**
 * Layout usado por páginas "fora do sistema" (login, recuperar senha...).
 * Centraliza o conteúdo na tela, sem navbar/sidebar.
 */
@Component({
  selector: 'app-auth-layout',
  imports: [RouterOutlet],
  templateUrl: './auth-layout.html',
  styleUrl: './auth-layout.scss',
})
export class AuthLayout {}
