import { Component, signal } from '@angular/core';
import { Card } from '../../shared/components/card/card';
import { Botao } from '../../shared/components/botao/botao';
import { Alerta } from '../../shared/components/alerta/alerta';
import { LoadingSpinner } from '../../shared/components/loading-spinner/loading-spinner';

/**
 * Página de exemplo, exibida logo após o login.
 *
 * Serve como "catálogo vivo" dos componentes reutilizáveis de
 * src/app/shared/components — use-a como referência para aprender a
 * usá-los em novas telas do sistema.
 */
@Component({
  selector: 'app-dashboard',
  imports: [Card, Botao, Alerta, LoadingSpinner],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard {
  protected readonly mostrarAlerta = signal(true);
  protected readonly carregandoExemplo = signal(false);

  protected simularCarregamento(): void {
    this.carregandoExemplo.set(true);
    setTimeout(() => this.carregandoExemplo.set(false), 1500);
  }
}
