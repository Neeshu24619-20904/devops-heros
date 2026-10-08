# Session 5 — Git & GitHub Homework (Tasks 1–2)

## Task 1: `git commit -m` vs `git commit -a -m`

### Concept
- `git commit -m "message"`: commits **only already-staged** changes (`git add`ed). Modified but unstaged files and deletions are NOT included.
- `git commit -a -m "message"`: auto-stages **modified and deleted tracked files**, then commits. Does NOT include new **untracked** files — those still need `git add`.

### Commands — `git commit -m` (staged only)
```bash
echo "v1" > file.txt
git add file.txt
git commit -m "add file"
echo "v2" >> file.txt          # modify, do NOT stage
git status                     # file.txt shows as modified, unstaged
git commit -m "try commit"     # commits NOTHING new (working tree still dirty)
git status                     # change still present -> proves -m alone skips unstaged work
```

### Commands — `git commit -a -m` (auto-stage tracked)
```bash
echo "v3" >> file.txt          # modify tracked file
git commit -a -m "auto-stage tracked change"
git status                     # clean -> proves -a staged the modification

touch brand-new.txt            # untracked file
git commit -a -m "try new file"
git status                     # brand-new.txt still untracked -> proves -a ignores new files
git add brand-new.txt
git commit -m "add new file"
```

### Expected output
```
$ git commit -m "try commit"
On branch main
Changes not staged for commit:
  modified:   file.txt
no changes added to commit

$ git commit -a -m "auto-stage tracked change"
[main abc1234] auto-stage tracked change
 1 file changed, 1 insertion(+)
```

### Screenshots
- `git commit -m`:

  ![git commit -m](./screenshots/git%20commit%20-m.png)

- `git commit -a -m`:

  ![git commit -a -m](./screenshots/git%20commit%20-a%20-m.png)

### Interview notes
- `-a` = shortcut for `git add -u` (tracked updates only), NOT `git add -A`.
- New files always need explicit `git add`; `-a` never picks them up.
- Prefer explicit `git add -p` + `git commit -m` for careful, reviewable commits; `-a` is for quick iterations.

---

## Task 2: `git cherry-pick`

### Concept
Cherry-pick copies **one specific commit** (by hash) from anywhere and replays it onto the current branch — useful for pulling a single fix into `main` without merging a whole branch.

### Commands — full workflow
```bash
# 1. Create 2-4 commits on main
git checkout -b demo 2>/dev/null || git checkout main
echo one > a.txt && git add a.txt && git commit -m "main: commit 1"
echo two > b.txt && git add b.txt && git commit -m "main: commit 2"
git log --oneline -n 4

# 2. Create a new branch, add 2-3 commits
git checkout -b feature
echo feat1 > f1.txt && git add f1.txt && git commit -m "feature: commit 1"
echo feat2 > f2.txt && git add f2.txt && git commit -m "feature: commit 2 (fix to cherry-pick)"
echo feat3 > f3.txt && git add f3.txt && git commit -m "feature: commit 3"
git log --oneline -n 5

# 3. Identify the target commit hash
TARGET=$(git log --oneline --grep="fix to cherry-pick" --format=%H -n 1)
echo $TARGET

# 4. Cherry-pick it onto main and verify
git checkout main
git cherry-pick $TARGET
git log --oneline -n 5
ls f2.txt && cat f2.txt     # picked change now present on main
git status
```

### Conflict handling
```bash
git cherry-pick <sha>          # if conflict:
git status                     # resolve files, then:
git add <resolved-files>
git cherry-pick --continue
# or: git cherry-pick --abort  # to cancel
```

### Expected output
```
$ git cherry-pick a1b2c3d
[main e4f5g6h] feature: commit 2 (fix to cherry-pick)
 Date: ...
 1 file changed, 1 insertion(+)
 create mode 100644 f2.txt
```

### Screenshots

![cherry-pick step 1](./screenshots/git%20cherry-pick-1.png)

![cherry-pick step 2](./screenshots/git%20cherry-pick-2.png)

![cherry-pick step 3](./screenshots/git%20cherry-pick-3.png)

![cherry-pick step 4](./screenshots/git%20cherry-pick-4.png)

### Interview notes
- Cherry-pick creates a **new commit hash** (same diff, new identity) — history is not shared with the source.
- Use it for hotfixes/single-commit backports; use `merge`/`rebase` for whole-branch integration.
- Know `--continue`, `--abort`, `--skip`, and `-x` (records original hash in message).
