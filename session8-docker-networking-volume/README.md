# Session 8: Docker Networking & Volumes

## Overview

Hands-on with Docker networks (bridge, custom, host, overlay) and volumes
(bind mounts, named volumes). Covers 4 homework tasks plus the in-class
`demo/` 3-tier app.

- Homework detail: [`network and volume homework/readme.md`](./network%20and%20volume%20homework/readme.md)
- Class demo: [`demo/`](./demo/) (`frontend` + `backend` + `database`)
- Compose variant: [`docker-compose.yml`](./docker-compose.yml)

## Resources

- https://docs.docker.com/engine/network/drivers/

## Demo: 3-Tier App (`demo/`)

`demo/docker-compose.yml` wires `frontend (nginx)` → `backend (built ./backend)`
→ `database (mysql:8.0)` across two custom bridge networks with a named volume:

```yaml
services:
  frontend:
    image: nginx:latest
    ports: ["8080:80"]
    volumes:
      - ./frontend/index.html:/usr/share/nginx/html/index.html
      - ./frontend/nginx.conf:/etc/nginx/conf.d/default.conf
    networks: [frontend_net]
  backend:
    build: ./backend
    networks: [frontend_net, backend_net]
    depends_on: [database]
  database:
    image: mysql:8.0
    environment: { MYSQL_ROOT_PASSWORD: root, MYSQL_DATABASE: demo }
    volumes: [db_data:/var/lib/mysql]
    networks: [backend_net]
networks: { frontend_net: {}, backend_net: {} }
volumes: { db_data: {} }
```

```bash
cd demo
docker compose up --build -d
docker network inspect demo_frontend_net
docker network inspect demo_backend_net
docker exec <backend> ping frontend
docker exec <backend> ping database
```

Simplified variant in root `docker-compose.yml` runs the same 3 services
(frontend/backend/database) with a `db_data` named volume for MySQL persistence.

## Task 1: 3 Containers + 3 Networks

**Done:** created `frontend` (Nginx), `backend` (Alpine), `database` (MySQL)
containers; created 3 Docker networks; connected `backend` to 2 networks;
verified `frontend ↔ backend` and `backend ↔ database` connectivity
(`ping` / `docker exec` / `docker inspect` / `docker network ls`).

```bash
docker network create net-frontend
docker network create net-mid
docker network create net-backend
docker run -d --name frontend --network net-frontend nginx
docker run -d --name backend --network net-mid alpine sleep infinity
docker network connect net-frontend backend
docker run -d --name database --network net-backend \
  -e MYSQL_ROOT_PASSWORD=root mysql:8.0
docker network connect net-mid database  # or backend_net per demo
docker exec backend ping -c 3 frontend
docker exec frontend ping -c 3 backend
docker inspect backend
docker network ls
```

### Screenshots

- docker exec backend:

  ![docker exec backend](./network%20and%20volume%20homework/screenshots/task-1/docker%20exec%20backend.png)

- docker exec frontend:

  ![docker exec frontend](./network%20and%20volume%20homework/screenshots/task-1/docker%20exec%20frontend.png)

- docker inspect:

  ![docker inspect](./network%20and%20volume%20homework/screenshots/task-1/docker%20inspect.png)

- docker network ls:

  ![docker network ls](./network%20and%20volume%20homework/screenshots/task-1/docker%20ls.png)

## Task 2: Host Network + Apache2

**Done:** pulled Apache2 image, ran container on the **host** network,
verified site on port 80.

```bash
docker pull httpd:2.4
docker run -d --name apache-host --network host httpd:2.4
curl http://localhost:80
docker ps
docker logs apache-host
```

> `--network host` shares the host stack (Linux only); no `-p` mapping needed.

### Screenshots

- Website (port 80):

  ![Running Task 2](./network%20and%20volume%20homework/screenshots/task-2/Running%20Task%202%20of%20Network.png)

- Commands:

  ![Commands Task 2](./network%20and%20volume%20homework/screenshots/task-2/commands%20of%20task%202.png)

## Task 3: Bind Mount `index.html` ("Hello students")

**Done:** local folder with `index.html` containing `Hello students`,
bind-mounted into Nginx; verified live reload without container restart.

```bash
mkdir html && echo "Hello students" > html/index.html
docker run -d -p 8080:80 --name nginx-bind \
  -v "$(pwd)/html:/usr/share/nginx/html" nginx
curl http://localhost:8080   # Hello students
echo "Hello students - updated" > html/index.html
curl http://localhost:8080   # updated text, no restart
docker inspect nginx-bind --format '{{ .Mounts }}'
```

(No screenshots required — verify via `curl`/browser.)

## Task 4: Overlay Network (Research)

- Overlay networks let containers on **multiple Docker hosts** communicate as
  if on one network (VXLAN encapsulation, requires Swarm / key-value store).
- Used by **Docker Swarm** and distributed apps for cross-host service discovery
  and load balancing.
- Contrast: `bridge` = single host, `host` = shared host stack,
  `overlay` = multi-host, `macvlan` = physical-LAN IPs.
- Typical Swarm flow: `docker swarm init`, `docker network create -d overlay mynet`,
  `docker service create --network mynet ...`.

(No screenshots — research task.)
