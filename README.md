# Digital Delights Backend
 
Hello everyone! My name is Jayln Ramos and this is my final capstone for LTCA. This is a Spring Boot REST API for an e-commerce platform. Supports user authentication, product/category browsing, a shopping cart, and order placement.
 
## Tech stack
 
- **Java 17**

- **Spring Boot 4.0.2** — web, security, validation, data JPA

- **MySQL** — persistence (via `mysql-connector-j`)

- **JWT** (`io.jsonwebtoken`) — stateless authentication

- **Springdoc / Swagger UI** — interactive API docs

- **JUnit + H2** — in-memory testing

- **Maven** — build and dependency management
 
## Project structure
 
```

org.yearup

├── ECommerceApplication.java       # Spring Boot entry point

├── controllers/                    # REST endpoints

├── models/                         # Entities + auth DTOs (models/authentication)

├── repository/                     # Spring Data JPA repositories

├── security/                       # Auth config + JWT handling (security/jwt)

└── service/                        # Business logic layer

```
 
## API overview
 
| Resource | Endpoints |

|---|---|

| **Auth** | `POST /login`, `POST /register` |

| **Categories** | `GET /categories`, `GET /categories/{id}`, `GET /categories/{id}/products`, `POST /categories`, `PUT /categories/{id}`, `DELETE /categories/{id}` |

| **Products** | `GET /products`, `GET /products/{id}`, `POST /products`, `PUT /products/{id}`, `DELETE /products/{id}` |

| **Profile** | `GET /profile`, `PUT /profile` |

| **Shopping cart** | `GET /cart`, `POST /cart/products/{productId}`, `PUT /cart/products/{productId}`, `DELETE /cart` |

| **Orders** | `POST /orders` |
 
Full request/response details are available via Swagger UI once the app is running, or through the included Insomnia collection.
 
## Getting started and Setup
 
1. Clone the repo and open `capstone-api-starter` in IntelliJ (or your IDE of choice).

2. Create a local MySQL database (the app expects one named `videogamestore` by default, or set the `DB_NAME` environment variable to override it).

3. Update the datasource credentials and JWT secret in `application.properties` — **don't commit real credentials to version control.** Consider moving these to
 
