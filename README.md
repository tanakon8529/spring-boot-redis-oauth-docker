
# Spring Boot Redis OAuth Docker

This project showcases a Spring Boot application that integrates with Redis for token storage and is containerized using Docker. It demonstrates how to set up OAuth2 authentication with Redis as the backend for token storage.

## Tech Stack

- Java
- Spring Boot
- Redis
- Docker

## Project Structure

```plaintext
spring-boot-redis-oauth-docker/
├── docker-compose.yml
├── Dockerfile
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── config/
│   │   │           │   ├── RedisConfigChecker.java
│   │   │           │   └── RedisConfig.java
│   │   │           ├── controller/
│   │   │           │   ├── AuthController.java
│   │   │           │   ├── HealthController.java
│   │   │           │   └── ProtectedController.java
│   │   │           ├── security/
│   │   │           │   ├── SecurityConfig.java
│   │   │           │   └── UUIDAuthenticationFilter.java
│   │   │           └── SpringBootRedisOauthDockerApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── controller/
│                       ├── AuthControllerTest.java
│                       └── HealthControllerTest.java
└── target/
```

## Description

This project is a simple Spring Boot application demonstrating the use of OAuth2 for authentication, with Redis for storing tokens. It includes Docker configurations to containerize the application and run it along with a Redis instance.

## How to Build

1. **Clone the repository**:
    ```bash
    git clone https://github.com/tanakon8529/spring-boot-redis-oauth-docker.git
    cd spring-boot-redis-oauth-docker
    ```

2. **Build the project**:
    ```bash
    ./mvnw clean package
    ```

## How to Start Docker

1. **Build and start the Docker containers**:
    ```bash
    docker-compose up --build
    ```

2. **Check the running containers**:
    ```bash
    docker ps
    ```

    You should see two containers running: one for the Spring Boot application and one for Redis.

## Endpoints

- **/auth**: Endpoint to authenticate and get a token.
- **/health**: Health check endpoint.
- **/protected**: Protected endpoint that requires a valid token.

## Example Requests

1. **Get a token**:
    ```bash
    curl -X POST "http://localhost:8080/auth" -H "clientId: yourClientId" -H "clientSecret: yourClientSecret"
    ```

2. **Access protected endpoint with token**:
    ```bash
    curl -X GET "http://localhost:8080/protected" -H "Authorization: Bearer yourToken"
    ```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
