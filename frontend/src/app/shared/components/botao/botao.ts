import { Component, input, output } from '@angular/core';

export type VarianteBotao = 'primary' | 'secondary' | 'danger' | 'success' | 'outline-primary';

/**
 * Botão reutilizável baseado nas classes do Bootstrap.
 *
 * Exemplo de uso:
 * ```html
 * <app-botao texto="Salvar" icone="fa-solid fa-floppy-disk" (clicado)="salvar()" />
 * <app-botao texto="Entrando..." [carregando]="true" tipo="submit" />
 * ```
 */
@Component({
  selector: 'app-botao',
  imports: [],
  templateUrl: './botao.html',
  styleUrl: './botao.scss',
})
export class Botao {
  /** Texto exibido dentro do botão. */
  texto = input.required<string>();

  /** Classe de ícone do Font Awesome, ex: "fa-solid fa-check". Opcional. */
  icone = input<string | null>(null);

  /** Cor/estilo do botão, seguindo as variantes do Bootstrap. */
  variante = input<VarianteBotao>('primary');

  /** `type` nativo do botão HTML. */
  tipo = input<'button' | 'submit' | 'reset'>('button');

  /** Desabilita o botão. */
  desabilitado = input(false);

  /** Quando true, mostra um spinner e desabilita o clique (ex: aguardando requisição). */
  carregando = input(false);

  /** Disparado quando o usuário clica no botão (e ele não está desabilitado/carregando). */
  clicado = output<void>();

  aoClicar(): void {
    if (this.desabilitado() || this.carregando()) {
      return;
    }
    this.clicado.emit();
  }
}
