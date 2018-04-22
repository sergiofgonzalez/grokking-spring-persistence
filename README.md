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
  + [A2. One-to-One: Tables Sharing the Primary Key](02-using-jpa-for-modeling-entity-relationships/README.md#a2-one-to-one-tables-sharing-the-primary-key)
  + [A3. One-to-One: Distinct Tables](02-using-jpa-for-modeling-entity-relationships/README.md#a3-one-to-one-distinct-tables) 
    + [A) Unidirectional One-to-One Association](02-using-jpa-for-modeling-entity-relationships/README.md#a-unidirectional-one-to-one-association)
      + [Sample 2 &mdash; One-to-One: Unidirectional](02-using-jpa-for-modeling-entity-relationships/002-one-to-one-unidirectional)
    + [B) Bidirectional One-to-One Association](02-using-jpa-for-modeling-entity-relationships/README.md#b-bidirectional-one-to-one-association)
      + [Sample 3 &mdash; One-to-One: Bidirectional](02-using-jpa-for-modeling-entity-relationships/003-one-to-one-bidirectional)
+ [B. Mapping One-to-Many and Many-to-One Associations](02-using-jpa-for-modeling-entity-relationships/README.md#b-mapping-one-to-many-and-many-to-one-associations)
  + [B1. One-to-Many: Distinct Tables](02-using-jpa-for-modeling-entity-relationships/README.md#b1-one-to-many-distinct-tables)
    + [A) Unidirectional One-to-Many Association](02-using-jpa-for-modeling-entity-relationships/README.md#a-unidirectional-one-to-many-association)
      + [Sample 4 &mdash; One-to-Many: Unidirectional](02-using-jpa-for-modeling-entity-relationships/004-one-to-many-unidirectional)      
    + [B) Bidirectional One-to-Many Association](02-using-jpa-for-modeling-entity-relationships/README.md#b-bidirectional-one-to-many-association)
      + [Sample 5 &mdash; One-to-Many: Bidirectional](02-using-jpa-for-modeling-entity-relationships/005-one-to-many-bidirectional)
  + [B2. One-to-Many: Link Tables](02-using-jpa-for-modeling-entity-relationships/README.md#b2-one-to-many-link-tables)
    + [A) Unidirectional One-to-Many Association with link table](02-using-jpa-for-modeling-entity-relationships/README.md#a-unidirectional-one-to-many-association-with-link-table)
      + [Sample 6 &mdash; One-to-Many: Unidirectional Link Table](02-using-jpa-for-modeling-entity-relationships/006-one-to-many-unidirectional-link-table)
    + [B) Unidirectional Many-to-One Association with link table](02-using-jpa-for-modeling-entity-relationships/README.md#b-unidirectional-many-to-one-association-with-link-table)
      + [Sample 7 &mdash; Many-to-One: Unidirectional Link Table](02-using-jpa-for-modeling-entity-relationships/007-many-to-one-unidirectional-link-table)  
    + [C) Bidirectional One-to-Many Association with link table](02-using-jpa-for-modeling-entity-relationships/README.md#c-bidirectional-one-to-many-association-with-link-table)
      + [Sample 8 &mdash; One-to-Many: Bidirectional Link Table](02-using-jpa-for-modeling-entity-relationships/008-one-to-many-bidirectional-link-table)

+ [C. Mapping Many-to-Many Associations](02-using-jpa-for-modeling-entity-relationships/README.md#c-mapping-many-to-many-associations)
  + [C1. Many-to-Many: Link Table](02-using-jpa-for-modeling-entity-relationships/README.md#c1-many-to-many-link-table)
    + [A) Unidirectional Many-to-Many Association: User as Owner](02-using-jpa-for-modeling-entity-relationships/README.md#a-unidirectional-many-to-many-association-user-as-owner)
      + [Sample 9 &mdash; Many to Many Unidirectional Link Table (user owning the relationship)](02-using-jpa-for-modeling-entity-relationships/009-many-to-many-unidirectional-link-table-user-owns)
    + [B) Unidirectional Many-to-Many Association: Email as Owner](02-using-jpa-for-modeling-entity-relationships/README.md#b-unidirectional-many-to-many-association-email-as-owner)
      + [010 &mdash; Many-to-Many: Unidirectional Link Table (email owning the relationship)](02-using-jpa-for-modeling-entity-relationships/010-many-to-many-unidirectional-link-table-email-owns/).]
    + [C) Bidirectional Many-to-Many Association (with owner)](02-using-jpa-for-modeling-entity-relationships/README.md#c-bidirectional-many-to-many-association-with-owner)
      + [011 &mdash; Many-to-Many: Bidirectional Link Table (with owner)](02-using-jpa-for-modeling-entity-relationships/011-many-to-many-bidirectional-link-table-with-owner/).]
    + [D) Bidirectional Many-to-Many Association (without owner)](02-using-jpa-for-modeling-entity-relationships/README.md#d-bidirectional-many-to-many-association-without-owner)
      + [012 &mdash; Many-to-Many: Bidirectional Link Tables (without owner)](02-using-jpa-for-modeling-entity-relationships/012-many-to-many-bidirectional-link-tables-without-owner/)
