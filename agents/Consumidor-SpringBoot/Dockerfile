FROM openjdk:17-jdk-alpine

# Argumento para el nombre del archivo JAR
ARG JAR_FILE=bibliotecaMalaga/target/backend-malaga.jar

# Copia el JAR al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto en el que corre SpringBoot (por ejemplo, 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]

