# 01. AWS IAM — Identity & Access Governance

## 1. IAM Overview

**IAM (Identity and Access Management)** is the AWS service used to control access to resources within an AWS environment.

It answers four basic questions:

```text
Who is requesting access?
        ↓
What resource are they accessing?
        ↓
What operation are they trying to perform?
        ↓
Under what conditions is it permitted?
```

IAM is a **global AWS service**.

The main building blocks are:

- **Users** — individual identities
- **Groups** — collections of users
- **Roles** — identities that provide temporary credentials
- **Policies** — documents describing permissions
- **Permissions** — actions an identity is allowed or denied to perform

---

# 2. IAM Users

An IAM user represents a specific identity that requires AWS access.

A user can be configured with credentials such as:

- Console login credentials
- Access keys
- Permissions through policies or groups

A simplified example:

```text
Developer
    │
    ▼
IAM User
    │
    ▼
AWS Resources
```

Historically, IAM users were commonly used for long-term credentials. However, for human users, AWS generally recommends using **temporary credentials through IAM Identity Center** rather than relying on long-lived IAM user credentials.

IAM users can still be appropriate for specific situations where a persistent identity is required.

---

# 3. IAM Groups

An IAM group is a way of organizing multiple IAM users.

Instead of attaching the same permissions separately to every developer, permissions can be assigned to a group.

For example:

```text
              Developers Group
              /       |       \
             /        |        \
         Alice       Bob      Charlie
             \        |        /
              Shared Permissions
```

Suppose the group has:

```text
S3 → ReadOnly
EC2 → ReadOnly
```

Any user who belongs to that group receives those permissions.

### Why groups are useful

Groups make access management easier because they:

- Reduce repeated policy assignments
- Simplify permission management
- Make onboarding and offboarding easier
- Provide a consistent permission structure for teams

One important restriction:

> **IAM groups cannot contain other IAM groups.**

---

# 4. IAM Roles

An IAM role is an identity that can provide **temporary permissions** to whoever or whatever assumes it.

Unlike an IAM user, a role normally does not have its own permanent username/password or long-lived access keys.

A role can be assumed by entities such as:

- AWS services
- Applications
- IAM users
- Identities from another AWS account

### Example: EC2 + S3

Suppose an application running on EC2 needs to download objects from S3.

A poor design would be:

```text
EC2
 │
 ├── Access Key
 └── Secret Key
       │
       ▼
      S3
```

The credentials would have to be stored and protected by the application.

A better architecture is:

```text
EC2
 │
 ▼
IAM Role
 │
 ▼
S3 Permissions
```

The EC2 instance receives temporary credentials associated with the role.

This avoids embedding long-lived AWS credentials directly inside the application.

---

# 5. IAM Policies

A **policy** is a JSON document that describes what actions are allowed or denied.

For example:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::my-bucket/*"
    }
  ]
}
```

This policy allows:

```text
Action:
s3:GetObject

Resource:
Objects inside my-bucket
```

In other words:

```text
Identity
   │
   ▼
Policy
   │
   ├── Effect → Allow
   ├── Action → s3:GetObject
   └── Resource → my-bucket objects
```

---

# 6. Important Policy Components

An IAM policy can contain several important elements.

### Effect

Specifies whether the request should be:

```text
Allow
```

or

```text
Deny
```

### Action

Specifies the AWS operation being controlled.

Examples:

```text
s3:GetObject
s3:PutObject
ec2:StartInstances
```

### Resource

Identifies the AWS resource to which the permission applies.

For example:

```text
arn:aws:s3:::my-bucket/*
```

### Condition

Adds additional requirements that must be satisfied before the permission applies.

For example, a policy can restrict access based on particular circumstances or request attributes.

### Principal

Identifies the entity that is making a request in policy contexts where a principal is specified, such as resource-based policies.

> **Note:** Principal is not generally an element of an identity-based IAM policy. It is