import { Component, input } from '@angular/core';

/**
 * Indicador de carregamento simples (spinner do Bootstrap), útil para
 * mostrar enquanto uma requisição está em andamento.
 *
 * Exemplo de uso:
 * ```html
 * @if (carregando()) {
 *   <app-loading-spinner texto="Carregando dados..." />
 * }
 * ```
 */
@Component({
  selector: 'app-loading-spinner',
  imports: [],
  templateUrl: './loading-spinner.html',
  styleUrl: './loading-spinner.scss',
})
export class LoadingSpinner {
  texto = input('Carregando...');
}
