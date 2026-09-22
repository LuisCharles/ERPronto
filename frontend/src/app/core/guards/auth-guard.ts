import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../services/auth';

/**
 * Protege rotas que exigem login. Se o usuário não estiver autenticado,
 * ele é redirecionado para a tela de login.
 *
 * Uso (em app.routes.ts): `{ path: 'dashboard', canActivate: [authGuard], ... }`
 */
export const authGuard: CanActivateFn = () => {
  const auth = inject(Auth);
  const router = inject(Router);

  if (auth.estaAutenticado()) {
    return true;
  }

  router.navigate(['/login']);
  return false;
};
