import { Service, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest, LoginResponse } from '../models/usuario.model';

/** Chave usada para guardar o token JWT no localStorage do navegador. */
const CHAVE_TOKEN = 'erppronto_token';

/**
 * Serviço responsável por autenticação: fazer login, logout e informar
 * se o usuário está (ou não) autenticado no momento.
 *
 * Guarda o token em um signal, então qualquer componente que use
 * `authService.estaAutenticado()` é atualizado automaticamente quando
 * o usuário loga ou desloga.
 */
@Service()
export class Auth {
  private readonly http = inject(HttpClient);

  private readonly tokenSignal = signal<string | null>(this.lerTokenSalvo());

  /** true quando existe um token válido guardado. */
  readonly estaAutenticado = computed(() => this.tokenSignal() !== null);

  /**
   * Envia e-mail/senha para o backend (`POST /api/auth/login`).
   * Se der certo, o token retornado é salvo automaticamente.
   */
  login(credenciais: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${environment.apiUrl}/auth/login`, credenciais)
      .pipe(tap((resposta) => this.salvarToken(resposta.token)));
  }

  /** Remove o token salvo, efetivamente deslogando o usuário. */
  logout(): void {
    localStorage.removeItem(CHAVE_TOKEN);
    this.tokenSignal.set(null);
  }

  /** Token atual (ou null se não estiver logado). Usado pelo interceptor. */
  obterToken(): string | null {
    return this.tokenSignal();
  }

  private salvarToken(token: string): void {
    localStorage.setItem(CHAVE_TOKEN, token);
    this.tokenSignal.set(token);
  }

  private lerTokenSalvo(): string | null {
    return localStorage.getItem(CHAVE_TOKEN);
  }
}
