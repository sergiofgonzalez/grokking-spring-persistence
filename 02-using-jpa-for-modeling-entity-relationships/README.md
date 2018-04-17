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

### A2. One-to-One: Tables Sharing the Primary Key

JPA does not provide a direct way to map your entities to implement the one-to-one association in this way. Workarounds are available (like defining the `Email` entity with a non-generated identifier, and manage the associated id for the associated `User` as a regular field) but those feel *hackish* and lead to an overly complicated solution.

![One-to-One: Single Table](../01-representing-object-associations-in-the-db/images/08-one-to-one-multiple-tables.png)

### A3. One-to-One: Distinct Tables

In this scenario you use two distinct tables with the key from one entity maintained by the other entity. This approach requires an extra *unique* constraint placed in the foreign key column that otherwise would allow a one-to-many relationship.

![One-to-One: Single Table](../01-representing-object-associations-in-the-db/images/09-one-to-one-distinct-tables.png)

This type of relationship can be expressed with a `@OneToOne` annotations. This approach is useful if you suspect that the relationship can change in the future from a one-to-one to a one-to-many or many-to-one. Otherwise, the approach on [A1. One-to-One: Mapping an Embedded One-to-One Association](#a1-one-to-one-mapping-an-embedded-one-to-one-association) should be preferred.

#### A) Unidirectional One-to-One Association

In this approach, you select one of the entities (i.e. `User`) as the class *owning* the other class (i.e. `Email`):

![One-to-One: Single Table](images/01-one-to-one-unidirectional.png)

The following listing shows both classes annotated in a way that is consistent with the previous picture:

```java
@Entity
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false)
    private String email;
    
    protected Email() {     
    }
    
...
}
```

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
    
    @OneToOne
    @JoinColumn(unique = true)
    private Email email;
    
    protected User() {      
    }
    
...
```

Note that in this case, the `Email` class is a full-fledged JPA entity, and therefore, can have its own data access repositories.

You can review a working example of this scenario in [002 &mdash; One-to-One: Unidirectional](002-one-to-one-unidirectional/).