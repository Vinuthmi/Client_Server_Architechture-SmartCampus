# Smart Campus API

## Project Description
This project is a RESTful API developed using Java, JAX-RS (Jersey), and Grizzly HTTP Server.

It manages a Smart Campus system including:
- Rooms
- Sensors
- Sensor Readings

The API allows users to create, retrieve, filter, and manage data using RESTful principles.

---

## How to Run

1. Open the project in NetBeans
2. Right-click the project → Run
3. Server will start at:
   http://localhost:8081/api/v1/

---

## API Endpoints

### Rooms
- GET /rooms → Get all rooms
- POST /rooms → Create a room
- GET /rooms/{id} → Get room details
- DELETE /rooms/{id} → Delete room

### Sensors
- GET /sensors → Get all sensors
- GET /sensors?type=Temperature → Filter sensors
- POST /sensors → Create sensor
- GET /sensors/{id} → Get sensor

### Sensor Readings
- GET /sensors/{id}/readings → Get readings
- POST /sensors/{id}/readings → Add reading

---

## 🧪 Sample CURL Commands

### 1. Create Room
```bash
curl -X POST http://localhost:8081/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LIB-301","name":"Library","capacity":40}'
```
### 2. Get Rooms
```bash
curl http://localhost:8081/api/v1/rooms
```
### 3. Create Sensor
```bash
curl -X POST http://localhost:8081/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"SEN-001","type":"Temperature","status":"ACTIVE","currentValue":25.5,"roomId":"LIB-301"}'
```
### 4. Filter Sensors
```bash
curl http://localhost:8081/api/v1/sensors?type=Temperature
```
### 5. Add Sensor Reading
```bash
curl -X POST http://localhost:8081/api/v1/sensors/SEN-001/readings \
-H "Content-Type: application/json" \
-d '{"id":"R1","timestamp":1710000000,"value":28.5}'
```
## Error Handling

- 409: Room cannot be deleted if it has sensors
- 422: Room does not exist
- 404: Resource not found
- 403 Forbidden → Sensor under maintenance
- 500 Internal Server Error → Unexpected error

## Features

- RESTful API design
- Filtering using query parameters
- Nested resources (sensor readings)
- Exception handling using mappers
- Logging filter for request and response
- In-memory data storage using HashMap

## Technologies Used

- Java
- JAX-RS (Jersey)
- Grizzly Server
- Maven
