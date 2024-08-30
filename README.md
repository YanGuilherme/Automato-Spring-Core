# Guia de Instalação e Execução do Projeto

## Backend

1. **Instalação de Ferramentas:**
    - Instale o SDKMAN:
      ```bash
      curl -s "https://get.sdkman.io" | bash
      ```
    - Instale o Java 21 (Oracle) ou superior:
      ```bash
      sdk install java 21-oracle
      ```
    - Instale o Maven:
      ```bash
      sdk install maven
      ```
    - Verifique as versões disponíveis de Java:
      ```bash
      sdk list java
      ```
    - Instale a versão desejada do Java usando o identificador específico:
      ```bash
      sdk install java <identifier>
      ```
    - Verifique as versões disponíveis do Maven:
      ```bash
      sdk list maven
      ```
    - Instale a versão desejada do Maven:
      ```bash
      sdk install maven <version>
      ```

2. **Instalação do IntelliJ IDEA:**
    - Baixe e instale o [IntelliJ IDEA](https://www.jetbrains.com/idea/).

3. **Rodando o Backend:**
    - Após tudo estar instalado, execute o seguinte comando no terminal para rodar o backend:
      ```bash
      mvn spring-boot:run
      ```

## Frontend

1. **Instalação de Ferramentas:**
    - Instale o Node.js e npm:
      ```bash
      sudo apt install npm
      ```
    - Instale o http-server via npm:
      ```bash
      npm install -g http-server
      ```

2. **Rodando o Frontend:**
    - Para rodar o servidor HTTP local na porta 9090, use o comando:
      ```bash
      http-server -p 9090
      ```
    - Acesse o frontend no navegador através da URL:
      ```bash
      http://localhost:9090
      ```
