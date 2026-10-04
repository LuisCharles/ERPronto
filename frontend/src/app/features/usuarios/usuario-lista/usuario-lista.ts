import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { Usuario } from '../../../core/models/usuario.model';
import { Usuarios } from '../../../core/services/usuarios';
import { Card } from '../../../shared/components/card/card';
import { Alerta } from '../../../shared/components/alerta/alerta';
import { LoadingSpinner } from '../../../shared/components/loading-spinner/loading-spinner';

/**
 * Tela de LISTAGEM de usuários. Serve de modelo para as listagens das
 * outras entidades (clientes, fornecedores, produtos...).
 *
 * Toda listagem tem 4 estados possíveis, e o HTML trata cada um:
 *  1. carregando  -> mostra o spinner
 *  2. erro        -> mostra um alerta vermelho com botão de tentar de novo
 *  3. vazia       -> mostra uma mensagem amigável
 *  4. com dados   -> mostra a tabela
 */
@Component({
  selector: 'app-usuario-lista',
  imports: [RouterLink, DatePipe, Card, Alerta, LoadingSpinner],
  templateUrl: './usuario-lista.html',
  styleUrl: './usuario-lista.scss',
})
export class UsuarioLista implements OnInit {
  private readonly usuariosService = inject(Usuarios);

  protected readonly usuarios = signal<Usuario[]>([]);
  protected readonly carregando = signal(false);
  protected readonly mensagemErro = signal<string | null>(null);

  /**
   * Mensagem enviada pela tela de cadastro depois de salvar com sucesso
   * (veja `router.navigate(..., { state })` em usuario-form.ts).
   */
  protected readonly mensagemSucesso = signal<string | null>(
    history.state?.mensagemSucesso ?? null,
  );

  /** Texto digitado no campo de busca. */
  protected readonly termoBusca = signal('');

  /**
   * Lista que aparece na tabela: os usuários filtrados pela busca.
   * Como é um `computed`, recalcula sozinho sempre que `usuarios` ou
   * `termoBusca` mudam, sem precisar chamar nada manualmente.
   */
  protected readonly usuariosFiltrados = computed(() => {
    const termo = this.termoBusca().trim().toLowerCase();

    if (!termo) {
      return this.usuarios();
    }

    return this.usuarios().filter(
      (usuario) =>
        usuario.nome.toLowerCase().includes(termo) || usuario.email.toLowerCase().includes(termo),
    );
  });

  ngOnInit(): void {
    this.carregar();
  }

  protected carregar(): void {
    this.mensagemErro.set(null);
    this.carregando.set(true);

    this.usuariosService
      .listar()
      .pipe(finalize(() => this.carregando.set(false)))
      .subscribe({
        next: (usuarios) => this.usuarios.set(usuarios),
        error: () => this.mensagemErro.set('Não foi possível carregar os usuários.'),
      });
  }

  protected buscar(evento: Event): void {
    this.termoBusca.set((evento.target as HTMLInputElement).value);
  }
}
