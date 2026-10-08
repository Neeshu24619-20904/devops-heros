# 02. AWS EC2 — Compute Fundamentals

## 1. Understanding EC2

**Amazon EC2 (Elastic Compute Cloud)** is AWS's service for running virtual machines in the cloud.

Instead of purchasing and maintaining a physical server, you can launch an EC2 instance and use it as a virtual server.

An EC2 instance can be used to:

- Deploy applications
- Host websites
- Run backend services and APIs
- Execute background jobs
- Process computational workloads
- Run development environments

A simple architecture is:

```text
AWS Cloud
    │
    ▼
EC2 Instance
    │
    ▼
Application
```

The amount of CPU, memory, networking, and storage can be selected according to the workload.

---

# 2. AMI — The Starting Template

An **AMI (Amazon Machine Image)** is a template from which EC2 instances are launched.

An AMI can contain things such as:

- Operating system
- Pre-installed software
- System configuration
- Required application files

For example:

```text
Ubuntu AMI
     │
     ▼
EC2 Instance
     │
     ▼
Ubuntu Server
```

AWS provides ready-to-use images including:

- Amazon Linux
- Ubuntu
- Windows Server

You can also create a **custom AMI** after configuring a server, allowing future EC2 instances to start from the same baseline.

### Mental model

Think of an AMI as a **blueprint** and the EC2 instance as the **machine created from that blueprint**.

---

# 3. Choosing an EC2 Instance Type

The **instance type** determines the computing resources available to an EC2 instance.

Important characteristics include:

- Number and type of CPU cores
- Amount of RAM
- Network performance
- Storage capabilities/performance

Different workloads need different hardware profiles.

For example:

```text
t3.micro
   ↓
Small / lightweight workloads

t3.medium
   ↓
More CPU + memory

c7g
   ↓
Compute-intensive workloads

r7g
   ↓
Memory-heavy workloads
```

### Major EC2 families

| Category | Designed for |
|---|---|
| General Purpose | Balanced