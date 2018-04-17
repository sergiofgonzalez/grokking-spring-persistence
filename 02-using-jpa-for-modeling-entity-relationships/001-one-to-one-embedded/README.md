# 001 &mdash; One-to-One: Embedded

> One-to-One association using `@Embedded` and represented by a single table in the database

## Description

In the example, we define a couple of domain classes `User` and `Email` featuring a *one-to-one* association.
The classes are annotated in a way that a single table `user` will be used to hold both users and emails.

The application tests include a test harness demonstrating how to query *User* and *Email* objects using Spring Data JPA framework.

## Relevant Project Artifacts

+ `domain/` &mdash; contains the definition of the Entities
+ `repositories/` &mdash; features the *Spring Data* repository that will let you interact with the database
+ `src/main/resources/application.yml` &mdash; the application properties. Make sure to adapt the properties to match your environment (e.g. `spring.datasource.url`)
+ `src/main/resources/sql` &mdash; *DDL* and *DML* scripts for the tables in play
+ `src/test/java` &mdash; test harness for the domain model and repositories