FROM eclipse-temurin:11-jdk-alpine
LABEL maintainer="adriano@example.com"

# Variabili d'ambiente
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Crea una directory per l'applicazione
WORKDIR /app

# Copia il jar dell'applicazione
COPY target/*.jar app.jar

# Esponi la porta su cui l'applicazione sarà in ascolto
EXPOSE 8080

# Comando per avviare l'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]