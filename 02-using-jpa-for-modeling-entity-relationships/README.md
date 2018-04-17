# Grokking Spring Persistence: Using JPA for modeling Entity Relationships

> annotating your Java classes for modeling Entity Relationships

+ Annotating one-to-one associations
+ Annotating one-to-many and many-to-one-associations
+ Annotating many-to-many associations

## Introduction

An application needs a persistence layer to maintain information its state in a database. One way to achieve that is through metadata annotations on your domain model classes. Those annotation will then be interpreted by the Java Runtime Environment (JRE) and the JPA (Java Persistence API) framework to effectively map the relationship between your objects and the tables in the relational database.

## A. Mapping One-to-One Associations

Let's consider the scenario for a one-to-one relationship between a User and an Email:

![User-Email One-to-One](../01-representing-object-associations-in-the-db/images/06-user-email-one-to-one.png)


### A1. One-to-One: Mapping an Embedded One-to-One Association

An embedded one-to-one association uses one table to represent both entities:

![One-to-One: Single Table](../01-representing-object-associations-in-the-db/images/07-one-to-one-single-table.png)

When all the fields of one entity are maintained within the same table as another, the enclosed entity is referred to as an embedded entity in JPA terminology.

The `@Embedded` annotation is used to manage this relationship, and it requires the following conditions to be met:
+ An embeddable entity must be composed entirely of basic fields and attributes (no classes involved)
+ An embeddable entity can only used the `@Basic`, `@Column`, `@Lob`, `@Temporal` and `@Enumerated`. An embeddable entity can also make use of the `@Embedded` annotations.
+ An embeddable entity cannot maintain its own primary key, because its primary key is the primary key of the enclosing entity.

The following listings show the `User` and `Email` classes annotated so that the supporting database representation is a single table representing the one-to-one relationship between the domain model classes:

 ```java
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false)
    private String username;
    
    @Column(length = 50, nullable = false)
    private String password;
    
    @Embedded
    private Email email;
    
    protected User() {      
    }
    
...
}
 ```

```java
public class Email {

    protected Email() {     
    }
    
    public Email(String email) {
        this.email = email;
    }
    
    @Column(length = 50, nullable = false)
    private String email;
    
    public String getEmail() {
        return email;
    }
...
}
```

Note that:
+ the `@Embedded` annotation is attached to the `email` field to instruct the JPA framework to include the fields of the `Email` class as columns in the `user` table.  
+ the `Email` class does not require any JPA annotations at the class level.

You can review a working example of this scenario in [001 &mdash; One-to-One: Embedded](001-one-to-one-embedded/).