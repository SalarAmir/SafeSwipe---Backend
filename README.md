# Smart Transaction Monitor Backend

## Architecture Overview

This Spring Boot application provides a cohesive backend for monitoring suspicious financial transactions in real-time.

## Key Components

### 1. **Model Layer**
- `Transaction.java`: Core entity with proper JPA annotations and Lombok integration
- `TransactionDto.java`: Data Transfer Object for API responses

### 2. **Repository Layer**
- `TransactionRepository.java`: JPA repository with custom query methods for fraud detection

### 3. **Service Layer**
- `TransactionService.java`: Main business logic for transaction processing
- `FraudDetectionService.java`: Specialized service for detecting suspicious activities
- `SuspiciousTransactionEvent.java`: Application event for decoupled communication

### 4. **Controller Layer**
- `TransactionController.java`: REST API endpoints with proper HTTP status codes and CORS support

### 5. **WebSocket Layer**
- `TransactionWebSocketHandler.java`: Event-driven WebSocket notifications
- `WebSocketConfig.java`: Secure WebSocket configuration

### 6. **Configuration**
- `CorsConfig.java`: Cross-origin configuration for frontend integration
- `GlobalExceptionHandler.java`: Centralized error handling

## Fraud Detection Features

The system detects suspicious transactions based on:
- **High Amount**: Transactions over $10,000
- **High Velocity**: Multiple transactions totaling over $5,000 in 10 minutes
- **Unusual Timing**: Transactions between 11 PM and 5 AM

## API Endpoints

- `POST /api/transactions` - Create new transaction
- `GET /api/transactions` - Get all transactions
- `GET /api/transactions/suspicious` - Get suspicious transactions only
- `GET /api/transactions/{id}` - Get specific transaction

## WebSocket Endpoint

- `/ws-transactions` - Real-time suspicious transaction notifications

## Key Improvements Made

1. **Separation of Concerns**: Removed direct dependency between service and WebSocket controller
2. **Event-Driven Architecture**: Uses Spring Events for loose coupling
3. **Better Error Handling**: Global exception handler and proper HTTP status codes
4. **Enhanced Security**: Specific CORS origins instead of wildcard
5. **Improved Data Model**: Proper JPA annotations and validation
6. **Advanced Fraud Detection**: Multiple criteria for suspicious activity detection
7. **Clean Code**: Removed redundant code and improved maintainability

## Database Configuration

The application uses PostgreSQL database. Update `application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/transactions
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`
