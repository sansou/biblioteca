# Etapa 1: Build do Projeto
FROM maven:3.9.5-eclipse-temurin-21 

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o contêiner
COPY . .

# Executa o build do projeto usando Maven
RUN mvn clean package -DskipTests 

ENTRYPOINT [ "/app/mvnw","spring-boot:run" ] 

# Expõe a porta padrão do Spring Boot
EXPOSE 8080
