# Étape 1 : Build de l'application
FROM maven:3.9.9-openjdk-17 AS build
WORKDIR /app

# Copie du fichier pom.xml et téléchargement des dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie du code source
COPY src ./src

# Compilation du projet sans exécuter les tests
RUN mvn clean package -DskipTests

# Étape 2 : Image finale pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copie du .jar généré dans l'image finale
COPY --from=build /app/target/kaddem-0.0.1-SNAPSHOT.jar app.jar

# Exposition du port utilisé par l'application Spring Boot
EXPOSE 8082

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]
