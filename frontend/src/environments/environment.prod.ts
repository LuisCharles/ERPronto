/**
 * Ambiente de PRODUÇÃO.
 * Usado quando você roda `ng build` (build de produção).
 * Aqui a URL é relativa ("/api"), pensando num cenário onde o Angular
 * é servido pelo mesmo domínio/proxy que o backend. Ajuste conforme a
 * infraestrutura real do deploy.
 */
export const environment = {
  production: true,
  apiUrl: '/api',
};
