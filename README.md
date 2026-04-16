# Agendador de Tarefas

Um sistema de agendamento de tarefas desenvolvido com Spring Boot, MongoDB e autenticação JWT.

## Descrição

O Agendador de Tarefas é uma aplicação RESTful que permite aos usuários criar, gerenciar e acompanhar tarefas agendadas. O sistema suporta autenticação baseada em tokens JWT e utiliza MongoDB como banco de dados.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.4**
- **MongoDB** (banco de dados NoSQL)
- **Spring Security** (autenticação JWT)
- **Spring Web** (REST API)
- **Spring Cloud OpenFeign** (chamadas para serviços externos)
- **Lombok** (redução de código boilerplate)
- **MapStruct** (mapeamento entre entidades e DTOs)
- **Gradle** (gerenciamento de dependências e build)
- **Docker** (containerização)

## Funcionalidades

- ✅ Cadastro de tarefas com data de evento
- ✅ Listagem de tarefas por período
- ✅ Listagem de tarefas por usuário (via token JWT)
- ✅ Atualização de tarefas
- ✅ Exclusão de tarefas
- ✅ Controle de status de notificação (PENDENTE, NOTIFICADO, CANCELADO)
- ✅ Autenticação baseada em JWT
- ✅ Integração com serviço de usuários via Feign

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/benevenuto/agendadortarefas/
│   │   ├── AgendadorTarefasApplication.java          # Classe principal
│   │   ├── business/                                 # Camada de negócio
│   │   │   ├── TarefasService.java                   # Serviço de tarefas
│   │   │   ├── dto/                                  # Data Transfer Objects
│   │   │   └── mapper/                               # Mapeadores MapStruct
│   │   ├── controller/                               # Controladores REST
│   │   │   └── TarefasController.java                # API de tarefas
│   │   ├── exceptions/                               # Tratamento de exceções
│   │   └── infrastructure/                           # Camada de infraestrutura
│   │       ├── entity/                               # Entidades do banco
│   │       ├── enums/                                # Enums do sistema
│   │       ├── repository/                           # Repositórios
│   │       └── security/                             # Configurações de segurança
│   └── resources/
│       └── application.properties                    # Configurações da aplicação
└── test/                                             # Testes unitários
```

## Modelo de Dados

### Tarefa
- `id`: Identificador único (String)
- `nomeTarefa`: Nome da tarefa
- `descricao`: Descrição detalhada
- `dataCriacao`: Data de criação (LocalDateTime)
- `dataEvento`: Data do evento/tarefa (LocalDateTime)
- `emailUsuario`: Email do usuário proprietário
- `dataAlteracao`: Última data de alteração (LocalDateTime)
- `statusNotificacaoEnum`: Status da notificação (PENDENTE, NOTIFICADO, CANCELADO)

## API Endpoints

### Tarefas

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/tarefas` | Criar nova tarefa | JWT (header Authorization) |
| GET | `/tarefas/eventos` | Buscar tarefas por período | Não |
| GET | `/tarefas` | Buscar tarefas do usuário | JWT (header Authorization) |
| PUT | `/tarefas?id={id}` | Atualizar tarefa | Não |
| PATCH | `/tarefas?status={status}&id={id}` | Alterar status da notificação | Não |
| DELETE | `/tarefas?id={id}` | Excluir tarefa | Não |

### Exemplos de Uso

#### Criar Tarefa
```bash
POST /tarefas
Authorization: Bearer {token}
Content-Type: application/json

{
  "nomeTarefa": "Reunião de Projeto",
  "descricao": "Reunião semanal da equipe",
  "dataEvento": "2024-12-01T10:00:00"
}
```

#### Buscar Tarefas por Período
```bash
GET /tarefas/eventos?dataInicial=2024-12-01T00:00:00&dataFinal=2024-12-31T23:59:59
```

## Configuração e Execução

### Pré-requisitos

- Java 17 ou superior
- MongoDB (local ou Docker)
- Gradle (opcional, wrapper incluído)

### Configuração do Banco de Dados

O sistema utiliza MongoDB. Configure a conexão no arquivo `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/db_agendador
```

### Executando Localmente

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd agendador-tarefas
   ```

2. **Execute o MongoDB**
   ```bash
   # Via Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   ```

3. **Execute a aplicação**
   ```bash
   # Via Gradle wrapper
   ./gradlew bootRun
   ```

A aplicação estará disponível em `http://localhost:8081`

### Executando com Docker

1. **Build da imagem**
   ```bash
   docker build -t agendador-tarefas .
   ```

2. **Execute o container**
   ```bash
   docker run -p 8081:8081 agendador-tarefas
   ```

## Configurações

As configurações principais estão no arquivo `src/main/resources/application.properties`:

- `spring.application.name`: Nome da aplicação
- `spring.data.mongodb.uri`: URI de conexão com MongoDB
- `usuario.url`: URL do serviço de usuários
- `server.port`: Porta do servidor (8081)

## Segurança

- Autenticação baseada em JWT
- Tokens devem ser enviados no header `Authorization` como `Bearer {token}`
- Validação de usuários através de serviço externo via Feign

## Tratamento de Exceções

O sistema possui tratamento global de exceções através da classe `GlobalExceptionHandler`, que trata:
- Recursos não encontrados (`ResourceNotFoundException`)
- Outras exceções genéricas

## Testes

Execute os testes com:
```bash
./gradlew test
```

## Build e Empacotamento

Para gerar o JAR executável:
```bash
./gradlew build
```

O arquivo será gerado em `build/libs/agendador-tarefas-0.0.1-SNAPSHOT.jar`
