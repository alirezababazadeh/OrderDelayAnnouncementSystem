# Orders Delay Announcement System

## Setup Guideline

There are two ways to install the system:
```
Install Java and Maven on your system, then build the jar file and use Docker for other steps
Only Docker (slower)
```
### First way: Using Java and Maven and Docker

Make sure about installation of these tools:
```
java (17 or above)
maven
Docker and Docker Compose
```
Run this command:
```
.\mvnw clean package
```
* In Windows, move the target/*.jar files to the root directory. 
* In Linux and Mac, edit the docker-compose.yml file like this:

```
...
dockerfile: dockerfiles/Dockerfile
...
```
Run this command to use the system:
```
docker-compse up -d
```
### Second way: Only Docker

Edit this part of docker-compose file:
```
...
dockerfile: dockerfiles/Dockerfile-automate
...
```

Run this command to use the system:
```
docker-compse up -d
```

## Using the System

In order to use the system, goto this url:

```
http://localhost:8080/swagger.html
```
First run the MockController to generate the data for orders, trips and vendors. Then use the other APIs. 

- - -
© 2024 Evaluation Project