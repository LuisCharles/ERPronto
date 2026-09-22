# ErpPronto — Frontend

Projeto Angular (v22) criado como modelo de estudo, usando **Bootstrap 5**
(estilo/layout) e **Font Awesome** (ícones). Conecta com o backend Spring
Boot que está na raiz do repositório (`/api/auth/login`).

## Como rodar

```bash
npm install   # só na primeira vez
npm start     # roda em http://localhost:4200
```

O backend (Spring Boot) precisa estar rodando em `http://localhost:8080`
(porta padrão) para o login funcionar de verdade — o `SecurityConfig.java`
do backend já libera CORS para `http://localhost:4200`.

## Como este projeto está organizado

Tudo fica dentro de `src/app`, dividido por **responsabilidade**:

```
src/app/
├── core/                    → "motor" da aplicação, sem tela própria
│   ├── guards/               → controlam se uma rota pode ser acessada (ex: precisa estar logado)
│   ├── interceptors/         → interceptam toda requisição HTTP (ex: anexar o token JWT)
│   ├── models/                → interfaces/tipos usados pelos serviços
│   └── services/              → lógica compartilhada (ex: AuthService)
│
├── layouts/                 → "moldes" de tela
│   ├── auth-layout/           → tela cheia centralizada (usado no login)
│   └── main-layout/           → navbar + sidebar + footer (usado nas telas internas)
│
├── shared/components/       → componentes pequenos e reutilizáveis em qualquer tela
│   ├── botao/                 → <app-botao> — botão padronizado
│   ├── card/                  → <app-card> — caixa de conteúdo com título
│   ├── alerta/                → <app-alerta> — mensagem de sucesso/erro/aviso
│   ├── loading-spinner/       → <app-loading-spinner> — indicador de carregamento
│   ├── navbar/, sidebar/, footer/ → usados só dentro do main-layout
│
├── features/                 → as telas/páginas do sistema, uma pasta por assunto
│   ├── login/                  → tela de login (formulário reativo)
│   └── dashboard/               → tela de exemplo pós-login, mostra os componentes acima
│
├── app.routes.ts             → mapa de rotas (qual componente aparece em cada URL)
└── app.config.ts             → configuração global (rotas, HttpClient, etc.)
```

### Por que separar assim?

- **`core`** você usa em qualquer lugar da aplicação, mas ele não tem HTML/tela.
- **`layouts`** definem "a moldura" — o que aparece ao redor do conteúdo (ou nada, no caso do login).
- **`shared/components`** são blocos de LEGO: pequenos, sem lógica de negócio, reaproveitáveis em qualquer tela nova.
- **`features`** é onde moram as telas reais do sistema. Quando for criar uma tela nova
  (ex: "clientes"), crie uma pasta `features/clientes/` e siga o mesmo padrão do `dashboard`.

### Fluxo de login

1. Usuário preenche e-mail/senha em `features/login`.
2. `Login` chama `AuthService.login()` (`core/services/auth.ts`), que faz um
   `POST /api/auth/login` no backend.
3. Se der certo, o token JWT é salvo (`localStorage`) e o usuário é levado para `/dashboard`.
4. Dali em diante, o `authInterceptor` (`core/interceptors`) anexa esse token em
   toda requisição automaticamente, e o `authGuard` (`core/guards`) bloqueia
   o acesso às telas internas se não houver login.

### Estilos (Bootstrap + Font Awesome)

- `src/styles.scss` importa o Bootstrap inteiro e permite customizar cores/fontes
  antes disso, em `src/styles/_variaveis.scss` (mude `$primary`, por exemplo, e
  todo o sistema muda de cor).
- Ícones Font Awesome já vêm prontos: basta usar `<i class="fa-solid fa-user"></i>`
  em qualquer template (veja exemplos em `features/dashboard/dashboard.html`).

## Comandos úteis

```bash
npm start                     # servidor de desenvolvimento (localhost:4200)
npm run build                 # build de produção (gera dist/frontend)
npm test                      # roda os testes unitários
npx ng generate component features/nome-da-tela   # gera uma tela nova já no padrão do projeto
```
