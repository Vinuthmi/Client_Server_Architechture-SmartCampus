# Smart Campus API  
### RESTful Room & Sensor Management System

<p align="left">
   <strong>Java 17</strong> &nbsp;&nbsp;|&nbsp;&nbsp;
   <strong>JAX-RS (Jersey)</strong> &nbsp;&nbsp;|&nbsp;&nbsp;
   <strong>Grizzly HTTP Server</strong> &nbsp;&nbsp;|&nbsp;&nbsp;
   <strong>Apache Maven</strong>
</p>

## Coursework Information

| Category     | Details                                   |
|--------------|-------------------------------------------|
| Student Name | Janithya Vinuthmi Wadumethrige            |
| Student ID   | 20241447 / w2153566                       |
| Module       | 5COSC022C.2 Client-Server Architectures   |
| Assignment   | Individual Coursework                     |


---
## Project Overview

This project is a RESTful API developed for a Smart Campus system using **Java**, **JAX-RS (Jersey)**, and the **Grizzly HTTP server**. It manages **Rooms**, **Sensors**, and **Sensor Readings** through standard HTTP operations.

The API supports key features such as resource creation, retrieval, filtering, and nested resources. All data is stored in-memory using **HashMap** and **ArrayList**, making the system lightweight and easy to run without an external database.

It also includes proper **error handling** and **request/response logging**, demonstrating core concepts of client-server architecture and RESTful API design.

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

---


# Report: Answers to Coursework Questions

## Part 1: Service Architecture & Setup

### **Q1.1-**

**In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.**

**Answer:**
In JAX-RS, the default lifecycle of a resource class is per-request. This means that every time a client sends a request, a new instance of the resource class is created to handle it. The runtime does not treat resource classes as singletons unless explicitly configured.

This behaviour ensures that each request is isolated. There is no risk of one request accidentally modifying the internal state of another request through instance variables. However, this also means that resource classes cannot be used to store shared application data such as rooms or sensors.

To manage shared data, developers need to use external data structures such as static maps or a centralized data store. Since multiple requests may access and modify these structures at the same time, careful handling is required. Without proper design, issues like race conditions or inconsistent updates can occur.

Because of this, it is important to ensure that updates to shared data are controlled and consistent. The per-request lifecycle improves safety at the class level, but it shifts responsibility to how shared in-memory data is managed.

---

### **Q1.2-**

**Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?**

**Answer:**
Hypermedia, also known as HATEOAS, allows an API to include links in its responses that guide clients to related resources or actions. Instead of just returning raw data, the API provides navigation options within the response itself.

This is considered an advanced RESTful practice because it makes the API more self-descriptive. Clients do not need to rely heavily on external documentation to understand what to do next. They can simply follow the links provided by the API.

For client developers, this approach makes integration easier and more flexible. If the API structure changes in the future, the client can still work correctly by following updated links rather than relying on hardcoded URLs.

Overall, HATEOAS improves usability, reduces errors, and makes the API more adaptable over time.



## Part 2: Room Management

### **Q2.1-**

**When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.**

**Answer:**
Returning only room IDs makes the response much smaller. This helps reduce network usage and improves performance, especially when there are many rooms. However, the client will then need to make additional requests to get full details for each room.

On the other hand, returning full room objects provides all the necessary information in one response. This makes things easier for the client because no extra requests are needed. The downside is that the response becomes larger, which may increase bandwidth usage.

So, there is a trade-off between efficiency and convenience. Returning IDs is more efficient, while returning full objects is more user-friendly for the client.



### **Q2.2-**

**Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.**

**Answer:**
Yes, the DELETE operation is idempotent in this implementation. This means that performing the same DELETE request multiple times results in the same final state.

When a room is deleted for the first time, it is removed from the system. If the same request is sent again, the room no longer exists, so the API will typically return a 404 response. However, the system state does not change after the first deletion.

If the room cannot be deleted due to business rules, such as having sensors assigned, the request will be rejected. Sending the same request again will produce the same result without changing anything in the system.

In both cases, repeated requests do not cause additional changes, which is why the operation is considered idempotent.



## Part 3: Sensor Operations & Linking

### **Q3.1-**

**We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?**

**Answer:**
The @Consumes(MediaType.APPLICATION_JSON) annotation tells JAX-RS that the method only accepts JSON input. This means the client must send data with the correct Content-Type header.

If a client sends data in a different format, such as text/plain or application/xml, the request will not be accepted. JAX-RS will automatically detect the mismatch before the method is executed.

In such cases, the server returns a 415 Unsupported Media Type error. The request body is not processed, and the method logic is never reached.

This ensures that only properly formatted JSON data is handled by the API, which helps maintain consistency and avoids errors.



### **Q3.2-**

**You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?**

**Answer:**
Using @QueryParam for filtering makes the API more flexible and easier to use. Query parameters allow clients to optionally filter data without changing the main structure of the URL.

For example, a client can request all sensors or filter them by type using the same endpoint. It also allows multiple filters to be combined easily, such as filtering by type and status at the same time.

If filtering is implemented using the URL path, it becomes less flexible. The API would need separate paths for different types of filters, which makes it harder to extend and maintain.

Query parameters clearly indicate that the request is modifying or refining a collection, while path parameters are meant to identify specific resources. This is why the query parameter approach is generally preferred.



## Part 4: Deep Nesting with Sub-Resources

### **Q4.1-**

**Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?**

**Answer:**
The Sub-Resource Locator pattern helps organize APIs by separating different levels of functionality into different classes. Instead of handling everything in one large resource class, each part of the API is handled by a specific class.

This makes the code easier to read and maintain. Each class focuses on a single responsibility, which reduces complexity and improves clarity.

If all nested paths were handled in one class, the code would become very large and difficult to manage. It would also be harder to test and extend.

By delegating logic to separate classes, developers can build a more modular and scalable system. This approach keeps the API clean and manageable as it grows.



## Part 5: Advanced Error Handling, Exception Mapping & Logging

### **Q5.2-**

**Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?**

**Answer:**
HTTP 422 is more appropriate because it indicates that the request was valid, but the data inside it is incorrect. In this case, the endpoint exists and is working, but the payload contains a reference to something that does not exist.

A 404 error, on the other hand, suggests that the endpoint itself is missing, which is not true in this situation.

Using 422 helps the client understand that the problem is with the data they provided, not with the API itself. This makes error handling clearer and more meaningful.



### **Q5.4-**

**From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?**

**Answer:**
Exposing internal Java stack traces can reveal sensitive information about how the system is built. This may include class names, file paths, and library versions.

An attacker can use this information to understand the internal structure of the application. This makes it easier to identify weaknesses and plan targeted attacks.

Stack traces may also show how the system processes data, which can help attackers craft inputs that exploit vulnerabilities.

To avoid these risks, APIs should return simple error messages to users while keeping detailed logs internally for debugging.



### **Q5.5-**

**Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?**

**Answer:**
Using JAX-RS filters for logging keeps the code clean and organized. Instead of adding logging statements in every method, the logic is handled in one central place.

This reduces repetition and ensures that all requests and responses are logged consistently. It also makes the code easier to maintain, since any changes to logging only need to be made in one location.

Additionally, filters automatically apply to all endpoints, so there is no risk of forgetting to add logging in a method.

Overall, this approach improves code quality and makes the system more scalable.

---


