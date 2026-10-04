import { Component, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { Usuarios } from '../../../core/services/usuarios';
import { Card } from '../../../shared/components/card/card';
import { Botao } from '../../../shared/components/botao/botao';
import { Alerta } from '../../../shared/components/alerta/alerta';

/**
 * Tela de CADASTRO de usuário. Serve de modelo para os formulários das
 * outras entidades.
 *
 * Passo a passo do que acontece ao clicar em "Salvar":
 *  1. Se o formulário tiver erro, marca todos os campos como "tocados"
 *     (para as mensagens de erro aparecerem) e para por aí.
 *  2. Liga o `carregando` (o botão mostra o spinner e trava o clique).
 *  3. Envia os dados para o backend pelo serviço `Usuarios`.
 *  4. Deu certo -> volta para a listagem com uma mensagem de sucesso.
 *     Deu errado -> mostra o erro no topo do formulário.
 */
@Component({
  selector: 'app-usuario-form',
  imports: [ReactiveFormsModule, RouterLink, Card, Botao, Alerta],
  templateUrl: './usuario-form.html',
  styleUrl: './usuario-form.scss',
})
export class UsuarioForm {
  private readonly formBuilder = inject(FormBuilder);
  private readonly usuariosService = inject(Usuarios);
  private readonly router = inject(Router);

  protected readonly salvando = signal(false);
  protected readonly mensagemErro = signal<string | null>(null);

  /**
   * Os campos do formulário e suas validações.
   * O nome de cada campo aqui é o mesmo usado no `formControlName` do HTML.
   */
  protected readonly form = this.formBuilder.nonNullable.group({
    nome: ['', [Validators.required, Validators.maxLength(100)]],
    email: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.minLength(6)]],
    perfilId: [null as number | null, [Validators.required, Validators.min(1)]],
    ativo: [true],
  });

  /**
   * true quando o campo tem erro E o usuário já mexeu nele.
   * Assim a tela não abre "toda vermelha" antes de a pessoa digitar algo.
   *
   * Uso no HTML: `[class.is-invalid]="campoInvalido('nome')"`
   */
  protected campoInvalido(nomeDoCampo: keyof typeof this.form.controls): boolean {
    const campo = this.form.controls[nomeDoCampo];
    return campo.invalid && (campo.dirty || campo.touched);
  }

  protected salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.mensagemErro.set(null);
    this.salvando.set(true);

    const { nome, email, senha, perfilId, ativo } = this.form.getRawValue();

    this.usuariosService
      .cadastrar({ nome: nome.trim(), email: email.trim(), senha, perfilId: perfilId!, ativo })
      .pipe(finalize(() => this.salvando.set(false)))
      .subscribe({
        next: (usuario) =>
          this.router.navigate(['/usuarios'], {
            state: { mensagemSucesso: `Usuário "${usuario.nome}" cadastrado com sucesso.` },
          }),
        error: (erro: HttpErrorResponse) => this.mensagemErro.set(this.traduzirErro(erro)),
      });
  }
  //peidei na farofa

  /** Transforma o erro HTTP em uma mensagem que o usuário entende. */
  private traduzirErro(erro: HttpErrorResponse): string {
    switch (erro.status) {
      case 0:
        return 'Não foi possível conectar ao servidor. Verifique se o backend está rodando.';
      case 400:
        return 'Dados inválidos. Revise os campos e tente novamente.';
      case 409:
        return 'Já existe um usuário cadastrado com este e-mail.';
      default:
        return 'Não foi possível cadastrar o usuário. Tente novamente.';
    }
  }
}
