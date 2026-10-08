# 03. Amazon S3 — Object Storage

## 1. Introduction to S3

**Amazon S3 (Simple Storage Service)** is AWS's object storage service.

It is designed for storing and retrieving large amounts of data without managing traditional disks or file servers.

S3 can store many types of data, including:

- Images and videos
- Documents
- Application assets
- Backup files
- Logs
- Data archives

S3 is designed to provide high scalability and durability.

A simplified interaction looks like:

```text
Application
     │
     ▼
 S3 Bucket
     │
     ▼
   Objects
```

---

# 2. Buckets — The Top-Level Containers

A **bucket** is a logical container in which S3 objects are stored.

For example:

```text
company-data
│
├── images/
├── documents/
├── backups/
└── logs/
```

A bucket name must be **globally unique across Amazon S3**.

Buckets are created in a specific AWS Region.

### Mental model

Think of a bucket as the main storage container, while the objects inside it are the actual pieces of data.

---

# 3. Objects — The Data Stored in S3

An **object** is the actual item stored inside a bucket.

An object consists of things such as:

- The data itself
- A key
- Metadata

For example:

```text
Bucket:
my-app-data

Object key:
images/profile.jpg
```

The structure can be understood as:

```text
Bucket
  │
  └── images/profile.jpg
             │
             └── Object data
```

Here:

```text
Bucket = my-app-data
Key    = images/profile.jpg
Data   = contents of the image
```

The key acts as the object's identifier within the bucket.

---

# 4. S3 Storage Classes

Not every object is accessed with the same frequency.

S3 therefore provides different **storage classes**, allowing you to balance access patterns, performance, and storage cost.

Some commonly encountered classes include:

- S3 Standard
- S3 Intelligent-Tiering
- S3 Standard-IA
- S3 One Zone-IA
- S3 Glacier Instant Retrieval
- S3 Glacier Flexible Retrieval
- S3 Glacier Deep Archive

A simplified selection strategy is:

```text
Frequently accessed
        │
        ▼
   S3 Standard

Less frequently accessed
        │
        ▼
   Standard-IA

Long-term archival
        │
        ▼
   Glacier classes
```

### Example

A company's active application images might use **S3 Standard**, while old compliance records could be moved to **S3 Glacier Deep Archive**.

The appropriate class depends on how often the data needs to be accessed and the required retrieval characteristics.

---

# 5. Object Versioning

**S3 Versioning** allows multiple versions of the same object to be retained.

For example:

```text
config.json
   │
   ├── Version 1
   ├── Version 2
   └── Version 3
```

Suppose an administrator accidentally overwrites an important configuration file.

With versioning enabled, an earlier version may still be available for recovery.

Versioning is particularly useful when protection against:

- Accidental deletion
- Accidental overwrites
- Application mistakes

is important.

---

# 6. Lifecycle Management

S3 **Lifecycle rules** automate what happens to objects as they become older.

For example:

```text
New object
    │
    ▼
S3 Standard
    │
    │ after 30 days
    ▼
S3 Standard-IA
    │
    │ after 90 days
    ▼
S3 Glacier
```

Lifecycle rules can be used to:

- Move objects to cheaper storage classes
- Expire old objects
- Automatically remove unnecessary data

This is especially useful for data such as:

- Application logs
- Old backups
- Temporary files
- Historical records
- Archived datasets

Instead of manually moving files, the lifecycle configuration handles the transition automatically.

---

# 7. Protecting S3 Data with Encryption

Encryption helps protect objects stored in S3.

S3 supports several server-side encryption approaches, including:

- **SSE-S3**
- **SSE-KMS**
- **SSE-C**

A simplified flow is:

```text
Application
     │
     ▼
Object
     │
     ▼
Encryption
     │
     ▼
S3
```

The appropriate encryption option depends on the security and key-management requirements of the workload.

For example, organizations that need centralized control and auditing of encryption keys may use **AWS KMS** with SSE-KMS.

---

# 8. Bucket Policies

A **bucket policy** is a JSON-based resource policy attached directly to an S3 bucket.

It can define which principals are allowed or denied access to the bucket and which S3 operations they can perform.

For example:

```text
Identity
   │
   ▼
Bucket Policy
   │
   ▼
S3 Bucket
```

A policy can control actions such as:

```text
s3:GetObject
s3:PutObject
s3:DeleteObject
```

A simplified policy might effectively say:

```text
Allow → GetObject
Resource → Objects in a specific bucket
```

### Security consideration

S3 buckets should **not be made publicly accessible unless public access is genuinely required**.

For most private application data, access should be restricted to the identities and services that actually need it.

---

# 9. S3 in Real Applications

## Static Website Hosting

S3 can store static website files such as:

```text
HTML
CSS
JavaScript
Images
```

Conceptually:

```text
User
 │
 ▼
S3
 │
 ├── index.html
 ├── style.css
 └── app.js
```

---

## Application File Storage

Applications can use S3 for user-generated or application-related files.

```text
Application
     │
     ▼
S3 Bucket
     │
     ├── Images
     ├── Documents
     └── Uploads
```

This prevents the application server from having to store every uploaded file locally.

---

## Backups

Applications can store backup files in S3:

```text
Application
     │
     ▼
Backup
     │
     ▼
S3
```

Lifecycle rules can later move older backups into lower-cost archival storage.

---

## Log Storage

AWS services and applications can also send logs to S3.

```text
AWS Services / Applications
          │
          ▼
      S3 Bucket
          │
          ▼
      Log Storage
```

S3's scalability makes it useful for retaining large quantities of historical logs.

---

# 10. S3 — Quick Reference

| Concept | Purpose |
|---|---|
| S3 | AWS object storage service |
| Bucket | Container for objects |
| Object | Actual stored data |
| Object Key | Identifier/path-like name of an object |
| Storage Class | Determines storage characteristics and cost |
| Versioning | Keeps previous object versions |
| Lifecycle Rule | Automates transitions/deletion |
| Encryption | Protects stored data |
| Bucket Policy | Controls access to bucket resources |

### Simple mental model

```text
                 S3
                  │
             ┌────┴────┐
             │ Bucket  │
             └────┬────┘
                  │
        ┌─────────┼─────────┐
        ▼         ▼         ▼
      Object    Object    Object
        │
        ▼
  Storage Class
        │
        ▼
 Lifecycle / Encryption
```

**In short: S3 provides scalable object storage, buckets organize the storage, objects contain the data, storage classes optimize cost and access patterns, and features such as versioning, lifecycle rules, encryption, and policies help manage and protect that data.**