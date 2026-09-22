import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { Auth } from '../services/auth';

/**
 * Anexa automaticamente o header `Authorization: Bearer <token>` em toda
 * requisição HTTP, sempre que houver um usuário logado.
 * Assim nenhum componente precisa se preocupar em montar esse header na mão.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(Auth).obterToken();

  if (!token) {
    return next(req);
  }

  const requisicaoComToken = req.clone({
    setHeaders: { Authorization: `Bearer ${token}` },
  });

  return next(requisicaoComToken);
};
