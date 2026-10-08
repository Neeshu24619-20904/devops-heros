# Session 1: DevOps Engineer Roadmap

## Overview

Orientation session: what DevOps is, what a DevOps engineer does, and the
learning path followed by the rest of this repo (Linux → scripting →
networking → Git → Docker → Kubernetes → Helm → CI/CD → DevSecOps →
Terraform/Cloud → monitoring/GitOps → Python).

Existing note: [`session1.md`](./session1.md) (title only); slides: [`devops1-83.pdf`](./devops1-83.pdf).

## What Is DevOps?

Culture + practices bridging Dev and Ops: automate everything, ship small and
often, measure and improve (CALMS: Culture, Automation, Lean, Measurement,
Sharing). Goals: faster releases, stable systems, short feedback loops.

## Roadmap Stages

1. **Foundations** — Linux, networking, shell scripting (`session2`, `session3`, `session4`)
2. **Version control** — Git/GitHub branching, PRs (`session5`)
3. **Containers** — Docker images, networks, volumes, Compose, multi-stage builds (`session6-7`, `session8`)
4. **Orchestration** — Kubernetes architecture, objects, services, ingress, storage, HPA/probes, troubleshooting (`session9`–`session14`)
5. **Packaging / delivery** — Helm, GitHub Actions CI/CD, DevSecOps scanning (`session15`–`session17`)
6. **IaC / Cloud** — Terraform, cloud provisioning (`session18`, `session19`)
7. **Operate** — monitoring/observability, GitOps, Python automation (`session20`, `session21`)

## Core Skills Checklist

- Linux CLI, SSH, systemd, logs; Bash scripting
- TCP/IP, DNS, HTTP, reverse proxy (Nginx/Apache)
- Git flow; Dockerfile best practices; Compose; registries
- K8s: pods, deployments, services, ingress, config/secrets, volumes, HPA, probes
- CI/CD pipelines, image scanning, secrets management
- Terraform state/modules; cloud networking/compute; Prometheus/Grafana; Python scripting

## How to Use This Repo

Follow sessions in numeric order; each folder has its own README with commands,
configs, and screenshots as deliverables.
