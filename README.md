# Lumios Service

Lumios Service is a backend project for the Lumios app, built using Spring Boot. It provides various services including image analysis and book information retrieval.
Link to the frontend repo: https://github.com/michaelvbend/lumios-frontend
## Getting Started

### Installation Process

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/lumios-service.git
   cd lumios-service

2. Build the project using Maven:  
    ```sh
   mvn clean install
   
3. Run the application:  
   ```sh
   mvn spring-boot:run

## Software Dependencies
- Java 21
- Spring Boot 3.2.5
- PostgreSQL
- Maven

## Build and Test
1. To build the project, use:
   ```sh
     mvn clean install
2. To run tests, use:
   
   ```sh
   mvn test

## Dependencies
This project uses two main dependencies:  
- Google Vision API: Used to extract the ISBN from the book in the image.
- ISBNDB: Used to fetch the book title based on the extracted ISBN. This is necessary because a book can have multiple ISBNs.

## Contribute
Contributions are welcome! Please follow these steps:  
1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Make your changes.
4. Commit your changes (git commit -am 'Add new feature').
5. Push to the branch (git push origin feature-branch).
6. Create a new Pull Request.

## License
This project is licensed under the MIT License 