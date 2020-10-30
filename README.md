# xDesign
xDesign - Technical Challenge

### Stack:
| Technology | Version |
|--|--|
| **Java** | 11.0.3-2018-01-14 |
| **Spring Boot** | 2.3.4.RELEASE |
| **Project Lombok** | 1.18.12 |
| **JUnit 4/5** | 4.1.5 - 5.6.2 |
| **Springfox Swagger 2** | 2.9.2 |

### Acessing Swagger | Open API:
Once with the application running:
http://localhost:8080/swagger-ui.html

Exists a Dockerfile prepared to download a OpenJDK 11 Slim and install the application.

- Run the command: `docker build -t xdesign/challenge:release .`
- Run the command: `docker run -p port:port <IMG_TAG>`
- Example: `docker run -p 8080:8080 8fb870f41548`
- Or download the image `docker pull samueldnc/samuelcatalano:xdesign`
