# Session 18: Terraform & IaC

Infrastructure defined in code instead of console clicks — versioned, repeatable, destroyable.

> Merged from the former `Readme.md` (install links) plus the S3 demo and topic folders. This `README.md` is the single master file.

## Install & Setup

- Terraform: https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli
- Terraform + AWS guide: https://developer.hashicorp.com/terraform/tutorials/aws-get-started/aws-create
- AWS CLI: https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html

```bash
aws configure
aws sts get-caller-identity
```

## Topics Covered

| Folder | Topic |
|---|---|
| `01-iac-basics/` | IaC concepts (manual vs code-managed infra) |
| `02-terraform-architecture/` | Core / providers / state model |
| `03-providers/` | Provider + version constraints |
| `04-resources/` | Resource blocks |
| `05-variables/` | Variables + `terraform.tfvars` |
| `06-outputs/` | Outputs |
| `07-init-plan-apply/` | init / fmt / validate / plan / apply |
| `08-destroy/` | plan-destroy / destroy |
| `09-state/` | `terraform state list/show` |
| `terraform-s3-demo/` | Full S3 bucket demo (below) |

## S3 Demo Workflow

Full walkthrough: [terraform-s3-demo/README.md](terraform-s3-demo/README.md)

```bash
terraform init
terraform fmt
terraform validate
terraform plan      # Plan: 1 to add, 0 to change, 0 to destroy.
terraform apply     # type "yes"
terraform output
terraform state list        # aws_s3_bucket.demo (name varies by branch)
terraform state show aws_s3_bucket.demo
aws s3 ls
terraform plan -destroy
terraform destroy   # type "yes"
```

Files: `terraform.tf`, `providers.tf`, `variables.tf`, `main.tf` (`aws_s3_bucket`), `outputs.tf`.

## AWS Services Referenced

No `aws-services/` directory exists in this session, so the notes live inline here:

- **01 IAM** — users/roles/policies; credentials for `aws configure`; verified via `aws sts get-caller-identity`.
- **02 EC2** — compute context referenced by Terraform provider/auth docs; not provisioned in this demo.
- **03 S3** — the demo resource (`aws_s3_bucket`, `force_destroy = true`); verified with `aws s3 ls` / `head-bucket`.
- **04 VPC** — network isolation context for AWS resources; built out fully in Session 19.
- **05 DynamoDB / RDS** — state-locking backends (DynamoDB) and managed databases (RDS) as next steps beyond local `terraform.tfstate`.
