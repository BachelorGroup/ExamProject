# ExamProject

This is the group project for the 6100 Enterprise 2 exam, by the group SOJ

## Welcome to our exam project

We have a few different modules, here are those listed and who has worked on what

* [Sebusk](https://github.com/Sebusk)
    * auth
    * user-service
    * user-service-dto
* [kveola/anonexam](https://github.com/Kveola13)
    * movie
* [Hundur](https://github.com/Hundur)
    * ticket
    * eureka
    * scg (with Sebusk)
    
### How to run the application

When you first clone the repo, you need to do a `mvn install` or a `mvn clean package` to 
get the jar files needed to run the docker-compose. Once you've done this, open up 
a terminal like git bash or cmd in the root folder, and run `docker-compose up -d`. We've had 
issues on some computers with this, so if this ends up not working, you can run the modules 
manually. 

To do this you need to rightclick the `Application.kt` file in each module, and run them. 
This has to be done in a specific order; first you run the `EurekaApplication.kt` which will
launch the eureka server. Wait until this is done, then run the rest of the modules. Once
they've finished, you'll see them on the eureka server on [http://localhost:8761/](http://localhost:8761/).


Each module also comes with tests, to run these you go to the module, and down the `test/` path,
in here you will rightclick and press run, if you want to see the coverage of the programs, press
`run with coverage`. Coverage is about 90% for each module.

### Api documentation
To use swagger, we now have to authenticate using:
```
username: admin
password: admin
```
[Eureka](http://localhost:8761/)

[Swagger link for ticket-service](http://localhost:8081/swagger-ui.html) 

[Swagger link for auth-service](http://localhost:8082/swagger-ui.html) 

[Swagger link for movie-service](http://localhost:8083/swagger-ui.html) 

[Swagger link for user-service](http://localhost:8084/swagger-ui.html) 


### What the project does

The project exposes and endpoint to which you can do different HTTP calls. CRUD on databases, mimicking a movie theater application.

### Structure

The project consists of several micro services. The REST services have their own databases.
The scg module is the gateway of the application, hiding the actual endpoints of our services.
Our micro services are connected the Eureka, which means they are in a Service Discovery and can communicate with each other.
The docker-compose file will start the application and each respective micro service. It packages the modules based on their own docker and application.yml files.
In the application.yml files you can see how each service register themselves to Eureka.
Each API has swagger documentation which can be easily accessed by the links above. There is high test coverage on all REST APIs

### How we implemented it

We started off before we had had the classes regarding micro services, therefore it did start off as a monolithic application.
We worked with it as a monolithic app, implementing a lot of features still during that stage. We created our own entities, dtos, repos, apis and so on.
During the exam period we did have a huge amount of work on other exams therefore there was not much room for work for at least a month.

When we started working on the project again after our last exam, we first started off taking our own components and putting them into our own modules. 
After this refactoring, we started trying to work out how to set up the gateway(scg) and service discovery(eureka).
With these new changes we had to add some new files to our modules as well; a Docker file and an application.yml file containing some things needed for Eureka and scg.
We first got each service up and running with eureka manually, then after we started working our way towards making Docker-Compose work.

### Different technologies that are used

* Spring boot 2.0
* Spring Security
* Spring Cloud Gateway
* Netflix Eureka
* Redis(further impl)
* Maven
* Swagger2
* Micro Service Architecture
* Docker
* REST
* Databases
    * Postgres
    * Hibernate
* RestAssured
* Kotlin