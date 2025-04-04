# Usar una imagen base con JDK 8 y Gradle
FROM gradle:8.11-jdk21 AS build

# Establecer un directorio de trabajo
WORKDIR /app

# Copiar archivos de tu proyecto al directorio de trabajo
COPY . /app

# Ejecutar Gradle para construir el proyecto
RUN gradle clean build

# Crear una nueva imagen basada en OpenJDK 8
FROM openjdk:21-jdk-slim

# Exponer el puerto que utilizará la aplicación
EXPOSE 8080

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /app/build/libs/HistorialPlus-0.0.1-SNAPSHOT.jar /app/HistorialPlus-0.0.1-SNAPSHOT.jar

# Establecer el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/HistorialPlus-0.0.1-SNAPSHOT.jar"]