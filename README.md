# Kafka Basics

This project is based on starting with Apache Kafka with Java API, Kafka Terminal CLI and using the Conduktor platform for better understanding of Kafka concepts like partitioning, topics, and consumer groups. The Conduktor Docker Kafka dashboard UI is used to visualize and interact with Kafka more effectively.

- [Conduktor](https://conduktor.io/)
- [Learn about Apache Kafka](https://learn.conduktor.io/kafka/what-is-apache-kafka/)

## Getting Started with Kafka

Apache Kafka is installed without Zookeeper using KRaft mode, as Kafka 4.0 and beyond will no longer require Zookeeper. Below are some useful links for installation and understanding Kafka.

- [Start with Kafka](https://learn.conduktor.io/kafka/starting-kafka/)
- [How to Install Apache Kafka on Mac without Zookeeper? (KRaft mode)](https://learn.conduktor.io/kafka/how-to-install-apache-kafka-on-mac-without-zookeeper-kraft-mode/)

## Kafka Terminal CLI

To interact with Kafka via the command line, you can follow the guide below:

- [Kafka Terminal CLI](https://learn.conduktor.io/kafka/kafka-cli-tutorial/)

## Java API

If you're programming with Kafka in Java, refer to this guide:

- [Apache Kafka with JAVA API](https://learn.conduktor.io/kafka/java-kafka-programming/)

## Kafka Dashboard UI

To make Kafka interaction simpler, Conduktor provides a Kafka Dashboard UI that runs in Docker. You need Docker installed on your local machine to run the Conduktor UI Dashboard on `localhost:8080`. Use the following command to set up the dashboard:

```bash
curl -L https://releases.conduktor.io/quick-start -o docker-compose.yml && docker compose up -d --wait && echo "Conduktor started on http://localhost:8080"

```

For further details, visit:

- [Conduktor Docker](https://conduktor.io/get-started)

This README gives an overview of Kafka basics, how to install and work with Kafka using the Conduktor platform, and links for further resources.
