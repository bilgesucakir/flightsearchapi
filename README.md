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
- Database create table statements are given as example in *flightsearchapi/database_related* folder

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
- Airport-Flight: one-to-many
- User-Role: many-to-many

## Endpoints

### Airport CRUD Endpoints
##### (available for admins)
- **GET** api/airports: Retrieve a list of all airports.
- **GET** api/airports/{airportId}: Retrieve details of a specific airport by ID.
- **POST** api/airports: Create a new airport.
- **PUT** api/airports/{airportId}: Update details of a specific airport by ID.
- **DELETE** api/airports/{airportId}: Delete a specific airport by ID.

### Flight CRUD Endpoints
##### (available for admins)
- **GET** api/flights: Retrieve a list of all flights.
- **GET** api/flights/{flightId}: Retrieve details of a specific flight by ID.
- **POST** api/flights: Create a new flight.
- **PUT** api/flights/{flightId}: Update details of a specific flight by ID.
- **DELETE** api/flights/{flightId}: Delete a specific flight by ID.

### Auth Endpoints
##### (no restriction on availability)
- **POST** api/auth/register: Register a new user.
- **POST** api/auth/login: Authenticate and receive a JWT token (valid for 15 minutes).

### Flight Search Endpoints
##### (available for admins and users)
- **GET** api/search/flights
  - ?departureCity=""&arrivalCity=""&departureDate=yyyy-mm-dd: Retrieve one-way flights according to filters.
  - ?departureCity=""&arrivalCity=""&departureDate=yyyy-mm-dd&returnDate=yyyy-mm-dd: Retrieve two-way flights according to filters.

## Security
The API uses JWT for authentication. Include the obtained JWT token in the Authorization header for secured endpoints.
- In Postman, select Bearer token in **authorization** tab
- In Swagger UI, enter the token in **authorize** section

##### Role-based Access
- Users have the USER role.
- Admins have the ADMIN and USER role.

Passwords are stored in database with BCrypt encoding

## Documentation
After running, the Swagger OpenAPI UI is available at http://localhost:8080/swagger-ui/index.html or you can get the JSON version from http://localhost:8080/api-docs

## Additional Notes
To try out the endpoints, you can use already registered users (3 admin, 1 user).
You can reach their credentials in *flightsearchapi/registered_users_information* folder