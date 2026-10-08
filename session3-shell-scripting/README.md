# Session 3 — Shell Scripting Homework: System Information Script

Homework task (from `task.md`): create a shell script that prints the current date,
hostname, username, disk usage and running processes; uses variables; takes user
input with `read -p`; creates a directory with `mkdir`; creates a file with `touch`;
and stores process info in a file with `>` redirection.

## Homework script (`test1.sh` — canonical submission script)

```bash
#!/bin/bash

mkdir result_file
cd result_file
touch result.log
echo "This is my result file" > result.log
date
echo hostname
echo whoami
df -h
ps > process.log

read -p "Enter your name: " name
read -p "Enter your roll number: " roll_no

current_date=$(date)
echo "My name is $name" >> result.log
```

> Note: `echo hostname` / `echo whoami` print literal strings. The correct commands
> to print the values are `hostname` and `whoami` (see corrected script below).

## Corrected / recommended version

```bash
#!/bin/bash
# sysinfo.sh — system information script (homework, corrected)

current_date=$(date)          # variable + command substitution
my_hostname=$(hostname)       # variable
my_user=$(whoami)             # variable

echo "Current date: $current_date"
echo "Hostname: $my_hostname"
echo "Username: $my_user"

echo "--- Disk usage ---"
df -h

echo "--- Running processes ---"
ps aux | head -n 20

read -p "Enter your name: " name
read -p "Enter your roll number: " roll_no
read -p "Enter your comment: " comment

echo "My name is $name"
echo "My roll number is $roll_no"
echo "My comment is: $comment"

mkdir -p result_file           # create directory
touch result_file/result.log  # create file
ps > result_file/process.log  # redirection: process info -> file
echo "My name is $name (roll: $roll_no) on $current_date" >> result_file/result.log
cat result_file/result.log
```

## Supporting practice scripts in this folder

| File | Concept demonstrated |
|---|---|
| `variable.sh` | Variables: `name="Nensi"`, `roll_no=123`, `echo "My name is $name"` |
| `input.sh` | `read -p` prompts for name / roll_no / comment, then `echo` with variables |
| `hello.sh` | `mkdir hello`, `touch app.log`, `echo ... > app.log`, `cat app.log` |
| `script1.sh` | `mkdir test`, `echo ... > app.log` (overwrite) vs `>> app.log` (append), `cat` |
| `data.sh` | `mkdir data1`, overwrite vs append behaviour of `>` |
| `condition.sh` | `if/elif/else` on `read -p` age input |
| `loop.sh` / `while_loop.sh` / `while_loop1.sh` | `for` and `while` loops |
| `function.sh` | `show_info(){ ...; }` function definition + call |

## Commands used

```bash
chmod +x test1.sh sysinfo.sh
./test1.sh
./sysinfo.sh

# Individual commands covered by the homework
date
hostname
whoami
who
df -h
ps aux
mkdir result_file
touch result_file/result.log
ps > process.log
echo "text" >> result.log
cat result.log
```

## Expected output

```
$ ./sysinfo.sh
Current date: Fri Oct  3 10:30:00 UTC 2026
Hostname: myhost
Username: neeshant
--- Disk usage ---
Filesystem      Size  Used Avail Use% Mounted on
/dev/sda1        50G   20G   30G  40% /
--- Running processes ---
USER  PID %CPU %MEM COMMAND
...
Enter your name: Nensi
Enter your roll number: 123
Enter your comment: Awesome
My name is Nensi
My roll number is 123
My comment is: Awesome

$ cat result_file/process.log | head -n 5
    PID TTY          TIME CMD
      1 ?        00:00:01 systemd
...
```

## Screenshots

No `screenshots/` folder exists in this session yet. The terminal outputs above serve
as the documented result. To add evidence screenshots later:

1. Create `screenshots/` in this folder.
2. Run `./sysinfo.sh`, capture the terminal, save as `screenshots/sysinfo-output.png`.
3. Embed with `![sysinfo output](./screenshots/sysinfo-output.png)`.

## Interview notes

- `var=$(command)` captures output; `echo $var` prints it — always quote: `echo "$var"`.
- `>` overwrites a file, `>>` appends; `ps > process.log` redirects stdout to a file.
- `read -p "Prompt: " var` prompts and stores input in one step.
- `mkdir -p` avoids errors if the directory exists; `touch` creates an empty file or updates timestamps.
- `date`, `hostname`, `whoami`, `df -h`, `ps aux` are the standard system-info quintet.
