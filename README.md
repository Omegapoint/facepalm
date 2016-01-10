# Facepalm

Most 'insecure' test web applications today does not feel like a real application, whether it's DWNA (PHP) or WebGoat etc. Facepalm is intended to be just that, a real application which happens to have severe security vulnerability. The application is a fused clone of Instagram, Facebook and 9gag

The application is written in Java 8 together with Spring Boot. As the source code is fully available, the application is suitable for workshops  where one learns to detect and solve security issues.

##### Technologies
Some of the technologies used
* Java 8
* Spring Boot (and the rest of the Spring family)
* Thymeleaf (for template rendering)
* Gradle (for dependecy management)
* Hibernate/JPA (for ORM/database)
* HSQLDB (for in-memory database)

##### Attacks
Currently, the following (at least) vulnerabilities are known:
 * SQL injection (both GET (url) and POST (form) requests)
 * XSS attack
 * Command (line) execution

More are beging worked on.  :+1:

## I just want to test it!
There is a jar available at the root of the repository (which can be downloaded directly from Github page).
The jar might be out of date since it will most likely not be refreshed on each commit. Just at on risk, best is always to build yourself!

## Setup
#### Editor (IntelliJ)
1. Clone the repository
2. Open cloned folder inside editor
3. Open "Application.java", run main method

#### Standalone (no editor)
1. Clone the repository
2. Build using gradle
```gradle
gradle build
```
3. Copy build/libs/facepalm-x.x.x-SNAPSHOT.jar to some directory
4. Copy 'docs'-folder to same directory
5. Start application with
```sh
java -jar facepalm-x.x.x-SNAPSHOT.jar
```

## SQL Injection
It is possible to perform SQL injections at multiple places in the applications.
An useful query can be
```sql
' OR 1=1; --'
```

A tool that might come handy is SQLMap. The image details page looks suitable.

The vulnerability can be investigated  for example in JPAUserRepository or JPAImageRepository.java. Try to study the code first and then try exploiting the application

## XSS

There are some areas that can be exploited using stored cross site scripting. A sample code that can be useful is
```html
<script>alert("Hello")</script>
```

Most modern rendering frameworks handles proper output encoding for you. However, some field have the automatic encoding deactivated.

## Command execution
Command execution is a vulnarability where the attacker can perform an arbitrary command on the attacked system. An example of this exploit is when the application reads file from disk using shell commands. Using shell/cmd syntax knowledge, sample attacks could be
1. *nix host
```sh
; cat /etc/passwd
```
2. Windows host
```sh
%26dir
```

## Links

 * [Omegapoint AB](https://www.omegapoint.se)
