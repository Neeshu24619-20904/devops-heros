# Session 19: Cloud + Terraform (VPC)

Combines cloud networking fundamentals with Terraform: a real VPC built, planned, applied, and destroyed as code.

## Topics Covered

| Folder | Topic |
|---|---|
| `01-cloud-service-models/` | IaaS / PaaS / SaaS |
| `02-regions-and-availability-zones/` | Regions, AZs |
| `03-vpc-and-subnets/` | VPC (`10.0.0.0/16`), subnets, CIDR |
| `04-route-tables-and-internet-gateway/` | Routing, IGW |
| `05-security-groups/` | Firewall rules (who can reach EC2) |
| `06-terraform-vpc/` | Lab: VPC + public subnet + IGW + route table + association + SG (no EC2, avoids charges) |
| `07-terraform-workflow/` | `init / fmt / validate / plan / apply`, plus `destroy` |
| `08-mini-project/` | Capstone: VPC `10.20.0.0/16` + public subnet `10.20.1.0/24` + IGW + route table + web SG |

## Architecture (lab + mini-project)

```text
Internet
   |
Internet Gateway
   |
VPC (10.0.0.0/16 lab, 10.20.0.0/16 mini-project)
   |
Public Subnet (10.0.1.0/24 lab, 10.20.1.0/24 mini-project)
   |
Route Table (+ association) + Security Group
```

EC2/S3 attach to this network in later work; state handling below applies to all of it.

## State

- Local `terraform.tfstate` tracks the real VPC/subnet/IGW/SG IDs.
- Inspect: `terraform state list`, `terraform state show <addr>`.
- Never hand-edit state; use `plan`/`apply`/`destroy`. Remote backends (e.g. S3 + DynamoDB lock) are the production next step.

## Workflow (plan / apply / destroy)

```bash
terraform init
terraform fmt
terraform validate
terraform plan      # review VPC/subnet/IGW/SG to add
terraform apply     # type "yes"
terraform output
terraform plan -destroy
terraform destroy   # type "yes"
```

Demos: [06-terraform-vpc/README.md](06-terraform-vpc/README.md), [07-terraform-workflow/README.md](07-terraform-workflow/README.md), [08-mini-project/README.md](08-mini-project/README.md).
