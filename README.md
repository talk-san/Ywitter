# **Ywitter** - A Twitter-like API using Spring Boot

**Ywitter** is a pet project application inspired by Twitter, built using the Spring Framework. It provides similar functionalities such as posting tweets, following other users, and viewing a personalized feed.

## Features

- **User Authentication**: Secure user authentication using JSON Web Tokens (JWT).
- **Posting Tweets**: Users can post tweets and share their thoughts with their followers.
- **Following Users**: Users can follow other users to stay updated with their tweets.
- **Personalized Feed**: Users have a personalized feed showing tweets from users they follow.

## Technologies Used

- **Spring Boot**: A powerful framework for building Java applications, providing a fast and efficient way to develop robust, production-ready applications.
- **Spring Security**: Provides authentication and authorization support, ensuring secure access to the application's resources.
- **Spring Data JPA**: Simplifies database access and management by providing a high-level abstraction over JDBC.
- **Hibernate**: An ORM (Object-Relational Mapping) framework that simplifies data persistence in Java applications.
- **MySQL**: A popular relational database management system used for storing application data.
- **JSON Web Tokens (JWT)**: A compact, URL-safe means of representing claims to be transferred between two parties.
- **JUnit**: A unit testing framework for Java, used for testing individual units/components of the application.


## Getting Started

To get started with Ywitter, follow these steps:

### Clone the Repository:

` git clone https://github.com/your-username/ywitter.git`

### Set Up MySQL Database:

- Create a MySQL database named ywitter.
- Setup the environment variable for username and password for database connection

### Build and Run the Application:

`cd ywitter
./mvnw spring-boot:run`

### Use tools like Postman to interact with the API endpoints.

## License
This project is licensed under the MIT License
