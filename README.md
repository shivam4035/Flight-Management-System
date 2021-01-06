# Flight-Management-System
Spring boot learning project for searching and booking flights.
* Correctly built Hexagonal Architecture in Flight and User Service.
* Two Micro-Services 
 ## Flight Service
 * Admin can save, fetch, update and delete the flight using the basic CRUD operation.
 * Flights can be searched using Source, Destination, Journey Date, Class (Business/ Economy) and No. of Passengers.
 
 ## User Service
 * User can be able to search and book the preferrable flight by giving contact no. as unique Id.
 * Through Unique Id, user can also check the booking history.
 
# Tech Used
 * Feign Client has been used so that User Service can call the Flight Service to update the no. of seats when a booking has been made.
  1. Eureka Server - Spring Cloud Registry
  2. Zuul Api Gateway
  3. Spring Cloud Config Server - For saving the configuration files for fligth and user service.
  4. Coding Architecture - Hexagonal Architecture which includes 4 different modules
   - Adapter -> the controller layer
   - Api -> storing the endpoints and for feign client
   - Core -> for business logic (service layer)
   - Deploy -> for making the service up
   
* Database Used - MongoDB   
* Test Suites - Unit, Slice and Component Tests are included.


# For running
 * Clone the whole project from the master branch.
 * Up the Eureka Service, followed by Spring Cloud Config Server.
 * Next Up the Flight Service -> User Service -> Api Gateway
 * Explore the endpoints on postman.
