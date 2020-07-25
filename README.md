Movie Storage Backend

## Prerequisites
- Java 1.8 or Greater
- Maven 3 or Greater
- MySQL 5 (Not for test)

## Build
``` mvn clean build```

## Run Tests
``` mvn test ```
## Run
```mvn spring-boot:run```

## Notes

- Test coverage is more than 90%.
- Code Stack is Spring Boot, Maven, MySQL, H2(for tests),
Hibernate provided by Spring Data JPA, JUnit5, Liquibase.
- [Sample Input CSV File](src/test/resources/movies.csv)
- Example of APIs can be found in [Component Tests](src/test/java/com/product/backend/component/MovieControllerTest.java).