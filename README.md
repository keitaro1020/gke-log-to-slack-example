# GKEのログをSlackに通知する

## Architecture

![diagram](https://raw.githubusercontent.com/keitaro1020/gke-log-to-slack-example/webproxy/architecture.png)

## build

1. Create GKE Container

```
gcloud container clusters create example-cluster-2 --scopes https://www.googleapis.com/auth/logging.write
```

2. Login GKE

see at GCP Console

3. Create Namespace

```
kubectl create namespace logging-system
```

4. build & push docker image

```
./gradlew build docker
docker tag gke-log-service/gke-log-service:0.0.1 gcr.io/{project_id}/gke-log-service:0.0.1
gcloud docker -- push asia.gcr.io/{project_id}/gke-log-service:0.0.1
```

5. Create k8s service

```
kubectl apply -f k8s.yml
```

6. Deploy log2slack function

see at log2slack/README.md

## run

```
curl "http://{service endpoint}/log?level={level}&text={log message}"
```

- level
    - TRACE
    - DEBUG
    - INFO
    - WARN
    - ERROR