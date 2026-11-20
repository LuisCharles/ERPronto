ERPronto
Projeto acadêmico de desenvolvimento de um ERP (Enterprise Resource Planning), desenvolvido por estudantes do 3º semestre do curso de Tecnologia em Informática para Negócios da Fatec São José do Rio Preto, no 2º semestre de 2026.
Situação do projeto: em desenvolvimento. O repositório reúne o código inicial do backend e arquivos relacionados ao banco de dados. A documentação acadêmica também contempla a modelagem, os protótipos de telas e os testes realizados no SQL Server. A integração de todos os módulos em uma aplicação completa ainda faz parte da evolução do projeto.

1. Sobre o projeto
O ERPronto foi concebido para centralizar informações e apoiar atividades administrativas e operacionais de uma organização. A proposta é reunir, em um mesmo sistema, cadastros e operações que normalmente ficariam dispersos em planilhas ou controles independentes.

Os objetivos são organizar os dados, facilitar consultas, reduzir retrabalho e oferecer uma base para o acompanhamento de compras, vendas, estoque e contas financeiras.

2. Funcionalidades previstas
O escopo funcional documentado contempla:
1. Autenticação: acesso ao sistema por usuário e senha, com autenticação baseada em JWT na arquitetura proposta.
2. Usuários: cadastro, consulta, atualização e controle de situação dos usuários.
3. Perfis e permissões: definição de níveis de acesso às funcionalidades.
4. Clientes: manutenção de dados cadastrais e de contato.
5. Fornecedores: manutenção de dados cadastrais e comerciais.
6. Categorias: organização e classificação dos produtos.
7. Produtos: cadastro de mercadorias, preços e informações de estoque.
8. Estoque: registros de entrada, saída e ajuste de quantidades.
9. Vendas: registro de operações e dos respectivos itens.
10. Compras: registro de aquisições e dos respectivos itens.
11. Financeiro: contas a pagar e a receber, inclusive lançamentos manuais.
12. Dashboard: apresentação de indicadores operacionais e financeiros.
    
Importante: esta lista descreve o escopo do projeto, não significa que todos os módulos já estejam implementados e integrados.

3. Tecnologias e arquitetura
Área	Tecnologia / situação
Backend	Java 17, Spring Boot 3.2.0 e Maven
API e segurança	Spring Web, Spring Security e bibliotecas JWT presentes nas dependências
Persistência no backend	Spring Data JPA; configuração atual com H2 em memória
Banco de dados modelado e testado	Microsoft SQL Server, utilizando SQL e SQL Server Management Studio (SSMS)
Interface planejada	Angular; protótipos de telas registrados na documentação do projeto
Versionamento	Git e GitHub

A proposta de arquitetura é: interface web → API REST em Java/Spring Boot → banco de dados.

Observação sobre os bancos: o arquivo src/main/resources/application.properties deste repositório configura o backend para usar H2 em memória. O modelo relacional de SQL Server foi desenvolvido e validado separadamente. Portanto, a presença dos scripts SQL não deve ser interpretada como confirmação de que o backend já esteja integrado ao SQL Server.

4. Modelagem e testes do banco de dados
A documentação acadêmica apresenta:
- Diagrama Entidade-Relacionamento (DER) conceitual e diagrama físico do banco;
- Modelo relacional normalizado com 15 tabelas e 19 chaves estrangeiras;
- Script SQL de criação das tabelas e seus relacionamentos;
- Script de carga com 170 comandos INSERT e dados fictícios;
- Consulta de validação da quantidade de registros inseridos no SQL Server.
Na carga de testes, a tabela pessoas recebeu 30 registros, e cada uma das outras 14 tabelas recebeu 10 registros.
Tabelas do modelo
pessoas, clientes, fornecedores, usuarios, perfis, permissoes, perfil_permissao, categorias, produtos, vendas, vendas_itens, compras, compras_itens, movimentacoes_estoque e contas_financeiras.

Os arquivos SQL disponíveis no repositório podem ser consultados na pasta TABELAS DO BANCO/. A documentação do projeto contém a versão utilizada nos testes acadêmicos; antes de reproduzir os testes, confira se os scripts do GitHub correspondem à versão mais recente.

Para reproduzir a carga de testes: utilize uma base de SQL Server com a estrutura criada, tabelas vazias e identificadores iniciando em 1, pois os INSERTs simplificados referenciam IDs numéricos. Execute primeiro o script de criação e, depois, o script de inserção. Não execute os mesmos INSERTs duas vezes na mesma base já preenchida.

5. Estrutura atual do repositório
ERPronto/
├── .mvn/wrapper/       # Arquivos do Maven Wrapper
├── TABELAS DO BANCO/  # Arquivos relacionados ao banco de dados
├── src/               # Código-fonte do projeto Java
├── .gitattributes
├── .gitignore
├── mvnw               # Maven Wrapper (Linux/macOS)
├── mvnw.cmd           # Maven Wrapper (Windows)
└── pom.xml            # Dependências e configuração do projeto Maven

Estrutura baseada nos itens visíveis na raiz do repositório público na data desta atualização. Os protótipos do frontend constam da documentação acadêmica, mas não foram identificados como uma pasta Angular na raiz consultada.

6
. Como executar o backend
Pré-requisitos
- JDK 17 instalado;
- Git para clonar o repositório;
- Acesso ao terminal (o projeto inclui o Maven Wrapper).
Clone o projeto:
git clone https://github.com/LuisCharles/ERPronto.git
cd ERPronto
No Windows:
.\mvnw.cmd spring-boot:run
No Linux/macOS:
./mvnw spring-boot:run

A execução depende de o ambiente e as configurações do backend estarem corretos. Esses comandos iniciam o projeto Spring Boot; não iniciam automaticamente um frontend Angular nem conectam a aplicação ao banco SQL Server documentado.

7. Etapas do desenvolvimento
Documentado e validado nesta etapa acadêmica
- Levantamento do escopo e das funcionalidades propostas;
- Diagrama de classes, DER conceitual, modelo relacional e diagrama físico;
- Protótipos e propostas de interface dos módulos;
- Criação e testes do banco SQL Server;
- Inserção e conferência dos 170 registros fictícios.
Em desenvolvimento / etapas seguintes
- Evolução dos recursos do backend e das telas;
- Integração entre frontend, API e banco de dados definitivo;
- Testes integrados e validação dos fluxos de negócio;
- Consolidação do produto mínimo viável (MVP).

8. Documentação
A documentação de Curricularização do ERPronto reúne a descrição do sistema, funcionalidades, diagramas UML, protótipos, especificação de classes, modelagem do banco, scripts SQL, resultados dos testes e organização da equipe.

9. Equipe
Projeto desenvolvido por estudantes da Fatec São José do Rio Preto:
- Gabriel Severiano
- Guilherme Artero
- Leonardo Melo
- Luis Charles
- Luiz Henrique Carvalho
- Vitor Khaled
