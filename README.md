# Healthcheck Monitoring App

The Healthcheck Monitoring App is a backend service for a web-based application that monitors the health status of various services. Each service is checked using a URL, and the system determines whether it is healthy or unhealthy based on its response.

This application allows authenticated users to manage services safely, including adding new services for monitoring. Additionally, it features an alerting system to notify users of any changes in service status. The app also stores and tracks past health check results for analysis.

Built with Spring Boot, this application uses PostgreSQL as its database for storing service information and user data.

## API Endpoints

### URL Controller
| Method | Endpoint                    | Description                                         |
|--------|-----------------------------|-----------------------------------------------------|
| GET    | `/urls/{urlId}`             | Retrieve details of a specific URL.                 |
| PUT    | `/urls/{urlId}`             | Update the details of a URL.                        |
| DELETE | `/urls/{urlId}`             | Remove a URL.                                       |
| PUT    | `/urls/toggle/{urlId}`      | Enable or disable monitoring for a specific URL.    |
| PUT    | `/urls/healthcheck/{urlId}` | Manually trigger a health check for a specific URL. |
| GET    | `/urls`                     | Retrieve a list of all monitored URLs.              |
| POST   | `/urls`                     | Add a new URL.                                      |
| DELETE | `/urls`                     | Remove all URLs.                                    |
| GET    | `/urls/search`              | Search for URLs based on criteria.                  |

### Alert Mail Controller
| Method | Endpoint                          | Description                                            |
|--------|-----------------------------------|--------------------------------------------------------|
| GET    | `/email/{urlId}/emails`           | Retrieve emails that recive alerts for a specific URL. |
| PUT    | `/email/{urlId}/emails`           | Add an email for alerts to a specific URL.             |
| DELETE | `/email/{urlId}/emails/{emailId}` | Remove an email from a specific URL.                   |

### User Controller
| Method | Endpoint                   | Description                              |
|--------|----------------------------|------------------------------------------|
| POST   | `/users/register`          | Register a new user.                     |
| POST   | `/users/authenticate`      | Authenticate a user.                     |
| GET    | `/users`                   | Retrieve a list of all registered users. |
| GET    | `/users/exists/{username}` | Check if a specific username exists.     |
| DELETE | `/users/{urlId}`           | Remove a user by ID.                     |

### History Controller
| Method | Endpoint           | Description                                            |
|--------|--------------------|--------------------------------------------------------|
| GET    | `/history/{urlId}` | Retrieve the last 10 health checks for a specific URL. |
| DELETE | `/history/{urlId}` | Remove the health check history for a specific URL.    |
