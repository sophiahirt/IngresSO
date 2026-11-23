# IngresSÔ

Sistema desktop de **venda de ingressos**, feito em **Java Swing** com **Maven**, organizado no padrão **MVC + DAO** e com os dados guardados em um banco **MySQL**.

O projeto foi desenvolvido para a disciplina de Programação, com a finalidade de aplicar conceitos de programação orientada a objetos, desenvolvimento de interfaces gráficas, manipulação de dados e integração com bancos de dados.

![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Maven-build-blue)
![MySQL](https://img.shields.io/badge/MySQL-XAMPP-4479A1)
![Swing](https://img.shields.io/badge/Interface-Swing-lightgrey)

---

## Sumário

- [Sobre o projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Arquitetura](#-arquitetura)
- [Banco de dados](#-banco-de-dados)
- [Pré-requisitos](#-pré-requisitos)
- [Como instalar e executar](#-como-instalar-e-executar)
- [Como usar o sistema](#-como-usar-o-sistema)
- [Regras de negócio](#-regras-de-negócio)
- [Solução de problemas](#-solução-de-problemas)
- [Melhorias futuras](#-melhorias-futuras)

---

## Sobre o projeto

O **IngresSÔ** permite cadastrar eventos (shows, peças, palestras etc.), vender ingressos para eles e acompanhar o total arrecadado. O sistema tem **duas telas**:

| Tela | Para que serve |
|------|----------------|
| **Eventos** | Cadastrar e excluir eventos, vendo vagas, vendidos e disponíveis. |
| **Vendas** | Vender ingressos, cancelar vendas e ver o total arrecadado. |

O visual é simples, no estilo clássico do **NetBeans** (tema padrão do Swing). As duas telas foram criadas com o editor visual do NetBeans, então os arquivos `.form` acompanham o código e a aba **Design** funciona.

---

## Funcionalidades

**Tela de Eventos**
- Cadastrar evento informando nome, preço e número de vagas.
- Listar eventos com preço, vagas, ingressos vendidos e disponíveis.
- Excluir evento (somente se ele não tiver vendas).
- Botão para ir à tela de Vendas.

**Tela de Vendas**
- Listar os eventos com ingressos disponíveis.
- Vender ingressos informando o comprador e a quantidade.
- Listar as vendas realizadas (comprador, evento, quantidade e total).
- Cancelar uma venda, devolvendo os ingressos ao evento.
- Exibir o **total arrecadado**.
- Botão para voltar à tela de Eventos.

**Geral**
- Dados salvos no MySQL: nada se perde ao fechar o programa.
- Validação dos campos, com mensagens claras para o usuário.
- Mensagem de erro explicativa quando o banco de dados não está disponível.

---

## Tecnologias

| Tecnologia | Uso |
|------------|-----|
| Java 21 | Linguagem principal |
| Java Swing | Interface gráfica |
| Maven | Gerenciamento do projeto e das dependências |
| MySQL (via XAMPP) | Banco de dados |
| JDBC + `mysql-connector-j` 9.5.0 | Conexão entre o Java e o MySQL |
| NetBeans | IDE usada, com editor visual (GUI Builder) |

---

## Estrutura do projeto

```
IngresSO/
├── pom.xml                      # Configuração do Maven (Java 21 + driver MySQL)
├── db.sql                       # Script de criação do banco de dados
├── README.md
├── .gitignore
└── src/
    └── main/
        └── java/
            ├── controller/
            │   ├── AppController.java       # Ponto de entrada (main) e navegação entre telas
            │   ├── EventoController.java    # Lógica da tela de Eventos
            │   └── VendaController.java     # Lógica da tela de Vendas
            ├── dao/
            │   ├── ConexaoDB.java           # Dados e abertura da conexão com o MySQL
            │   ├── EventoDAO.java           # Consultas da tabela eventos
            │   ├── VendaDAO.java            # Consultas da tabela vendas
            │   └── BancoException.java      # Erro de acesso ao banco
            ├── model/
            │   ├── Evento.java
            │   ├── Venda.java
            │   └── Bilheteria.java          # Regras de negócio
            └── view/
                ├── TelaEventos.java / .form # Tela de Eventos (editor visual)
                ├── TelaVendas.java / .form  # Tela de Vendas (editor visual)
                ├── ModeloTabela.java        # Tabela somente leitura
                └── Formato.java             # Formatação de valores em reais
```

---

## Arquitetura

O projeto segue o padrão **MVC**, com uma camada extra de **DAO** para falar com o banco.

| Pacote | Responsabilidade |
|--------|------------------|
| `model` | Entidades (`Evento`, `Venda`) e regras de negócio (`Bilheteria`). Não conhece a interface. |
| `view` | Telas Swing. Só mostram dados e avisam o controller quando o usuário clica. Não têm regra de negócio. |
| `controller` | Liga as telas ao model: lê os campos, chama as regras e atualiza as tabelas. |
| `dao` | Único lugar que escreve SQL e usa JDBC. |

**Fluxo de uma venda:**

```
Usuário clica em "Vender"
        │
        ▼
VendaController.vender()      → lê comprador e quantidade da TelaVendas
        │
        ▼
Bilheteria.vender()           → valida (comprador, quantidade, vagas disponíveis)
        │
        ▼
VendaDAO.inserir()            → grava a venda no MySQL
        │
        ▼
Bilheteria.recarregar()       → relê eventos e vendas do banco
        │
        ▼
VendaController.atualizar()   → atualiza as tabelas e o total na tela
```

O `AppController` cria as duas telas e o model, liga cada controller à sua tela e cuida da troca entre elas.

---

## Banco de dados

O script `db.sql` cria o banco **`ingresso`** com duas tabelas:

```sql
CREATE DATABASE ingresso;

USE ingresso;

CREATE TABLE eventos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    capacidade INT NOT NULL
);

CREATE TABLE vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    evento_id INT NOT NULL,
    comprador VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    FOREIGN KEY (evento_id) REFERENCES eventos(id)
);
```

**Relacionamento:** um evento pode ter várias vendas (`vendas.evento_id` → `eventos.id`).

```
┌───────────────┐         ┌─────────────────┐
│    eventos    │ 1     N │     vendas      │
├───────────────┤◄────────┤─────────────────┤
│ id (PK)       │         │ id (PK)         │
│ nome          │         │ evento_id (FK)  │
│ preco         │         │ comprador       │
│ capacidade    │         │ quantidade      │
└───────────────┘         └─────────────────┘
```

> Os ingressos **vendidos** de cada evento não são uma coluna: o sistema soma a quantidade das vendas do evento a cada consulta. Assim, o número nunca fica desatualizado, e cancelar uma venda devolve os ingressos automaticamente.

---

## Pré-requisitos

- **JDK 21** ou superior.
- **NetBeans** (recomendado). Ele já traz o Maven embutido.
- **XAMPP** (ou outro MySQL instalado) com o **MySQL** e o **Apache** disponíveis.
- Conexão com a internet na primeira abertura, para o Maven baixar o driver do MySQL.

> Se o seu JDK for mais antigo que o 21, abra o `pom.xml` e troque os dois valores `21` (em `maven.compiler.source` e `maven.compiler.target`) pela versão do seu JDK.

---

## Como instalar e executar

### 1. Baixar o projeto

```bash
git clone https://github.com/SEU-USUARIO/IngresSO.git
```

Ou baixe o ZIP pelo botão **Code → Download ZIP** do GitHub e extraia.

### 2. Ligar o MySQL (XAMPP)

1. Abra o **XAMPP Control Panel**.
2. Clique em **Start** ao lado de **Apache** e de **MySQL**. Os dois devem ficar com fundo verde.

> O Apache é necessário porque o phpMyAdmin roda por ele.

### 3. Criar o banco de dados

**Pelo phpMyAdmin:**
1. Acesse `http://localhost/phpmyadmin` no navegador.
2. Abra a aba **Importar**.
3. Em **Escolher arquivo**, selecione o `db.sql` do projeto.
4. Clique em **Importar**, no final da página.
5. Confira se o banco **ingresso** aparece na coluna da esquerda, com as tabelas `eventos` e `vendas`.

**Ou pelo terminal:**

```bash
mysql -u root -p < db.sql
```

### 4. Configurar a conexão

Abra `src/main/java/dao/ConexaoDB.java` e confira os dados:

```java
private static final String URL = "jdbc:mysql://localhost:3306/ingresso";
private static final String USUARIO = "root";
private static final String SENHA = "";
```

O XAMPP vem com o usuário `root` **sem senha**, então não precisa mudar nada. Se o seu MySQL tiver senha ou outra porta, ajuste esses valores.

### 5. Abrir e executar no NetBeans

1. Vá em **File → Open Project**.
2. Selecione a pasta `IngresSO` (a que contém o `pom.xml`). Ela deve aparecer com o ícone de projeto Maven.
3. Aguarde o NetBeans baixar as dependências.
4. Clique com o botão direito no projeto e escolha **Run**, ou abra `AppController.java` e use **Run File** (`Shift + F6`).

### Alternativa: executar pelo terminal

Com o Maven instalado, na pasta do projeto:

```bash
mvn compile exec:java
```

A classe principal (`controller.AppController`) já está definida no `pom.xml`.

---

## Como usar o sistema

### Tela de Eventos

1. Preencha **Evento**, **Preço (R$)** e **Vagas**.
2. Clique em **Cadastrar evento**. O evento aparece na tabela.
3. Para remover, selecione o evento na tabela e clique em **Excluir evento**. O sistema pede confirmação.
4. Clique em **Ir para Vendas** para trocar de tela.

O preço pode ser digitado com vírgula ou ponto (`50,00` ou `50.00`).

### Tela de Vendas

1. Selecione o evento na tabela **Eventos disponíveis**.
2. Digite o nome do **Comprador** e escolha a **Quantidade**.
3. Clique em **Vender**. A venda aparece em **Vendas realizadas**, e o total arrecadado é atualizado.
4. Para cancelar, selecione a venda na tabela e clique em **Cancelar venda**. O sistema pede confirmação, e os ingressos voltam ao evento.
5. Clique em **Ir para Eventos** para voltar.

---

## Regras de negócio

- O nome do evento é obrigatório.
- O preço não pode ser negativo.
- O número de vagas deve ser maior que zero.
- O nome do comprador é obrigatório.
- A quantidade de ingressos deve ser maior que zero (de 1 a 100 por venda).
- Não é possível vender mais ingressos do que o número de vagas disponíveis.
- Um evento **com vendas** não pode ser excluído. Cancele as vendas antes.
- Ao cancelar uma venda, os ingressos voltam para o evento.
- **Total arrecadado** = soma de (preço do evento × quantidade) de todas as vendas.

---

## Solução de problemas

| Problema | Causa provável | Solução |
|----------|----------------|---------|
| Mensagem *"Verifique se o MySQL está ligado..."* ao abrir | MySQL desligado, banco não criado ou senha errada | Ligue o MySQL no XAMPP, rode o `db.sql` e confira o `ConexaoDB.java`. |
| `Unknown database 'ingresso'` | O `db.sql` não foi executado | Importe o `db.sql` pelo phpMyAdmin. |
| `Access denied for user 'root'` | Senha diferente da configurada | Coloque a senha correta em `SENHA` no `ConexaoDB.java`. |
| `http://localhost/phpmyadmin` não abre | Apache desligado | Clique em **Start** ao lado do Apache no XAMPP. |
| MySQL liga e desliga logo em seguida no XAMPP | Outra instalação do MySQL usando a porta 3306 | Pare o outro serviço MySQL (programa **Serviços** do Windows) ou mude a porta. |
| Erro de compilação sobre a versão do Java | JDK anterior ao 21 | Troque `21` pela versão do seu JDK no `pom.xml`. |
| NetBeans não reconhece a pasta como projeto | Foi aberta a pasta errada | Abra a pasta que contém o `pom.xml` diretamente, usando **Open Project**. |
| Falha ao baixar o driver do MySQL | Sem internet na primeira abertura | Conecte-se à internet e use **Clean and Build**. |
| A aba **Design** não aparece | O arquivo `.form` não está ao lado do `.java` | Mantenha `TelaEventos.form` e `TelaVendas.form` na pasta `view`. |

---

## Melhorias futuras

- Editar evento (nome, preço e vagas).
- Tipos de ingresso (inteira e meia).
- Data e horário dos eventos.
- Relatório de vendas por evento.
- Impressão ou exportação do ingresso em PDF.
- Login com perfis (administrador e vendedor).
- Pool de conexões em vez de abrir uma conexão por operação.
