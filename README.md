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

**Docker:**
Exists a Dockerfile prepared to download a OpenJDK 11 Slim and install the application.

- Run the command: `docker build -t xdesign/challenge:release .`
- Run the command: `docker run -p port:port <IMG_TAG>`
- Example: `docker run -p 8080:8080 8fb870f41548`
- Or download the image `docker pull samueldnc/samuelcatalano:xdesign`

### How to run the application:
> IDE (IntelliJ, Eclipse, NetBeans):
- Importing the project as Maven project on your favourite IDE.
- Build project using Java 11
- Run/Debug project from Main Application Class :: XDesignTestApplication

> Terminal:
- `mvn spring-boot:run`

### How to run the tests?

> Terminal:
- `mvn test`


### APIs:

The basic URL path is: http://localhost:8080/api/xdesign/munros

> Returns a specific Munro by RunningNo where **/{runningNo}** is positive integer value
* GET: http://localhost:8080/api/xdesign/munros/1

> Returns a list of Munros according the criteria:
* GET: http://localhost:8080/api/xdesign/munros?minHeight=1000&maxHeight=1050&orderHeightBy=ASC&category=MUN&orderNameBy=DESC

> Criteria examples:
- limit=10 - positive integer value | required = false
- minHeight=983  - positive double value | required = false
- maxHeight=1176.3 - positive double value | required = false
- orderNameBy=ASC - string value "ASC" or "DESC" | required = false
- orderHeightBy=DESC - string value "ASC" or "DESC" | required = false

#### It is possible to combine all the criteria as shown in the example above.
