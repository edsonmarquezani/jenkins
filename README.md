## Jenkins Modular Pipeline

This is a boilerplate for a Jenkinsfile scripted pipeline written in Groovy, with
which you can define a sequence of stages in a higher-level, using pre-programmed
modules.

It is versioned, for extension and backwards compatibility capabilities, and reads
a YAML defining the pipeline, like following.

```
---
version: v1
stages:
- name: Build
  module: nodejs/generic
  options:
    build_command: ./build.sh

- name: Package
  module: docker/build
  options:
    docker_registry: dockerhub.com
    docker_image_name_prefix: nome

- name: Deploy
  module: kubernetes/replicaset
  options:
    kube_cluster_name: awsdev
    kube_namespace: gestao-grupos
    kube_replicaset_name: app-cn

- name: "Publish Files"
  module: s3/sync
  options:
    source_dir: ./content
    s3_bucket: company-staticfiles
    s3_prefix: files
```

The modules lie inside of `version/modules`, in a tree organization schema, where
each one has its submodules, which are groovy scripts as well. Each module/submodule
is responsible for reading and treating its options/parameters, leaving the main groovy
agnostic of any implementation details.
