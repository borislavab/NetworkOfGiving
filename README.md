# Network of Giving

## Setup

### Database setup

Requirements: Docker installation
1. Download the [PostgreSQL Docker YAML configuration file](./setup/postgreSQL-image.yml)
2. From a terminal, navigate to the directory where the file was downloaded
3. Run the command:

```bash
docker-compose -f postgreSQL-image.yml up
```

### Server setup

Requirements: IntelliJ IDEA
1. Open the [pom.xml file](./server/network-of-giving/pom.xml) with IntelliJ IDEA
2. Run the main method from the [NetworkOfGivingApplication class](./server/network-of-giving/src/main/java/com/example/networkofgiving/NetworkOfGivingApplication.java)

### Client setup

Requirements: Node.js
1. From a terminal, navigate to the [Angular client project directory](./client/network-of-giving-client)
2. Run the following commands:

    - If you have Angular CLI:

        ```bash
        npm install
        ng serve
        ```

    - If you don't have Angular CLI the following commands will use the one from node_modules:

        ```bash
        npm install
        npm start
        ```

The application can be accessed via browser at http://localhost:4200

