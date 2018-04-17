# Grokking Spring Persistence

> Understanding persistence concepts with the Spring Boot framework

## [Part 01 &mdash; Representing Object Associations in the Database](01-representing-object-associations-in-the-db/) 
> Understanding the different ways in which you can represent the relationships between classes in a relational database
+ [Introduction](01-representing-object-associations-in-the-db/README.md#introduction)
+ [The "Association Type Predictor" Method](01-representing-object-associations-in-the-db/README.md#the-association-type-predictor-method)
+ [Introduction](01-representing-object-associations-in-the-db/README.md#introduction)
+ [A. The One-to-One Association](01-representing-object-associations-in-the-db/README.md#a-the-one-to-one-association)
  + [A1. One-to-One: Single Table](01-representing-object-associations-in-the-db/README.md#a1-one-to-one-single-table)
  + [A2. One-to-One: Tables Sharing the Primary Key](01-representing-object-associations-in-the-db/README.md#a2-one-to-one-tables-sharing-the-primary-key)
  + [A3. One-to-One: Distinct Tables](01-representing-object-associations-in-the-db/README.md#a3-one-to-one-distinct-tables)        
+ [B. The One-to-Many Association](01-representing-object-associations-in-the-db/README.md#b-the-one-to-many-and-many-to-one-association)
  + [B1. One-to-Many: Distinct Tables](01-representing-object-associations-in-the-db/README.md#b1-one-to-many-distinct-tables)
  + [B2. One-to-Many: Link Table](01-representing-object-associations-in-the-db/README.md#b2-one-to-many-link-table)    
+ [C. The Many-to-Many Association](01-representing-object-associations-in-the-db/README.md#c-the-many-to-many-association)
  + [C1. Many-to-Many: Link Table](01-representing-object-associations-in-the-db/README.md#c1-many-to-many-link-table)
  + [C2. Many-to-Many: Link Table with Primary Key](01-representing-object-associations-in-the-db/README.md#c2-many-to-many-link-table-with-primary-key)
  
## [Part 02 &mdash; Using JPA for Modeling Entity Relationships](02-using-jpa-for-modeling-entity-relationships/) 
> Annotating your classes to declare the persistence relationships in your domain model 

+ [Introduction](02-using-jpa-for-modeling-entity-relationships/README.md#introduction)
+ [A. Mapping One-to-One Associations](02-using-jpa-for-modeling-entity-relationships/README.md#a-mapping-one-to-one-associations)
  + [A1. One-to-One: Mapping an Embedded One-to-One Association](02-using-jpa-for-modeling-entity-relationships/README.md#a1-one-to-one-mapping-an-embedded-one-to-one-association)
    + [Sample 1 &mdash; One-to-One: Embedded](02-using-jpa-for-modeling-entity-relationships/001-one-to-one-embedded)
