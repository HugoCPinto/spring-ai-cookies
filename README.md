# spring-ai-cookies

## Description
The purpose of this project is to demonstrate the use of SpringBoot and AI technologies to create an API RESTful service that can be used 
to interact with a database of cookies.
In this example, instead of passing filters like available cookie to filter the list of cookies, we are using an AI model to predict the
query that user is trying to do without give the full control of the query to the user.

## Technologies
- Java 21
- SpringBoot
- Spring framework AI
- Spring Data JPA
- H2 Database
- Ollama model
- Maven

## How to run
1. Clone the repository
2. Install Ollama model (https://ollama.com/) and install the model llama3
3. Run the project using the command `mvn spring-boot:run`
4. Access the API using the URLs below

| HTTP Method | Endpoint           | Description                                  |
|-------------|--------------------|----------------------------------------------|
| GET         | /cookies           | Retrieve all cookies (without AI)            |
| GET         | /cookies/available | Retrieve all cookies available (without AI)  |
| GET         | /cookies-ai?query= | Retrieve all cookies filtered (with AI)      |

## Example
1. Retrieve all cookies
```bash
curl -X GET http://localhost:8080/cookies
```

2. Retrieve all cookies available
```bash
curl -X GET http://localhost:8080/cookies/available
```

3. Retrieve all cookies filtered
```bash
curl -X GET http://localhost:8080/cookies-ai?query=show me all cookies available
```
