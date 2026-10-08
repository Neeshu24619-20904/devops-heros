# Session 17: DevSecOps

Security is shifted left — every push runs tests plus security scans, and gates decide whether the pipeline may continue.

## Flow

```text
Push → Tests (pytest) → SAST (CodeQL) → SCA (pip-audit)
  → Build (docker build) → Image scan (Trivy)
  → Push (GHCR) → Deploy (kubectl apply)
```

All three pre-build checks (tests, SAST, SCA) must pass before the image is built. See `demo/README.md` Method 3 for the full 7-stage diagram.

## Scans & Gates

| Check | Tool | What it catches | Gate behavior |
|---|---|---|---|
| **SAST** (Static Application Security Testing) | GitHub CodeQL (`04-sast/`) | Insecure patterns in source without running the app | Fail → stop pipeline |
| **SCA** (Software Composition Analysis) | `pip-audit` (`05-sca/`) | Known CVEs in dependencies (`requirements.txt`) | Fail → stop pipeline |
| **Secret scanning** | Gitleaks / GitHub secret scanning (`06-secret-scanning/`) | Hardcoded credentials, `.pem`/`.key`/`.env` leaks | Fail → stop pipeline, rotate secret |
| **Image scan** | Trivy (`07-container-image-scanning/`) | OS + package CVEs in the built image | Fail (e.g. HIGH/CRITICAL) → don't push/deploy |
| **Security gates** (`08-security-gates/`) | Pipeline `needs:` / job conditions | Turns findings into PASS/FAIL decisions | FAIL → build/push/deploy skipped |

Supporting stages: container registry (`02-container-registry/`, GHCR) and Kubernetes deployment (`03-kubernetes-deployment/`, `demo/k8s/`).

## Demo

Full Flask app + pipeline: [demo/README.md](demo/README.md) — run manually (`python3 app/app.py`), via Docker, via GitHub Actions (`demo/.github/workflows/devsecops.yml`, needs `KUBECONFIG` secret), or deploy to K8s manually (`kubectl apply -f k8s/`).

## Reference

- CodeQL: https://codeql.github.com/docs/
- pip-audit: https://pypi.org/project/pip-audit/
- Trivy: https://aquasecurity.github.io/trivy/
