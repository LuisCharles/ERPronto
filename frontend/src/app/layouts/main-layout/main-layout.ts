import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from '../../shared/components/navbar/navbar';
import { Sidebar } from '../../shared/components/sidebar/sidebar';
import { Footer } from '../../shared/components/footer/footer';

/**
 * Layout usado pelas páginas "internas" do sistema (depois do login).
 * Estrutura: navbar no topo, sidebar à esquerda, conteúdo da rota
 * no meio (via router-outlet) e footer embaixo.
 */
@Component({
  selector: 'app-main-layout',
  imports: [RouterOutlet, Navbar, Sidebar, Footer],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss',
})
export class MainLayout {}
