# Kafka stream API demo
## Overview

## How to run locally

### Create Kafka Topic for input
```
docker run \
  --net=host \
  --rm \
  confluentinc/cp-kafka:latest \
  kafka-topics --create --topic kafka-stream-test-input --partitions 3 --replication-factor 3 --if-not-exists --zookeeper localhost:32181
```

### Create Kafka Topic for output
```
docker run \
  --net=host \
  --rm \
  confluentinc/cp-kafka:latest \
  kafka-topics --create --topic kafka-stream-test-output --partitions 3 --replication-factor 3 --if-not-exists --zookeeper localhost:32181
```