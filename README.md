![Facepalm][facepalm img]
[![][license img]][license]

Most 'insecure' test web applications today does not feel like a real application, whether it's DWNA (PHP) or WebGoat etc. Facepalm is intended to be just that, a real application which happens to have severe security vulnerability. The application is a fused clone of Instagram, Facebook and 9gag

The application is written in Java 8 together with Spring Boot. As the source code is fully available, the application is suitable for workshops where one learns to detect and solve security issues.

**Please note that the security vulnerabilities are intended and should not be fixed by future contributers.**



## Workshops
As this application is intended for workshops, some existing workshops can be found in the workshop directory.

Current workshops are:
* SQL Injection
* Cross-site scripting attacks (XSS)
* Command Execution

## I just want to test it!
There is a jar available at the root of the repository (which can be downloaded directly from Github page).
The jar might be out of date since it will most likely not be refreshed on each commit. Just at on risk, best is always to build yourself!

## Technologies
Some of the technologies used
* Java 8
* Spring Boot (and the rest of the Spring family)
* Thymeleaf (for template rendering)
* Gradle (for dependecy management)
* Hibernate/JPA (for ORM/database)
* HSQLDB (for in-memory database)

## Setup for developers
#### Editor (IntelliJ)
1. Clone the repository
2. Open cloned folder inside editor
3. Open "Application.java", run main method
4. Open a browser and go to [http://localhost:8080](http://localhost:8080/)

#### Standalone (no editor, using start script)
1. Clone the repository
2. Start application with
```sh
./run_application.sh
```
3. Open a browser and go to [http://localhost:8080](http://localhost:8080/)

#### Standalone (no editor)
1. Clone the repository
2. Build using gradle
```gradle
./gradlew build
```
3. Copy build/libs/facepalm-x.x.x-SNAPSHOT.jar to some directory
4. Copy 'docs'-folder to same directory
5. Start application with
```sh
java -jar facepalm-x.x.x-SNAPSHOT.jar
```
6. Open a browser and go to [http://localhost:8080](http://localhost:8080/)




## Links

 * [Omegapoint AB](https://www.omegapoint.se)


[license]:LICENSE
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg
[facepalm img]:facepalm.png