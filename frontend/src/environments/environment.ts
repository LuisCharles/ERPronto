/**
 * Ambiente de DESENVOLVIMENTO.
 * Usado quando você roda `ng serve` (npm start).
 * Aponta para o backend Spring Boot rodando localmente na porta 8080.
 */
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
};
