# Project Management System with JWT 

## Table of Contents  
- [Overview](#overview)  
- [Classes](#classes)  
  - [Model Classes](#model-classes)  
  - [DTOs](#dtos)  
  - [Controllers](#controllers)  
  - [Services](#services)  
  - [Configuration](#configuration)  
- [Database](#database)  
- [Dependencies](#dependencies)  

## Overview  
This project is a User and Project Management System built using Spring Boot. It supports user authentication and authorization based on defined roles (CEO, TeamLeader, TeamMember) and manages projects associated with users. The system uses PostgreSQL as the database and implements security features using JWT tokens.  

## Classes  

### Model Classes  
1. **User**  
   - Represents a user in the system with attributes such as id, firstName, secondName, email, password, and role.  
   - Implements `UserDetails` for security purposes.  

2. **Role**  
   - Enum that defines the different roles available in the system: CEO, TeamLeader, TeamMember.  

3. **Project**  
   - Represents a project with attributes such as id, title, company, description, and status.  

### DTOs  
1. **UserDTO**  
   - Data Transfer Object for user information.  

2. **ProjectDTO**  
   - Data Transfer Object for project information.  

3. **SignUpRequest**  
   - Contains data required for signing up a new user.  

4. **SigninRequest**  
   - Contains data required for signing in a user.  

5. **JwtAuthenticationResponse**  
   - Response object containing JWT token and user information.  

6. **RefreshTokenRequest**  
   - Contains data for refreshing the JWT token.  

### Controllers  
1. **AuthenticationController**  
   - Handles user sign-up, sign-in, and token refresh requests.  

2. **ProjectController**  
   - Manages project-related requests (create, update, delete, retrieve).  

3. **UserController**  
   - Manages user-related requests (create, update, delete, retrieve).  

### Services  
1. **AuthenticationService**  
   - Interface for handling user authentication.  

2. **JWTService**  
   - Interface for managing JWT operations (generation, validation, extraction).  

3. **UserService**  
   - Interface for user-related operations.  

4. **ProjectService**  
   - Interface for project-related operations.  

5. **AuthenticationServiceImpl**  
   - Implementation of AuthenticationService.  

6. **UserServiceImpl**  
   - Implementation of UserService.  

7. **ProjectServiceImpl**  
   - Implementation of ProjectService.  

8. **UserDetailsServiceImpl**  
   - Implementation of user details service required for security.  

### Configuration  
1. **SecurityConfiguration**  
   - Configures Spring Security settings, JWT filter, and authentication provider.  

2. **JwtAuthenticationFilter**  
   - Filter that processes JWT tokens for incoming requests.  

3. **CEOConfiguration**  
   - Contains specific configurations for CEO level functionalities.  

## Database  
The project uses PostgreSQL as the database. Ensure you have a PostgreSQL instance running and create a database for this application.  

## Dependencies
This project requires the following dependencies in the `build.gradle` file:  

- `spring-boot-starter-web`  
- `spring-boot-starter-security`  
- `spring-boot-starter-data-jpa`  
- `postgresql`  
- `spring-boot-starter-validation`  
- `spring-boot-starter-json`  
- `spring-security-config`  
- `spring-security-web`  
- `spring-security-core`  
- `spring-boot-starter-jwt`  
