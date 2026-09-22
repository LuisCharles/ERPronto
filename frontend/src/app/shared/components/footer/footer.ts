import { Component } from '@angular/core';

/** Rodapé simples, exibido em todas as telas internas do sistema. */
@Component({
  selector: 'app-footer',
  imports: [],
  templateUrl: './footer.html',
  styleUrl: './footer.scss',
})
export class Footer {
  protected readonly anoAtual = new Date().getFullYear();
}
