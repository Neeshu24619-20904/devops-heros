# 05. AWS Database Services — DynamoDB & RDS

AWS provides different database services for different application requirements. Two important options are **DynamoDB**, a managed NoSQL database, and **Amazon RDS**, a managed relational database service.

---

# Part A — DynamoDB

## 1. What is DynamoDB?

**Amazon DynamoDB** is a fully managed **NoSQL database service** offered by AWS.

It is designed for applications that require large-scale data handling with consistently low latency.

Key characteristics include:

- Fully managed by AWS
- Serverless
- Highly scalable
- Low-latency performance
- No traditional database-server management

Unlike a traditional relational database, DynamoDB uses a flexible NoSQL data model rather than requiring every record to follow the same fixed set of columns.

---

## 2. DynamoDB Tables

A **table** is the main container used to store data in DynamoDB.

For example:

```text
Users
 │
 ├── Item 1
 ├── Item 2
 └── Item 3
```

A table can contain a very large number of items, depending on the application's requirements.

---

## 3. Items

An **item** represents one individual record stored inside a DynamoDB table.

For example:

```json
{
  "userId": "101",
  "name": "Abhijit",
  "email": "user@example.com"
}
```

An item is conceptually similar to a **row** in a relational database, although the underlying data model is different.

---

## 4. Attributes

The individual pieces of information contained within an item are called **attributes**.

For example:

```text
userId
name
email
```

One important characteristic of DynamoDB is its flexible schema.

Different items in the same table can contain different attributes when the application's data model requires it.

---

## 5. Partition Key

The **partition key** is used by DynamoDB to determine how an item is distributed and located within the database.

For example:

```text
userId = 101
```

If `userId` is selected as the partition key, DynamoDB uses its value when determining the item's storage location.

A well-designed partition key should distribute requests and data effectively rather than concentrating most traffic on a small number of partition-key values.

### Simple idea

```text
Application
     │
     ▼
Partition Key
     │
     ▼
DynamoDB determines storage location
```

---

## 6. Sort Key

A **sort key** is optional and can be combined with a partition key.

It allows multiple related items to share the same partition-key value while being distinguished and organized using another attribute.

Example:

```text
UserID    OrderID
-----------------
101       001
101       002
101       003
```

Here:

```text
Partition Key = UserID
Sort Key      = OrderID
```

Together, these form a **composite primary key**.

This design is useful when an application needs to efficiently retrieve multiple related records belonging to the same partition.

---

## 7. DynamoDB Architecture Example

A simplified application flow looks like:

```text
Application
     │
     ▼
 DynamoDB
     │
     ▼
   Items
```

DynamoDB is particularly useful when applications need predictable performance at large scale without managing database servers.

### Common use cases

- User session storage
- Shopping carts
- Gaming systems
- IoT workloads
- Real-time applications
- Large-scale web applications

---

# Part B — Amazon RDS

## 8. What is Amazon RDS?

**RDS stands for Relational Database Service.**

Amazon RDS is a managed AWS service that makes it easier to deploy, operate, and maintain relational databases.

AWS handles many routine administrative responsibilities, including:

- Database setup
- Automated backups
- Software patching
- Maintenance operations

This allows developers to focus more on the application and database itself instead of managing the underlying infrastructure.

---

# 9. Relational Databases

A relational database organizes information into **tables containing rows and columns**.

For example:

```text
Users
--------------------------------
id     name       email
--------------------------------
1      John       john@...
2      Alex       alex@...
```

Relational databases use **SQL** and are designed to represent relationships between different tables.

For example:

```text
Users
   │
   ├── Orders
   │
   └── Addresses
```

This makes relational databases a good choice when structured data and relationships are important.

---

# 10. Database Engines Supported by RDS

Amazon RDS supports several popular relational database engines, including:

- Amazon Aurora
- PostgreSQL
- MySQL
- MariaDB
- Oracle
- Microsoft SQL Server

The engine should be selected according to factors such as:

- Application compatibility
- SQL features
- Existing technology stack
- Performance requirements
- Licensing requirements

---

# 11. RDS DB Instance

An **RDS DB instance** provides the compute environment on which the selected database engine runs.

Its configuration determines resources such as:

- CPU
- Memory
- Network performance
- Storage capacity and performance

For example:

```text
Application
     │
     ▼
RDS DB Instance
     │
     ▼
PostgreSQL
```

The database engine and instance configuration together provide the environment required by the application.

---

# 12. Securing an RDS Database

RDS can be protected using several AWS security mechanisms.

Common controls include:

- VPC networking
- Security Groups
- IAM
- Encryption
- Database authentication

A common architecture places the database inside a **private subnet**:

```text
Application
     │
     ▼
Private Network
     │
     ▼
RDS Database
```

This prevents the database from being directly exposed to the public internet.

Applications should normally communicate with the database through controlled network access rather than making the database publicly accessible.

---

# 13. RDS Backups and Snapshots

RDS supports **automated backups** as well as manually created **DB snapshots**.

These mechanisms can be useful for:

- Recovering from failures
- Restoring a database
- Recovering from accidental changes
- Creating a point-in-time recovery strategy

A simplified recovery flow is:

```text
        RDS
         │
         ▼
      Backup
         │
         ▼
      Restore
```

Backups are an important part of protecting production databases against data loss.

---

# 14. Multi-AZ Deployment

**Multi-AZ** is primarily designed to improve database **availability**.

In a Multi-AZ configuration, AWS maintains a standby database in another Availability Zone.

Conceptually:

```text
Availability Zone A
        │
        ▼
   Primary RDS
        │
        │ replication
        ▼
Availability Zone B
        │
        ▼
   Standby RDS
```

If the primary database becomes unavailable, RDS can perform a failover to the standby environment.

### Important distinction

Multi-AZ is primarily about **high availability and failover**.

It should not be confused with read replicas, which are primarily used to help handle read workloads.

---

# 15. Read Replicas

A **Read Replica** is a separate copy of a database that can be used to handle read operations.

A simplified architecture is:

```text
                Application
                    │
                    ▼
               Primary RDS
                    │
             ┌──────┴──────┐
             ▼             ▼
        Write Traffic   Read Replica
                           │
                           ▼
                      Read Traffic
```

Read replicas can help applications that have a large number of read requests by distributing read workloads away from the primary database.

### Remember

```text
Multi-AZ     → Availability / Failover

Read Replica → Read Scaling
```

---

# 16. Common RDS Applications

RDS is a suitable choice for applications that depend on relational data and SQL.

Typical examples include:

- Web applications
- Backend APIs
- E-commerce platforms
- Business applications
- Systems requiring structured relationships

For example:

```text
Application
     │
     ▼
RDS PostgreSQL
     │
     ├── Users
     ├── Orders
     └── Products
```

---

# 17. DynamoDB vs RDS

| DynamoDB | Amazon RDS |
|---|---|
| NoSQL database | Relational database |
| Key-value / document model | Tables with rows and columns |
| Serverless | Managed database instances |
| Does not use traditional SQL as its primary data model | Uses SQL |
| Designed for high-scale, low-latency workloads | Suitable for relational workloads |
| Flexible item structure | Structured relational schema |
| Scaling is largely handled by the service | Scaling involves database instance/storage configuration and supported scaling options |

---

# 18. Quick Decision Guide

A simple way to think about the two services:

```text
Need relational data + SQL?
          │
          ▼
        RDS

Need highly scalable NoSQL
with low-latency access?
          │
          ▼
      DynamoDB
```

### Final mental model

**DynamoDB** is primarily about **scalable NoSQL data access without managing database servers**.

**RDS** is primarily about **running managed relational databases while AWS handles much of the operational work**.

The right choice depends on the application's data model, access patterns, scaling requirements, and operational needs.