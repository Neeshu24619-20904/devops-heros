# Session 14 — Kubernetes Troubleshooting

Core-command drills + broken→fixed issue labs + production-style mini-project.

## Assignment — [assignment/README.md](./assignment/README.md)

Full evidence with screenshots: Task 1 commands, Task 2 issues, Task 3 mini-project.

### Task 1 — Core commands

`kubectl get`, `describe`, `logs`, `exec`, `events` (+ misc commands). Debug order: `get` → `describe` → `logs` → `exec` → `events`.

![kubectl get](./assignment/screenshots/task1/kubectl%20get.png)

![kubectl describe](./assignment/screenshots/task1/kubectl%20describe.png)

![kubectl logs](./assignment/screenshots/task1/kubectl%20logs.png)

![kubectl exec](./assignment/screenshots/task1/kubectl%20exec.png)

![kubectl events](./assignment/screenshots/task1/kubectl%20events%201.png)

See [assignment/README.md](./assignment/README.md) for all Task 1 screenshots (events 1–3, misc commands).

### Task 2 — Issues (CrashLoopBackOff, ImagePullBackOff, Pending, Service/DNS)

- CrashLoopBackOff ([06-crashloopbackoff](./06-crashloopbackoff/)): `exit 1` loop → `logs --previous` → fix command. ![crashloop](./assignment/screenshots/task2/crashloop1.png)
- ImagePullBackOff ([07-imagepullbackoff](./07-imagepullbackoff/)): bad tag → `describe` pull error → retag `:latest`. ![imagepull](./assignment/screenshots/task2/imagepullback1.png)
- Pending ([08-pending-pods](./08-pending-pods/)): impossible requests → `0/1 nodes available` → lower requests. ![pending](./assignment/screenshots/task2/pendingpods.png)
- Service/DNS ([09-service-dns-troubleshooting](./09-service-dns-troubleshooting/)): selector mismatch → empty Endpoints; `nslookup` from dns-test pod.

See [assignment/README.md](./assignment/README.md) for the full broken→fixed command sequences and remaining screenshots.

### Task 3 — Mini-project

Deploy → break → investigate → fix → verify Nginx app ([mini-project](./mini-project/)). Q&A (ImagePullBackOff caused by nonexistent tag; found via `kubectl get pods -o wide`; fixed by retagging to `latest`) plus 5 terminal screenshots in [assignment/README.md](./assignment/README.md).

![mini-project](./assignment/screenshots/task3/miniproject1.png)

---

## Topic guides

| Guide | Covers |
|---|---|
| [01-kubectl-get](./01-kubectl-get/) | `get` drills |
| [02-kubectl-describe](./02-kubectl-describe/) | `describe` drills |
| [03-kubectl-logs](./03-kubectl-logs/) | `logs` / `--previous` |
| [04-kubectl-exec](./04-kubectl-exec/) | `exec` + in-container `curl` |
| [05-events](./05-events/) | cluster events |
| [06-crashloopbackoff](./06-crashloopbackoff/) | `broken-pod.yaml` → `fixed-pod.yaml` |
| [07-imagepullbackoff](./07-imagepullbackoff/) | bad tag → good tag |
| [08-pending-pods](./08-pending-pods/) | unsatisfiable requests → fixed requests |
| [09-service-dns-troubleshooting](./09-service-dns-troubleshooting/) | Service selectors, Endpoints, DNS |
| [scenarios](./scenarios/) | `scenario-1-crashloop` … `scenario-5-oomkilled` + `triage_all.sh` |
| [mini-project](./mini-project/) | Nginx deployment challenge |
| [assignment](./assignment/) | graded evidence (Tasks 1–3 + screenshots) |
