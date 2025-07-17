# 🛒 Spring E-commerce API

A comprehensive e-commerce REST API built with Spring Boot 3, featuring JWT authentication, role-based authorization, and complete product, cart, and order management functionality.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.5-blue.svg)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red.svg)
![Swagger](https://img.shields.io/badge/Swagger-3.0-brightgreen.svg)

## 📋 Table of Contents
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Authentication](#-authentication)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#-database-schema)
- [Testing](#-testing)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

### 🔐 Authentication & Authorization
- **JWT-based authentication** with secure token generation
- **Role-based access control** (USER/ADMIN roles)
- **Password encryption** using BCrypt
- **Stateless session management**

### 📦 Product Management
- **CRUD operations** for products
- **Category filtering** for product listings
- **Stock management** with real-time updates
- **Admin-only** product management

### 🛒 Shopping Cart
- **Add/remove items** from cart
- **Quantity management** with stock validation
- **User-specific cart isolation**
- **Real-time price calculations**

### 📋 Order Management
- **Create orders** from cart or custom items
- **Order history** with detailed item information
- **Automatic stock deduction** upon order creation
- **Order totals and item count** calculations

### 📚 Documentation
- **Interactive Swagger UI** for API exploration
- **OpenAPI 3.0 specification**
- **Comprehensive endpoint documentation**
- **Request/response examples**

## 🛠 Technology Stack

### Backend
- **Spring Boot 3.5.3** - Main framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM
- **JWT (JSON Web Tokens)** - Stateless authentication

### Database
- **PostgreSQL 17.5** - Primary database (Docker container)
- **H2** - In-memory database (for testing)

### Documentation
- **SpringDoc OpenAPI 3** - API documentation
- **Swagger UI** - Interactive API explorer

### Build & Development
- **Maven** - Dependency management
- **Java 21** - Programming language
- **Spring Boot DevTools** - Development utilities

## 🏗 Architecture

```
src/main/java/com/esataydin/
├── auth/                     # Authentication module
│   ├── controller/          # Auth endpoints
│   ├── dto/                 # Data transfer objects
│   ├── exception/           # Custom exceptions
│   ├── security/            # Security configuration & JWT
│   └── service/             # Authentication business logic
├── cart/                    # Shopping cart module
│   ├── controller/          # Cart endpoints
│   ├── dto/                 # Cart DTOs
│   ├── entity/              # Cart entities
│   ├── exception/           # Cart exceptions
│   ├── repository/          # Cart data access
│   └── service/             # Cart business logic
├── order/                   # Order management module
│   ├── controller/          # Order endpoints
│   ├── dto/                 # Order DTOs
│   ├── entity/              # Order entities
│   ├── exception/           # Order exceptions
│   ├── repository/          # Order data access
│   └── service/             # Order business logic
├── product/                 # Product management module
│   ├── controller/          # Product endpoints
│   ├── dto/                 # Product DTOs
│   ├── entity/              # Product entities
│   ├── exception/           # Product exceptions
│   ├── repository/          # Product data access
│   └── service/             # Product business logic
├── user/                    # User management module
│   ├── entity/              # User entities
│   ├── repository/          # User data access
│   └── service/             # User business logic
├── config/                  # Application configuration
│   └── SwaggerConfig.java   # Swagger/OpenAPI config
└── SpringEcommerceApiApplication.java
```

## 🚀 Getting Started

### Prerequisites
- **Java 21** or higher
- **Maven 3.6+**
- **Docker & Docker Compose** (for PostgreSQL database)
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Esataydin/Spring-Ecommerce-API.git
   cd Spring-Ecommerce-API
   ```

2. **Configure the database**
   
   **Option 1: PostgreSQL with Docker (Recommended)**
   
   Create a `docker-compose.yml` file in the project root:
   ```yaml
   version: '3.8'
   services:
     postgres:
       image: postgres:latest
       container_name: ecommerce_db
       environment:
         POSTGRES_DB: ecommerce_db
         POSTGRES_USER: ecommerce_user
         POSTGRES_PASSWORD: ecommerce_password
       ports:
         - "5432:5432"
       volumes:
         - postgres_data:/var/lib/postgresql/data
   
   volumes:
     postgres_data:
   ```
   
   Start the PostgreSQL container:
   ```bash
   docker-compose up -d
   ```
   
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
   spring.datasource.username=ecommerce_user
   spring.datasource.password=ecommerce_password
   ```

   **Option 2: H2 (For development)**
   ```properties
   # Uncomment H2 configuration in application.properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   ```

3. **Install dependencies and run**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   - **API Base URL:** `http://localhost:8080`
   - **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
   - **H2 Console:** `http://localhost:8080/h2-console` (if using H2)

## 📚 API Documentation

### Swagger UI
Access the interactive API documentation at:
**http://localhost:8080/swagger-ui/index.html**

### OpenAPI Specification
Get the raw OpenAPI JSON at:
**http://localhost:8080/v3/api-docs**

## 🔐 Authentication

### Registration
```bash
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "USER"
}
```

### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

### Using JWT Token
Include the JWT token in the Authorization header:
```bash
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
```

## 🔌 API Endpoints

### 🔐 Authentication
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/api/auth/register` | Register new user | Public |
| POST | `/api/auth/login` | User login | Public |

### 📦 Products
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/api/products` | Get all products | Public |
| GET | `/api/products?category={category}` | Filter by category | Public |
| POST | `/api/products` | Create product | Admin |
| PUT | `/api/products/{id}` | Update product | Admin |
| DELETE | `/api/products/{id}` | Delete product | Admin |

### 🛒 Cart
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/api/cart` | Add item to cart | User/Admin |
| GET | `/api/cart` | Get cart contents | User/Admin |

### 📋 Orders
| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/api/orders` | Create order | User/Admin |
| POST | `/api/orders/from-cart` | Create order from cart | User/Admin |
| GET | `/api/orders` | Get order history | User/Admin |

## 🗄 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(254) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);
```

### Products Table
```sql
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    category VARCHAR(100) NOT NULL
);
```

### Cart Items Table
```sql
CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    product_id BIGINT REFERENCES products(id),
    quantity INTEGER NOT NULL
);
```

### Orders Table
```sql
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Order Items Table
```sql
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id),
    product_id BIGINT REFERENCES products(id),
    quantity INTEGER NOT NULL
);
```

## 🧪 Testing

### Run Tests
```bash
./mvnw test
```

### Test with Swagger UI
1. Navigate to `http://localhost:8080/swagger-ui/index.html`
2. Register a new user or login
3. Copy the JWT token from the response
4. Click "Authorize" and paste the token
5. Test any endpoint interactively

### Sample API Calls

**Create a product (Admin required):**
```bash
curl -X POST "http://localhost:8080/api/products" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 15",
    "price": 999.99,
    "stock": 10,
    "category": "Electronics"
  }'
```

**Add item to cart:**
```bash
curl -X POST "http://localhost:8080/api/cart" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
```

## 🔧 Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# Database (PostgreSQL in Docker)
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=ecommerce_user
spring.datasource.password=ecommerce_password
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=your-secret-key
jwt.expiration=86400000

# Logging
logging.level.com.esataydin=DEBUG
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Esat Aydin**
- GitHub: [@Esataydin](https://github.com/Esataydin)

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- PostgreSQL for reliable database performance
- Swagger/OpenAPI for API documentation tools

---

**⭐ Star this repository if you find it helpful!**
