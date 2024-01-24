# Flight Search API

## Overview

Flight Search API is a RESTful Spring Boot web service that provides flight-related information. It includes functionality for searching flights, managing user roles, registering and logging in for admin and users, performing CRUD operations on flight and airport entities.

## Technologies Used

- Java
- Spring Boot
- MySQL
- Hibernate
- JPA
- JWT (JSON Web Tokens)
- Spring Security

## Database

- The application uses MySQL as its database (Azure Database for MySQL - Flexible Server)
- MySQL Workbench is used to monitor data 
- Database create table statements are given as example in [*flightsearchapi/database_related*](https://github.com/bilgesucakir/flightsearchapi/tree/master/database_related) folder

## Entities

### Airport
- id (int)
- city (String)

### Flight
- id (int)
- departureAirport (Airport)
- arrivalAirport (Airport)
- departureDateTime (LocalDateTime)
- arrivalDateTime (LocalDateTime)
- price (BigDecimal)

### User
- id (int)
- username (String)
- password (String)

### Role
- id (int)
- name (String)

### Entity Relationships
- Airport-Flight: one-to-many (bi directional)
- User-Role: many-to-many

## Endpoints and Requests

### Airport CRUD Endpoints and Requests
##### (available for admins)
- **GET** api/airports: Retrieve a list of all airports.
- **GET** api/airports/{airportId}: Retrieve details of a specific airport by ID.
- **POST** api/airports: Create a new airport.
  - body:
  ```
  {
    "id": 0,
    "city": "string"
  }
  ```  
- **PUT** api/airports/{airportId}: Update details of a specific airport by ID.
  - body:
  ```
  {
    "id": 0,
    "city": "string"
  }
  ```  
- **DELETE** api/airports/{airportId}: Delete a specific airport by ID.

### Flight CRUD Endpoints and Requests
##### (available for admins)
- **GET** api/flights: Retrieve a list of all flights.
- **GET** api/flights/{flightId}: Retrieve details of a specific flight by ID.
- **POST** api/flights: Create a new flight.
  - body:
  ```
    {
      "id": 0,
      "departureAirportId": 0,
      "arrivalAirportId": 0,
      "departureDateTime": "2024-01-03T10:52:20.363Z",
      "arrivalDateTime": "2024-01-03T10:52:20.363Z",
      "price": 0
    }
  ```   
- **PUT** api/flights/{flightId}: Update details of a specific flight by ID.
  - body:
  ```
    {
      "id": 0,
      "departureAirportId": 0,
      "arrivalAirportId": 0,
      "departureDateTime": "2024-01-03T10:52:20.363Z",
      "arrivalDateTime": "2024-01-03T10:52:20.363Z",
      "price": 0
    }
  ```
    
- **DELETE** api/flights/{flightId}: Delete a specific flight by ID.

### Auth Endpoints and Requests
##### (no restriction on availability)
- **POST** api/auth/register: Register a new user.
  - body:
  ```
    {
      "username": "string",
      "password": "string"
    }
  ```
- **POST** api/auth/login: Authenticate and receive a JWT token (valid for 15 minutes).
  - body:
  ```
    {
      "username": "string",
      "password": "string"
    }
  ```
    
### Flight Search Endpoints and Requests
##### (available for admins and users)
- **GET** api/search/flights
  - ?departureCity=*cityName*&arrivalCity=*cityName*&departureDate=*yyyy-mm-dd*: Retrieve one-way flights according to filters.
  - ?departureCity=*cityName*&arrivalCity=*cityName*&departureDate=*yyyy-mm-dd*&returnDate=*yyyy-mm-dd*: Retrieve two-way flights according to filters.

## Security
The API uses JWT for authentication. JWT tokens are provided in the response body of a login request. Include the obtained JWT token in the Authorization header before sending a reuqest. JWT token is required for all handled endpoints except register and login. 
- In Postman, select Bearer token in **Authorization** tab
- In Swagger UI, enter the token in **Authorize** section

##### Role-based Access
- Users have the role USER.
- Admins have the roles ADMIN and USER.

Passwords are stored in the database table named *roles* with BCrypt encoding

## Running the Project
- Use an IDE to run the project
  - IntelliJ IDEA
  - etc.
- Open a terminal window and navigate to the root of the project
  - Build the project:
  ```
    mvn clean install
  ```
  - Run the project:
  ```
    mvn spring-boot:run
  ```

## Documentation
After running, the Swagger OpenAPI UI is available [here](http://localhost:8080/swagger-ui/index.html) or you can get the JSON version from [here](http://localhost:8080/v3/api-docs)

## Additional Notes
To try out the endpoints, you can use already registered users (3 admin, 1 user).
You can reach their credentials in [*flightsearchapi/registered_users_information*](https://github.com/bilgesucakir/flightsearchapi/tree/master/registered_users_information) folder
