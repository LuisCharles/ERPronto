import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Usuario, UsuarioRequest } from '../models/usuario.model';

/**
 * Serviço que conversa com o backend sobre usuários.
 *
 * Toda chamada HTTP da entidade fica AQUI, nunca dentro dos componentes.
 * Assim, se a URL ou o formato mudar no backend, só este arquivo muda.
 *
 * Para criar o serviço de outra entidade (ex: clientes), copie este
 * arquivo e troque o nome, os tipos e o `urlBase`.
 */
@Service()
export class Usuarios {
  private readonly http = inject(HttpClient);

  private readonly urlBase = `${environment.apiUrl}/usuarios`;

  /** Busca todos os usuários (`GET /api/usuarios`). */
  listar(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.urlBase);
  }

  /** Cadastra um novo usuário (`POST /api/usuarios`) e devolve o usuário criado. */
  cadastrar(usuario: UsuarioRequest): Observable<Usuario> {
    return this.http.post<Usuario>(this.urlBase, usuario);
  }
}
