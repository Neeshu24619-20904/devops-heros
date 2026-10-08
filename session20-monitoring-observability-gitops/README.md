# Session 20: Monitoring, Observability & GitOps

From "is it up?" (monitoring) to "why is it broken?" (observability), then to Git-driven delivery (GitOps).

## Monitoring vs Observability

- **Monitoring** asks "Is the system healthy?" — dashboards + alerts on known problems (e.g. `IF error_rate > 5% THEN alert`). See `01-monitoring-vs-observability/`.
- **Observability** asks "Why is it behaving this way?" — infer internals from telemetry, essential for unknown problems.

## Three Pillars

| Pillar | What | Example |
|---|---|---|
| **Metrics** (`02-metrics-logs-traces/`, `03-prometheus/`) | Numbers over time | `http_requests_total`, CPU %, latency; Prometheus scrapes `/metrics` |
| **Logs** (`02-metrics-logs-traces/`) | Discrete events | App error lines, access logs |
| **Traces** (`02-metrics-logs-traces/`) | Request journey across services | Trace/span IDs through frontend → API → DB |

- **Prometheus** (`03-prometheus/`): metrics collection, PromQL, alerting rules.
- **Grafana** (`04-grafana/`): dashboards over Prometheus + other sources.

## GitOps

- **Intro + Git as source of truth** (`05-introduction-to-gitops/`, `06-git-as-source-of-truth/`): desired state lives in Git; clusters converge to it; every change is a commit (auditable, revertable).
- **Argo CD** (`07-argocd/`): watches the repo, auto-syncs Kubernetes.

```text
Git (desired state) → Argo CD (auto sync) → Kubernetes (running app)
```

## Mini-project

[08-mini-project/README.md](08-mini-project/README.md): `kind create cluster`, Namespace + Deployment (`replicas: 2`) + Service + Argo CD Application, push → Argo CD syncs → app runs.
