# arithmeticCalculator

This project has been built using:

- Java 17 
- Gradle
- Spring Boot

It also uses docker in order to raise the database container. To run locally:

1. Download Java 17
2. Run the command ```docker-compose up``` in the root directory with doctor damon running. This will start the postgres server.
3. Run the migrations using the command ```./gradlew flywayMigrate```.
4. Finally, starts the application using ```./gradlew bootRun```.

After that, the swagger will be accessible at the address ```localhost:8080/swagger-ui/index.html```.