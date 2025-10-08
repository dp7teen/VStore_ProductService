# 📦 VStore - Product Service

This is the **Product Service** for the VStore microservices-based e-commerce platform. It manages all aspects of products, including CRUD operations, stock, ratings, and search. It uses **Redis** for caching to improve performance.

This service registers itself with the **Eureka Server** and is accessible via the **API Gateway**. It is a core part of the [VStore project](https://github.com/your-username/VStore).

---

## ⚙️ Core Technologies

* **Backend**: Java 17, Spring Boot
* **Database**: MySQL
* **Cache**: Redis
* **Security**: Spring Security, JWT
* **Service Discovery**: Spring Cloud Eureka Client

---

## 🔑 Environment Variables

* **`PORT`**: `9091`
* **`EUREKA_SERVER`**: URL of the Eureka registry.
* **`DB_URL`**: MySQL connection URL.
* **`DB_USERNAME`**: Database username.
* **`DB_PASSWORD`**: Database password.
* **`REDIS_HOST`**: Redis host or container name.
* **`REDIS_PORT`**: Redis port (default `6379`).
* **`JWT_SECRET`**: Secret key for validating JWT tokens.

---

## 🚀 API Endpoints

* **POST `/api/products/add`**: Adds a new product. (Access: ADMIN)
* **GET `/api/products/{id}`**: Retrieves a product by its ID. (Access: Authenticated)
* **PATCH `/api/products/update/{id}`**: Updates product details. (Access: ADMIN)
* **DELETE `/api/products/delete/{id}`**: Deletes a product. (Access: ADMIN)
* **GET `/api/products/stock/{id}`**: Gets the current stock for a product. (Access: Authenticated)
* **PUT `/api/products/update/{id}/{quantity}`**: Updates the stock of a product. (Access: Authenticated)
* **POST `/api/products/search`**: Searches for products. (Access: Authenticated)
* **POST `/api/products/rate/{id}/{rating}`**: Adds a new rating for a product. (Access: Authenticated)
* **PUT `/api/products/rate/{id}`**: Updates an existing rating. (Access: Authenticated)
* **DELETE `/api/products/rate/{id}`**: Deletes a rating. (Access: Authenticated)

---

## ▶️ How to Run

1.  Ensure **MySQL**, **Redis**, and the **VStore-EurekaServer** are running.
2.  Navigate to the `VStore-ProductService/` directory.
3.  Run the service using Maven:
    ```bash
    mvn spring-boot:run
    ```
4.  The service will register with Eureka and be available on port `9091`.
