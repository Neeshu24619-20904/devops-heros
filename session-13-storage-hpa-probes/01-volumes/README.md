What I Learned

The main thing I understood from Kubernetes storage is that container storage and application storage are different things.

Containers are designed to be replaceable, so important data should not normally depend on the container's filesystem.

Kubernetes provides different storage options depending on the requirement.

For temporary data, emptyDir can be enough.

For accessing storage directly from a node, hostPath can be used, although it has limitations.

For persistent application data, PersistentVolume and PersistentVolumeClaim are used.

Finally, StorageClass and dynamic provisioning make the process more automatic by allowing Kubernetes to provision storage when an application requests it.

The overall idea can be remembered as:

Temporary Data
     ↓
  emptyDir

Node Storage
     ↓
  hostPath

Persistent Storage
     ↓
     PVC
     ↓
     PV

Automatic Storage
     ↓
StorageClass
     ↓
Dynamic Provisioning

This helped me understand why Kubernetes storage is separated from the lifecycle of individual containers and Pods.
