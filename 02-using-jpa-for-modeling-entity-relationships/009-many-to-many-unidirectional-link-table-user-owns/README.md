# 009 &mdash; Many-to-Many: Unidirectional with a link table (user owning the relationship)

> Many-to-Many unidirectional association represented by two distinct tables and a link table in the database. A user is not allowed to have the same Email twice.

## Description

In the example, we define a couple of domain classes `User` and `Email` featuring a *many-to-many* association navigable from the `User` to the associated `Email` instances.

In this example, an additional constraint is defined to prevent a `User` to have the same `Email` more than once.

The application tests include a test harness demonstrating how to query *User* and *Email* objects using Spring Data JPA framework.

## How to Run the Project

1. Create a sample database for the project
```sql
CREATE DATABASE `spring_persistence` /*!40100 COLLATE 'utf8_bin' */
```

2. Configure the datasource for your working environment:
```yaml
spring:
  datasource:
    # url, username and password: configure the connection string and parameters for your environment 
    url: jdbc:mysql://java_dev_db:3306/spring_persistence?autoReconnect=true&useSSL=false
    username: theuser
    password: thepass
    
    continue-on-error: false # Forcefully stop if problem found while initializing the db
```

3. Explore and Run the test classes found in `src/test/java`

## Relevant Project Artifacts

+ `domain/` &mdash; contains the definition of the Entities
+ `repositories/` &mdash; features the *Spring Data* repository that will let you interact with the database
+ `src/main/resources/application.yml` &mdash; the application properties. Make sure to adapt the properties to match your environment (e.g. `spring.datasource.url`)
+ `src/main/resources/sql` &mdash; *DDL* and *DML* scripts for the tables in play
+ `src/test/java` &mdash; test harness for the domain model and repositories