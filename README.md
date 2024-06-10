# -- CRUD CURSO

## 🧑🏻- Executar
  Este projeto utiliza o Docker para subir um banco PostgreSQL, por isso ao abrir o mesmo execute:
  
  **docker compose up -d    # Sobe o container do PostgreSQL**
  
  Após isso pode iniciar o projeto normalmente. 
  
  Para visualizar a documentação das requisições, acesse o link do Swagger: http://localhost:8080/swagger-ui/index.html

## 🗃️ **Dependências:** 
- 🗄️ Spring Data JPA 
- 🌐  Spring Web 
- 📃 PostgreSQL Driver 
- 🛠️ SpringBoot DevTools 
- 🐋 Docker Compose Support 
 
## 📂 Estrutura do Projeto
    src-|
        |
        |-controller        # Vincula métodos com o endereçamento de navegação
        |-dto-|             # Armazena as estruturas dos objetos de transferência e conversão de dados
        |     |
        |     |-mapper      # Realiza a conversão de entidades para dtos e vice-versa
        |     |-request     # Estruturas de dtos para o recebimento de dados
        |     |-response    # Estruturas de dtos para o envio de dados
        |
        |-exception         # Armazena as exceções e guarda as regras para lidar com as mesmas
        |-model             # Aonde as estruturas das entidades são armazenadas para serem criadas no banco
        |-repository        # Envia as query de inserção de dados para o banco
        |-service           # Organiza e faz a validação dos dados

## Minimum Viable Product
- ✅ Container Docker
- ✅ Conectar Banco
- ✅ Entidade Professor
- ✅ Entidade Aluno
- ✅ Entidade Curso
- ✅ Relacionar Curso e Professor (n:1)
- ✅ Relacionar Curso e Aluno (n:n)
- ✅ Listar Professor, Aluno e Curso
- ✅ Criar Aluno
- ✅ Atualizar Aluno
- ✅ Excluir Aluno
- ✅ Matricular Aluno
- ✅ Desmatricular Aluno
- ✅ Criar Professor
- ✅ Atualizar Professor
- ✅ Excluir Professor
- ✅ Criar Curso
- ✅ Atualizar Curso
- ✅ Excluir Curso
- ✅ Ver Alunos do Curso
- ✅ Ver Cursos
- ✅ Validações
- ✅ Testes Unitários
 
-> EXTRAS:
- ✅ Tratamento de Exceções
- ✅ Testes de Integração
- ✅ Swagger
- ✅ Paginação de Curso
 
US's - MVP
- ✅ [US001] Configurar Ambiente
    - ✅ [US001-1] Criar Container Docker
    - ✅ [US001-2] Conectar Banco
- ✅ [US002] Criar Entidades
    - ✅ [US002-1] Entidade Aluno
    - ✅ [US002-2] Entidade Professor
    - ✅ [US002-3] Entidade Curso
- ✅ [US003] Como aluno, quero me registrar
    - ✅ [US003-1] Criar Aluno
    - ✅ [US003-2] Listar Alunos
    - ✅ [US003-3] Atualizar Aluno
    - ✅ [US003-4] Remover Aluno
- ✅ [US004] Como professor, quero me registrar
    - ✅ [US004-1] Criar Professor
    - ✅ [US004-2] Listar Professores
    - ✅ [US004-3] Atualizar Professor
    - ✅ [US004-4] Remover Professor
- ✅ [US005] Como administrador quero administrar um curso
    - ✅ [US005-1] Criar Curso
    - ✅ [US005-2] Paginação de Curso
    - ✅ [US005-3] Atualizar Curso
    - ✅ [US005-4] Excluir Curso
- ✅ [US006] Gerenciar Alunos no Curso
    - ✅ [US006-1] Matricular Aluno no Curso
    - ✅ [US006-2] Desmatricular Aluno do Curso
    - ✅ [US006-3] Ver Alunos no Curso

US's - EXTRA
- ✅ [US00X] Funcionalidades Opcionais
    - ✅ [US00X-1] Swagger
    - ✅ [US00X-2] Tratamento de Exceções
    - ✅ [US00X-3] Testes Unitários
    - ✅ [US00X-4] Testes de Integração

🚧 DÉBITOS TÉCNICOS

- ✅ [US00X-5] Buscar Aluno por Nome/CPF
- ✅ [US00X-6] Buscar Professor por Nome/CPF
- 🚧 [US00X-7] Buscar Aluno Especifico no Curso
- 🚧 [US00X-8] Mostrar Idade do Aluno e Professor
    