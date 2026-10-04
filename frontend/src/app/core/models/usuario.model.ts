/**
 * Dados enviados ao backend para autenticar o usuário.
 * Precisa bater com `AuthenticationRequest` (Java) do backend.
 */
export interface LoginRequest {
  email: string;
  password: string;
}

/**
 * Resposta do backend após um login bem-sucedido.
 * Precisa bater com `AuthenticationResponse` (Java) do backend.
 */
export interface LoginResponse {
  token: string;
}

/**
 * Usuário como ele vem do backend (`GET /api/usuarios`).
 * Espelha a entidade `Usuario` (Java) — repare que a senha NUNCA vem
 * na resposta, por segurança.
 */
export interface Usuario {
  id: number;
  nome: string;
  email: string;
  perfilId: number;
  ativo: boolean;
  /** Data em formato ISO, ex: "2026-10-04T17:49:00-03:00". */
  criadoEm: string;
  atualizadoEm: string | null;
}

/**
 * Dados enviados ao backend para cadastrar um usuário (`POST /api/usuarios`).
 * Aqui vai a senha em texto puro; quem gera o `senha_hash` é o backend.
 * `id`, `criadoEm` e `atualizadoEm` não são enviados: o banco preenche sozinho.
 */
export interface UsuarioRequest {
  nome: string;
  email: string;
  senha: string;
  perfilId: number;
  ativo: boolean;
}
