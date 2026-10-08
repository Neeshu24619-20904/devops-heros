# Session 9: Kubernetes Fundamentals

## Overview

First Kubernetes session: install Minikube, bring up a local cluster, learn
control-plane/worker architecture, and work through the Kubernetes Basics
tutorial (pods, deployments, services).

Reference links:

- https://kubernetes.io/docs/tutorials/kubernetes-basics/
- https://minikube.sigs.k8s.io/docs/start/?arch=%2Fmacos%2Farm64%2Fstable%2Fbinary+download
- https://kubernetes.io/docs/concepts/architecture/
- https://github.com/Nency-Ravaliya/Kubernetes

## Deliverables

1. Minikube installed
2. Cluster running (`minikube status` / `kubectl cluster-info` clean)
3. Architecture understood (control plane vs nodes)
4. Basics tutorial completed (pod → deployment → service, access in browser)

## 1. Install Minikube

```bash
# macOS (arm64)
brew install minikube
# or: curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-darwin-arm64
#     sudo install minikube-darwin-arm64 /usr/local/bin/minikube

minikube version
kubectl version --client
```

Reference: Minikube start guide (link above).

## 2. Cluster Status

```bash
minikube start --driver=docker
minikube status
kubectl cluster-info
kubectl get nodes
```

Expected:

```text
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
```

```text
Kubernetes control plane is running at https://127.0.0.1:xxxxx
CoreDNS is running at ...
```

## 3. Architecture (concepts)

- **Control plane:** kube-apiserver, etcd, kube-scheduler,
  kube-controller-manager, cloud-controller-manager.
- **Nodes:** kubelet, kube-proxy, container runtime; run **pods**.
- **Objects:** Pod → ReplicaSet → Deployment; Service (ClusterIP/NodePort/LoadBalancer);
  ConfigMap/Secret; Ingress; Volumes.
- Declarative model: `kubectl apply -f manifest.yaml` reconciles desired state.

Reference: https://kubernetes.io/docs/concepts/architecture/

## 4. Basics Tutorial

```bash
kubectl create deployment hello-minikube --image=k8s.gcr.io/echoserver:1.4
kubectl expose deployment hello-minikube --type=NodePort --port=8080
kubectl get pods,deploy,svc
minikube service hello-minikube   # opens URL in browser
# or: kubectl port-forward svc/hello-minikube 8080:8080
curl http://localhost:8080
```

Tutorial: https://kubernetes.io/docs/tutorials/kubernetes-basics/
Extra examples: https://github.com/Nency-Ravaliya/Kubernetes

## Cleanup

```bash
minikube stop
minikube delete
```
