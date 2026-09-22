import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { Auth } from '../../core/services/auth';
import { Card } from '../../shared/components/card/card';
import { Botao } from '../../shared/components/botao/botao';
import { Alerta } from '../../shared/components/alerta/alerta';

/**
 * Tela de login. Demonstra o "combo" mais comum de uma tela de formulário:
 * ReactiveFormsModule + os componentes compartilhados (Card, Botao, Alerta)
 * + o AuthService para autenticar contra o backend.
 */
@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, Card, Botao, Alerta],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private readonly formBuilder = inject(FormBuilder);
  private readonly auth = inject(Auth);
  private readonly router = inject(Router);

  protected readonly carregando = signal(false);
  protected readonly mensagemErro = signal<string | null>(null);

  protected readonly form = this.formBuilder.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.minLength(4)]],
  });

  protected get emailInvalido(): boolean {
    const campo = this.form.controls.email;
    return campo.invalid && (campo.dirty || campo.touched);
  }

  protected get senhaInvalida(): boolean {
    const campo = this.form.controls.senha;
    return campo.invalid && (campo.dirty || campo.touched);
  }

  protected entrar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.mensagemErro.set(null);
    this.carregando.set(true);

    const { email, senha } = this.form.getRawValue();

    this.auth
      .login({ email, password: senha })
      .pipe(finalize(() => this.carregando.set(false)))
      .subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: () => this.mensagemErro.set('E-mail ou senha inválidos.'),
      });
  }
}
