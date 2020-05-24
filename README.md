# Network of Giving

## Setup

### Database setup

Requirements: Docker installation
* Download the [PostgreSQL Docker YAML configuration file](./setup/postgreSQL-image.yml)
* From a terminal, navigate to the directory where the file was downloaded
* Run the command:

```console
docker-compose -f postgreSQL-image.yml up
```

### Server setup

Requirements: IntelliJ IDEA
* Open the [pom.xml file](./server/network-of-giving/pom.xml) with IntelliJ IDEA
* Run the main method from the [NetworkOfGivingApplication class](./server/network-of-giving/src/main/java/com/example/networkofgiving/NetworkOfGivingApplication.java)

### Client setup

Requirements: Node.js
* From a terminal, navigate to the [Angular client project directory](./client/network-of-giving-client)
* Run the commands:

```console
npm install
npm start # if you don't have Angular CLI it will use the one from node_modules
```