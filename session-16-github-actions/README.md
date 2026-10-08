# Session 16: CI/CD & GitHub Actions

Automates build, test, and delivery on every `git push` so broken code is caught before it ships.

## CI vs CD

| CI (Continuous Integration) | CD (Continuous Delivery / Deployment) |
|---|---|
| Build + test on every push/PR | Deliver / deploy after tests pass |
| Find bugs early | Make the app available |
| `pytest`, lint, validate | Release, push image, deploy to K8s |

This session implements CI + build artifact; deployment targets (Docker/K8s/cloud) come later.

## Topics Covered

- CI vs CD, pipeline concepts (stages, jobs, steps)
- GitHub Actions intro, workflows (`.github/workflows/*.yml`, `on: push / pull_request / workflow_dispatch`)
- Jobs / steps / runners (`runs-on: ubuntu-latest`, parallel by default, `needs:` for ordering)
- Secrets (`${{ secrets.NAME }}`, never echo values, Settings → Secrets and variables → Actions)
- Artifacts (`actions/upload-artifact`, `calculator-build` with `build/` output)
- Build + test pipeline (checkout → setup Python → install → `pytest` → `./build.sh` → upload)

Sub-folders use timestamped names (`01-ci-vs-cd 10-33-34-211/`, …) plus a cleaned copy at `session-16-github-actions/` (`01-ci-vs-cd/`, `02-cicd-pipeline/`, …, `10-final-cicd-pipeline/`).

## Workflow / Jobs / Steps / Runners / Secrets / Artifacts / Build / Test

```yaml
name: Python CI Pipeline
on:
  push: { branches: [main] }
  pull_request: { branches: [main] }
  workflow_dispatch:
jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v6
      - uses: actions/setup-python@v7
        with: { python-version: "3.12" }
      - run: pip install -r requirements.txt
      - run: pytest -v
      - run: ./build.sh
      - uses: actions/upload-artifact@v4
        with: { name: calculator-build, path: build/ }
```

- **Workflow:** whole YAML automation definition.
- **Jobs:** groups of steps on one runner; parallel by default; `needs: test` makes build wait.
- **Steps:** each `uses:` (prebuilt action) or `run:` (shell command).
- **Runners:** GitHub-hosted ephemeral VMs (`ubuntu-latest`).
- **Secrets:** e.g. `DEMO_SECRET`; check presence without printing: `[ -n "$DEMO_SECRET" ]`.
- **Artifacts:** build output (`build/calculator.py`, `build/build-info.txt`) downloadable from the run Summary.
- **Build / test:** `pytest -v` gates the build; breaking `add()` → red pipeline → fix → green pipeline.

## Demos

- Hands-on calculator app: `session-16-github-actions/demo/README.md`
- Final 3-job pipeline (test + security-check + build): `session-16-github-actions/10-final-cicd-pipeline/README.md`

## Pipeline Screenshots

No checked-in screenshots for this session. To capture them: push to `main`, open repo → **Actions** → select the run, screenshot the jobs/steps log and the **Artifacts** (`calculator-build`) section on the run Summary page.

## Reference

- https://docs.github.com/en/actions
- Workflow syntax: https://docs.github.com/en/actions/using-workflows/workflow-syntax-for-github-actions
