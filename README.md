# Challenge

## :computer: How to execute

_Provide a description of how to run/execute your program..._

- `docker compose up -d` to start the database, kafka...
- `cd teschtest` to go to the project folder
- `mvn clean install` compile and run the tests
- `mvn spring-boot:run` to start the application
- `http://localhost:9000` to run the test

## :memo: Notes
_Some notes or explanation of your solution..._

A Spring Boot app with Java reading from two kafka topics, verifying and persisting stuff.
 
`DEBUG` is set as the default for the app code, if you don't want to see that change it back to `INFO` in the `application.yaml`
## :pushpin: Things to improve

_If u have more time or want to improve something..._
 
- Integration test (testcontainers)
- Circuit breaker to stop kafka consuming when an API is down (/log)
- More unit test coverage
- Better error handling
- Two application.yaml with profiles to append the correct config to the docker compose build and run everything from `docker compose up`