# 04. AWS VPC Networking

## 1. Understanding VPC

**Amazon VPC (Virtual Private Cloud)** provides an isolated virtual network within AWS where cloud resources can be deployed and network communication can be controlled.

A VPC allows you to define and manage things such as:

- IP address ranges
- Subnets
- Routing
- Internet connectivity
- Network access controls

A simplified hierarchy is:

```text
VPC
 │
 ├── Subnet
 │     └── AWS Resources
 │
 └── Subnet
       └── AWS Resources
```

The VPC forms the overall network, while subnets divide it into smaller network segments.

---

# 2. CIDR — Defining the Network Range

**CIDR (Classless Inter-Domain Routing)** is used to specify the IP address range available to a VPC or subnet.

For example:

```text
10.0.0.0/16
```

A VPC might therefore be configured as:

```text
VPC
10.0.0.0/16
```

Its address space can then be divided into smaller subnet ranges:

```text
10.0.1.0/24
10.0.2.0/24
```

The subnet ranges must fit within the CIDR block assigned to the VPC.

### Example

```text
VPC: 10.0.0.0/16
│
├── Subnet A: 10.0.1.0/24
└── Subnet B: 10.0.2.0/24
```

Think of the VPC CIDR as the **overall address space**, while subnet CIDRs are smaller sections carved out of it.

---

# 3. Subnets

A **subnet** is a smaller IP network created inside a VPC.

In AWS, each subnet is associated with an **Availability Zone**.

A simple design might look like:

```text
                 VPC
                  │
        ┌─────────┴─────────┐
        │                   │
 Public Subnet        Private Subnet
        │                   │
      EC2                 Database
```

Subnets are commonly designed as either:

- **Public subnets**
- **Private subnets**

This separation is an important part of building secure AWS architectures.

---

# 4. Route Tables

A **route table** determines how traffic leaving a subnet should be forwarded.

Routes specify a destination and where traffic should go.

For example:

```text
Destination: 0.0.0.0/0
Target: Internet Gateway
```

This effectively means:

> Traffic destined outside the local VPC network should be sent toward the Internet Gateway.

A private workload might instead use a NAT Gateway:

```text
Private Subnet
      │
      ▼
 Route Table
      │
      ▼
 NAT Gateway
      │
      ▼
  Internet
```

---

# 5. Internet Gateway

An **Internet Gateway (IGW)** provides a path between a VPC and the public internet.

A typical public-resource path is:

```text
EC2
 │
 ▼
Route Table
 │
 ▼
Internet Gateway
 │
 ▼
Internet
```

For a subnet to function as a public subnet, its routing configuration generally includes a route toward an Internet Gateway.

The resource itself also needs the appropriate public addressing configuration to communicate with the internet.

---

# 6. NAT Gateway

**NAT stands for Network Address Translation.**

A **NAT Gateway** allows resources located in private subnets to initiate outbound connections to the internet without making those resources directly reachable from the public internet.

For example:

```text
Private EC2
     │
     ▼
NAT Gateway
     │
     ▼
Internet
```

A common use case is allowing a private EC2 instance to download:

- Operating system updates
- Software packages
- Dependencies
- External resources

The private EC2 instance does not need its own public IP for this outbound access.

### Key idea

```text
Private instance → Internet
       ✓ Allowed

Internet → Private instance
       ✗ Not directly initiated through NAT
```

---

# 7. Security Groups

A **Security Group** works as a virtual firewall associated with resources such as EC2 instances.

It controls:

- Inbound connections
- Outbound connections

For example:

```text
Internet
   │
   ▼
Security Group
   │
   ▼
EC2 Instance
```

Typical rules might allow:

```text
SSH    → Port 22
HTTP   → Port 80
HTTPS  → Port 443
```

Security Groups are **stateful**.

This means that when an allowed connection is established, the corresponding return traffic is automatically handled without requiring a separate reverse rule.

---

# 8. Network ACLs

A **Network ACL (NACL)** provides another layer of network traffic control, but it operates at the **subnet level**.

NACL rules can explicitly:

- Allow traffic
- Deny traffic

Unlike Security Groups, NACLs are **stateless**.

Therefore, inbound and outbound traffic need to be considered separately.

### Security Group vs NACL

| Feature | Security Group | Network ACL |
|---|---|---|
| Scope | Resource/instance level | Subnet level |
| Stateful | Yes | No |
| Allow rules | Yes | Yes |
| Deny rules | No explicit deny rules | Yes |
| Traffic direction | Inbound / outbound | Inbound / outbound |

### Easy way to remember

```text
Security Group → "Can this resource communicate?"

NACL → "Can traffic enter or leave this subnet?"
```

---

# 9. Public and Private Subnets

## Public Subnet

A subnet is considered **public** when its route table provides a path to an Internet Gateway.

Conceptually:

```text
Internet
   │
   ▼
Internet Gateway
   │
   ▼
Public Subnet
   │
   ▼
EC2 / Load Balancer
```

Public subnets are commonly used for components that need public-facing connectivity, such as:

- Web servers
- Load balancers
- Bastion hosts

---

## Private Subnet

A **private subnet** does not have a direct route to an Internet Gateway.

It is commonly used for components that should not be directly exposed to the internet.

Examples include:

- Databases
- Internal application services
- Backend systems
- Private workloads

For outbound internet access, a private subnet may use a NAT Gateway:

```text
Private Subnet
      │
      ▼
 NAT Gateway
      │
      ▼
 Internet
```

---

# 10. Typical Three-Tier AWS Network

A common architecture separates public-facing components from internal services.

```text
                    Internet
                       │
                       ▼
               Internet Gateway
                       │
                       ▼
                Public Subnet
                       │
                       ▼
                 Load Balancer
                       │
                       ▼
                Private Subnet
                       │
                       ▼
              Application Servers
                       │
                       ▼
                Private Subnet
                       │
                       ▼
                    Database
```

The important security principle is **network segmentation**.

The load balancer can receive public traffic, while application servers and databases remain inside private network segments.

This reduces the number of resources directly exposed to the internet and creates clear boundaries between different parts of the application.

---

# 11. VPC Components at a Glance

| Component | Main Responsibility |
|---|---|
| VPC | Provides the overall isolated AWS network |
| CIDR | Defines an IP address range |
| Subnet | Divides the VPC into smaller networks |
| Route Table | Determines where network traffic is sent |
| Internet Gateway | Provides internet connectivity for the VPC |
| NAT Gateway | Enables outbound internet access from private resources |
| Security Group | Filters traffic at the resource level |
| NACL | Filters traffic at the subnet level |

### Final Mental Model

```text
                    VPC
                     │
          ┌──────────┴──────────┐
          │                     │
     Public Subnet         Private Subnet
          │                     │
    Load Balancer          Application
          │                     │
          │                     ▼
          │                  Database
          │
    Internet Gateway

Private resources ──► NAT Gateway ──► Internet
```

**The core idea:** VPC networking is about deciding **where resources live, how traffic moves between them, and which connections are allowed**.