# Parallel-Processing

Click in the badges below to see build and coverage information


![javaversion](https://img.shields.io/badge/Java-8-yellowgreen.svg)
![springboot](https://img.shields.io/badge/spring%20boot-1.5.9.RELEASE-orange.svg)
![server](https://img.shields.io/badge/server-undertow-yellow.svg)
![swagger](https://img.shields.io/badge/swagger-2.7.0-green.svg)
![googleformater](https://img.shields.io/badge/google%20format-1.5-blue.svg)




- _Technical information about the project is after the description_

Scenario for this application

#2: Parallel processing:

  - Write an application, which reads the content in HTML format from that URL: https://en.wikipedia.org/wiki/Europe

  - Afterwards each link in that article MUST be stored and followed up, reads again the content from the link found and stores all of the URLs found in the content

  - All of the processing MUST happen parallel

  - At the end, a file MUST be generated with a list of all URLs being found in all articles AND a counter how often they have been appeared.

  - The delivery MUST be a Java 8 project, which can be built and run using Maven

  
If you have any doubts about the project, please feel free to contact me at chtnazareth@gmail.com


  
## Instructions
  
To compile and run this project you will need:

  * **Java 8** (JDK8)
  * **Maven 3.0.5** or grater
  
#### Why Maven and not Gradle ?  

I have some Gradle projects as you can see in my Github, but some services I've used in this project 
do not work very well yet with gradle. So for this test I chose maven.


#### How to start the application and port   

Application port : **8080**  
 
To start the application use the command bellow   

```bash
mvn spring-boot:run
```


#### Application endpoint rest API

The base path for the endpoints is: **/api/v1/url-parser**
  - For this application we have:  **/api/v1/url-parser/generate-count-links-file** 
    - **/health** - Application's status
  - You can see more about the api at swagger
  
http://localhost:8080/swagger-ui.html 


#### Tests

To run all unit and integration tests use the command bellow   

```bash
mvn test
```  

- The application contains a built-in **MongoDB** database that is initialized along with application
  to save the feature toggle configuration    

#### Configs

At application.yml you can config:

  - The number of threads for generate the file : 
    ```
      parallelism:
        number: 1000
    ```  
  - The separator between links and counts  
      ```
        data:
          separator: ' , '    
      ``` 
  - The path and the name of the generated file
      ```
       file:
        path: 'links_counted.txt' 
      ``` 
  
#### Feature Toggle

The search for link of links is very time-consuming because of network latency. I put a feature toggle 
so that the inside links search can be turned off and the file can be generated faster.

To disable de feature toggle go to - > http://localhost:8080/ff4j-console/features     

  
# Architecture, tools and frameworks used

### Clean Architecture and Clean Code

#### Clean Code

Clean Code is a development style that focuses on the **ease of writing, reading and maintaining code**.

**Robert C. Martin**, in his book, "**Clean Code: A Handbook of Agile Software Craftsmanship**," 
states that the reading to writing ratio of the code is 10: 1. Therefore, a well-written code that 
facilitates reading is not only desirable, **but necessary in the current scenario**.

For this project I've used some clean code principles like :

* **Names are very important** : 
  * **Be precise**: _we must pass the central idea of ​​our variable or method, without turning, being concise 
    and direct_.
  * **Do not be afraid of big names**: _a very descriptive name, even if it is large, will enable a 
    better understanding and subsequent maintenance of the code_.
    
* **Comments only the necessary**
  * _Comment what is needed and only what is necessary. Codes are constantly modified, while comments rarely. 
    Thus, it is common for a comment to cease to have meaning, or worse, to pass on a false meaning after some time_.    

#### Clean Architecture

For this application I choose to use **Clean Architecture**

The Clean Architecture leverages well-known and not so well-known concepts, rules, and patterns, 
explaining how to fit them together, to propose a standardised way of building applications.

The core objectives behind Clean Architecture are the same as for Ports & Adapters (Hexagonal)
 and Onion Architectures:

* Independence of tools;
* Independence of delivery mechanisms;
* Testability in isolation.

In the [post](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) about 
Clean Architecture was published, this was the diagram used to explain the global idea:

![cleanarchitecture](https://8thlight.com/blog/assets/posts/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

**_The best of clean architecture is its use an software design technique to understand and solve 
complexity is Domain Driven Design (DDD). Domain Driven Design advocates modeling based on the 
reality of business as relevant to our use cases._** 

## Formatter

The code was formatted using [Google Format](https://github.com/google/google-java-format)


## APIs - Swagger

To document the APIs I used Swagger.

Swagger is the world’s largest framework of API developer tools for the OpenAPI Specification(OAS),
enabling development across the entire API lifecycle, from design and documentation, 
to test and deployment.

Here you can read more about [Swagger](https://swagger.io/)

To **see and test** the APIs go to path **/swagger-ui.html** (ex: _http://localhost:8080/swagger-ui.html_)

## UNDERTOW
I chose Undertow as web ser for this test. Why undertow?

Undertow is a web server designed to be used for both blocking and non-blocking tasks. 
Some of its main features are:

  * High Performance
  * Embeddable
  * Servlet 3.1
  * Web Sockets
  * Reverse Proxy

See more at In the [undertow](http://undertow.io/)


## MongoDB

I chose MongoDB as database for this test. Why MongoDB?

MongoDB is the database for today’s applications: innovative, fast time-to-market, globally scalable, 
reliable, and inexpensive to operate. With MongoDB, you can build applications that were never
possible with traditional relational databases.


See more at [MongoDB](https://www.mongodb.com/collateral/mongodb-architecture-guide)


## FF4J

Feature Toggle
Also called features flag, it allows to enable/disable features at runtime without deployment. 
During developments you implement multiple behaviours in your code. At runtime, the executed one is 
selected by evaluating conditions
http://ff4j.org/