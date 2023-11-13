# Etapa 1: Construir la aplicación con Maven
FROM maven:3.6.3-openjdk-8 as build
WORKDIR /app

# Copiar el archivo POM y descargar dependencias
# Copiar archivos de tu proyecto al directorio de trabajo
COPY . /app
RUN mvn dependency:go-offline

# Ejecutar Maven para construir el proyecto
RUN mvn clean package

# Etapa 2: Ejecutar la aplicación en Java 8
FROM openjdk:8-jre-slim
WORKDIR /app

# Copiar el archivo JAR construido en la primera etapa
COPY --from=build /app/target/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar /app/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar

# Exponer el puerto en el que la aplicación escucha
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "spring-boot-security-jwt-0.0.1-SNAPSHOT.jar"]
