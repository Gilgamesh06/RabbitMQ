FROM openjdk:17-jdk-alpine

# Argumento para el nombre del archivo JAR
ARG JAR_FILE=agregarLibro/target/agregarlibro.jar

# Copia el JAR al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto en el que corre SpringBoot (por ejemplo, 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "env && java -jar /app.jar"]

