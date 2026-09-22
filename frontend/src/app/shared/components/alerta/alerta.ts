import { Component, input, output } from '@angular/core';

export type TipoAlerta = 'success' | 'danger' | 'warning' | 'info';

const ICONE_POR_TIPO: Record<TipoAlerta, string> = {
  success: 'fa-solid fa-circle-check',
  danger: 'fa-solid fa-circle-exclamation',
  warning: 'fa-solid fa-triangle-exclamation',
  info: 'fa-solid fa-circle-info',
};

/**
 * Mensagem de alerta reutilizável (baseado no `.alert` do Bootstrap).
 *
 * Exemplo de uso:
 * ```html
 * <app-alerta tipo="danger" mensagem="E-mail ou senha inválidos." />
 * ```
 */
@Component({
  selector: 'app-alerta',
  imports: [],
  templateUrl: './alerta.html',
  styleUrl: './alerta.scss',
})
export class Alerta {
  tipo = input<TipoAlerta>('info');
  mensagem = input.required<string>();

  /** Se true, mostra um botão de fechar (X). */
  fechavel = input(false);

  /** Disparado quando o usuário clica no botão de fechar. */
  fechado = output<void>();

  protected get icone(): string {
    return ICONE_POR_TIPO[this.tipo()];
  }
}
