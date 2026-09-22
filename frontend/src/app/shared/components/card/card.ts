import { Component, input } from '@angular/core';

/**
 * Card genérico (baseado no `.card` do Bootstrap) para agrupar conteúdo.
 * Usa `<ng-content>` para receber o conteúdo de dentro do card, então
 * pode ser usado com qualquer HTML, formulário, lista, etc.
 *
 * Exemplo de uso:
 * ```html
 * <app-card titulo="Meus dados" icone="fa-solid fa-user">
 *   <p>Qualquer conteúdo aqui dentro...</p>
 * </app-card>
 * ```
 */
@Component({
  selector: 'app-card',
  imports: [],
  templateUrl: './card.html',
  styleUrl: './card.scss',
})
export class Card {
  /** Título mostrado no cabeçalho do card. Se não for passado, o cabeçalho some. */
  titulo = input<string | null>(null);

  /** Classe de ícone do Font Awesome exibida ao lado do título. Opcional. */
  icone = input<string | null>(null);
}
