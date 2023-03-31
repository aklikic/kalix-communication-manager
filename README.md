# Communication manager Kalix project
Instructions how to develop and run services in local Kalix sandbox.<br><br>
Main purpose of `Local Kalix sandbox` is zero friction in developing and testing Kalix services.<br> 
`Kalix platform` is used as a runtime environment with high availability and resilience that `Local Kalix sandbox` does not provide.<br>
`Local Kalix sandbox` can be deployed in two options based on availability of docker engine on developer's machine:
1. Developer's machine with available docker engine<br> 
Allows full Kalix developer experience with very fast turnover.<br>
Enables: developing Kalix service using Kalix Java SDK and local run and test (requires docker compose to run the Kalix service and Kalix Proxy)
2. Developer's machine without docker engine available<br>
In case developer's machine does not have docker engine available then external machine (for example Linux based) can be used.<br>
Developer's machine enables: developing Kalix service using Kalix Java SDK<br>
Docker engine enabled machine enables: local run and test
# Developer's machine with available docker engine
## Prerequisite
Java 17<br>
Apache Maven 3.6 or higher<br>
Access to Kalix Java SDK dependencies hosted on `Maven respository` (maybe required to be downloaded and scanned by local `Artifactory`): [kalix_full_dependecy_tree.txt](kalix_full_dependecy_tree.txt)<br>
Access to Kalix Java SDK Maven plugins hosted on `Maven respository` (maybe required to be downloaded and scanned by local `Artifactory`): [kalix_full_maven_plugins.txt](kalix_full_maven_plugins.txt)<br>
Docker 20.10.8 or higher (engine and compose)<br>

## Setup local Zookeeper and Kafka
1. Start kafka and zookeeper <br>
```
cd local
docker-compose -f docker-compose-kafka.yaml up
```
2. Create Kafka topics <br>
3. [Open Kafka UI](http://localhost:8081/)
4. Create channel topic: `channel-topic` (number of partitions: 2, Min Sync replicas: 1, Replication factor: 1, Time to retain data: 600000)

`NOTE:` For using external Kafka no need to run this. It is only required to update `bootstrap.servers` in `local/local.kafka.properties`. It is important that `channel-topic` topic is created prior to starting. 
## pom.xml setup
Copy `pom-full-dep.xml` to `pom.xml`
## Run Kalix locally
Start Kalix proxy:
```
cd local
docker-compose -f docker-compose-kalix.yaml up
```
Start Kalix service:
```
mvn compile exec:exec
```
## Test locally
Send email:
```
curl -XPOST -d '{ 
  "userId": "1",
  "channel": "EMAIL",
  "notificationType": "debit-notification"
}' http://localhost:9000/api/send -H "Content-Type: application/json"
```
Send sms:
```
curl -XPOST -d '{ 
  "userId": "1",
  "channel": "SMS",
  "notificationType": "debit-notification"
}' http://localhost:9000/api/send -H "Content-Type: application/json"
```
Send push:
```
curl -XPOST -d '{ 
  "userId": "1",
  "channel": "PUSH",
  "notificationType": "debit-notification"
}' http://localhost:9000/api/send -H "Content-Type: application/json"
```
# Developer's machine without docker engine available
## Developer's machine setup
### Prerequisite
Java 17<br>
Apache Maven 3.6 or higher<br>
Access to Kalix Java SDK dependencies hosted on `Maven respository` (maybe required to be downloaded and scanned by local `Artifactory`): [kalix_min_dependecy_tree.txt](kalix_min_dependecy_tree.txt)<br>
Access to Kalix Java SDK Maven plugins hosted on `Maven respository` (maybe required to be downloaded and scanned by local `Artifactory`): [kalix_min_maven_plugins.txt](kalix_min_maven_plugins.txt)<br>
### pom.xml setup
Copy `pom-min-dep.xml` to `pom.xml`
### Create Kalix SDK project uber jar
1. Create project uber jar
```
mvn package
```
2. Copy uber jar `target/communication-manager-1.0-SNAPSHOT.jar` to `docker/maven` folder

# Docker engine enabled machine
## Prerequisite
Docker 20.10.8 or higher (engine and compose)
## Copy `docker` folder from Developer's machine to this machine
## Setup local Zookeeper and Kafka
1. Start kafka and zookeeper <br>
```
cd docker
docker-compose -f docker-compose-kafka.yaml up
```
2. Create Kafka topics <br>
3. [Open Kafka UI](http://setme:8081/) Note: Change the URL `hostname` instead of `setme`
4. Create channel topic: `channel-topic` (number of partitions: 2, Min Sync replicas: 1, Replication factor: 1, Time to retain data: 600000)

`NOTE:` For using external Kafka no need to run this. It is only required to update `bootstrap.servers` in `docker/local.kafka.properties`. It is important that `channel-topic` topic is created prior to starting.
## Build Kalix SDK project docker image
1. Build Kalix SDK project docker image:
```
cd docker
docker build -t communication-manager .
```
## Run Kalix locally
```
cd docker
docker-compose -f docker-compose-kalix.yml up
```
## Test locally
Note: Change the URL `hostname` instead of `setme` <br>
Send email:
```
curl -XPOST -d '{ 
  "userId": "1",
  "channel": "EMAIL",
  "notificationType": "debit-notification"
}' http://setme:9000/api/send -H "Content-Type: application/json"
```
Send sms:
```
curl -XPOST -d '{ 
  "userId": "1",
  "channel": "SMS",
  "notificationType": "debit-notification"
}' http://setme:9000/api/send -H "Content-Type: application/json"
```
Send push:
```
curl -XPOST -d '{ 
  "userId": "1",
  "channel": "PUSH",
  "notificationType": "debit-notification"
}' http://setme:9000/api/send -H "Content-Type: application/json"
```