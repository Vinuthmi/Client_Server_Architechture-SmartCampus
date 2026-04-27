# Smart Campus API
## Project Description
This project is a RESTful API developed using Java and JAX-RS (Jersey).

It manages smart campus resources such as:
- Rooms
- Sensors
- Sensor readings

The system allows users to create, retrieve, and manage data efficiently.

## How to Run

1. Open the project in NetBeans
2. Right-click the project → Run
3. Server will start at:
   http://localhost:8081/api/v1/
   ## API Endpoints

### Rooms
- GET /rooms
- POST /rooms
- DELETE /rooms/{id}

### Sensors
- GET /sensors
- GET /sensors?type=Temperature
- POST /sensors

### Sensor Readings
- GET /sensors/{id}/readings
- POST /sensors/{id}/readings

## Sample Requests

### Create Room
POST /rooms

{
  "id": "LIB-301",
  "name": "Library",
  "capacity": 40
}

### Create Sensor
POST /sensors

{
  "id": "SEN-001",
  "type": "Temperature",
  "status": "ACTIVE",
  "currentValue": 25.5,
  "roomId": "LIB-301"
}

### Add Sensor Reading
POST /sensors/SEN-001/readings
{
  "id": "R1",
  "timestamp": 1710000000,
  "value": 28.5
}

## Error Handling

- 409: Room cannot be deleted if it has sensors
- 422: Room does not exist
- 404: Resource not found

## Features

- RESTful API design
- Filtering using query parameters
- Nested resources (sensor readings)
- Exception handling using mappers
- Logging filter for request and response

## Technologies Used

- Java
- JAX-RS (Jersey)
- Grizzly Server
- Maven
