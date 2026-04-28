# Smart Campus API

- Name: Janithya Vinuthmi Wadumethrige
- Student ID: 20241447 / w2153566
- Module: 5COSC022C.2 Client-Server Architectures
- Assignment: Individual Coursework


## Project Description
This project is a fully RESTful API developed using Java, JAX-RS (Jersey), and the Grizzly HTTP server for a Smart Campus system.

The API is designed to manage **Rooms** and their associated **Sensors**, while also maintaining a collection of **Sensor Readings** over time. It allows clients to **create, retrieve, filter, and manage** these resources through standard RESTful endpoints.

All data is stored in-memory using efficient data structures such as HashMap, without relying on any external database. The system also includes features like query-based filtering, nested resources for sensor readings, exception handling, and request/response logging to simulate a real-world backend service.

---

## Discovery Endpoint

### GET /api/v1
*Sample response*
```json
{
  "name": "Smart Campus API",
  "version": "v1",
  "contact": "admin@smartcampus.com",
  "links": {
    "rooms": "/api/v1/rooms",
    "sensors": "/api/v1/sensors"
  }
}
```
---
## Data Models

The system is built around three main entities that represent the Smart Campus environment.

### Room
A Room represents a location within the campus where sensors are installed.

```json
{
  "id": "CLS-102",
  "name": "Classroom 102",
  "capacity": 45,
  "sensorIds": []
}
```
### Sensor
```json
{
  "id": "SEN-001",
  "type": "Temperature",
  "status": "ACTIVE",
  "currentValue": 25.5,
  "roomId": "CLS-102"
}
```
### SensorReading
```json
{
  "id": "READ-001",
  "timestamp": 1713439200000,
  "value": 26.0
}
```
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

## Setup and Execution

### Requirements
Before running the project, make sure you have:
- Java JDK 17 or higher installed  
- Apache Maven installed  


**1. Get the project**
```bash
git clone https://github.com/Vinuthmi/SmartCampusAPI.git
```
**2. Navigate to the project folder**
```bash
cd SmartCampusAPI
```
**3. Compile the project**
```bash
mvn clean install
```
**4. Launch the API server**
```bash
mvn exec:java
```
**Access the API**

Once the server is running, open the following URL:

http://localhost:8081/api/v1/

**Testing**

You can interact with the API using:

- Postman
- Web browser
- curl commands

**Stopping the Server**

To stop the server, simply terminate the running process in the terminal (Ctrl + C).

---
  
## Sample cURL Commands
### 1. Discovery endpoint
```bash
curl http://localhost:8081/api/v1/
```

### 2. Create Room
```bash
curl -X POST http://localhost:8081/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"CLS-102","name":"Classroom 102","capacity":45}'
```
### 3. Create Sensor
```bash
curl -X POST http://localhost:8081/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"SEN-001","type":"Temperature","status":"ACTIVE","currentValue":25.5,"roomId":"CLS-102"}'
```
### 4. Filter Sensors
```bash
curl http://localhost:8081/api/v1/sensors?type=Temperature
```
### 5. Add Sensor Reading
```bash
curl -X POST http://localhost:8081/api/v1/sensors/SEN-001/readings \
-H "Content-Type: application/json" \
-d '{"id":"READ-001","timestamp":1713439200000,"value":26.0}'
```
### 6. Error Test (Delete Room with Sensors → 409)
```bash
curl -X DELETE http://localhost:8081/api/v1/rooms/CLS-102
```
### 7. Error Test (Invalid Room ID → 422)
```bash
curl -X POST http://localhost:8081/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"SEN-999","type":"Temperature","status":"ACTIVE","currentValue":25.5,"roomId":"INVALID-ROOM"}'
```
### 11. Error Test (Sensor Under Maintenance → 403)
```bash
curl -X POST http://localhost:8081/api/v1/sensors/SEN-001/readings \
-H "Content-Type: application/json" \
-d '{"id":"READ-002","timestamp":1713439300000,"value":27.0}'
```
## Error Handling

- 409: Room cannot be deleted if it has sensors
- 422: Room does not exist
- 403 Forbidden → Sensor under maintenance
- 404: Resource not found
- 500 Internal Server Error → Unexpected error

## Features

- RESTful API design
- Filtering using query parameters
- Nested resources (sensor readings)
- Exception handling using mappers
- Logging filter for request and response
- In-memory data storage using HashMap

## Technologies Used

| Category        | Technology              | Description                                      |
|----------------|------------------------|--------------------------------------------------|
| Language        | Java                   | Core programming language used for development   |
| REST Framework  | JAX-RS (Jersey)        | Used to build RESTful web services              |
| Server          | Grizzly HTTP Server    | Lightweight server to run the API               |
| Build Tool      | Apache Maven           | Manages dependencies and builds the project     |
| Data Storage    | HashMap (In-memory)    | Stores data temporarily without a database      |
