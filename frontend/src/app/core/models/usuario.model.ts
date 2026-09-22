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
