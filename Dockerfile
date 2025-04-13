FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD http://192.167.33.10:8081/repository/maven-snapshots/tn/esprit/spring/kaddem/0.0.1-SNAPSHOT/kaddem-0.0.1-20250413.221452-1.jar Eyansibi4twin2-1.0.jar
ENTRYPOINT ["java","-jar","/Eyansibi4twin2-1.0.jar"]
