# Assembleia de Votação

### Pré-requisitos
- O Docker deve estar instalado. Veja [como instalar o Docker aqui](https://docs.docker.com/engine/installation/).
- O Docker Compose deve estar instalado. Veja [como instalar o Docker Compose aqui](https://docs.docker.com/compose/install/).

### Passos para execução
1. Clonar o projeto localmente: `git clone https://github.com/mvapoa/section.git`
2. Acessar a raiz do projeto section
3. Empacotar a aplicação: `./gradlew build`
4. Subir os contêineres: `docker-compose up`
5. Visualizar todos os endpoints: [http://localhost:8001/swagger-ui.html](http://localhost:8001/swagger-ui.html)
