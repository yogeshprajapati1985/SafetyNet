# SafetyNet
SafetyNet Alerts Application.

## Project Overview
SafetyNet Alerts application is designed to send emergency information to emergency responders. The main purpose is to provide critical information during emergencies such as fires, storms, and floods. The application retrieves and dispatches information about residents, including phone numbers, ages, medical information, and more.

## Features
- Fire Alerts: Provide information about people in the house, including phone numbers.
- Storm Alerts: Notify residents in the storm area via text message.
- Flood Alerts: Dispatch responders with specialized information about residents, including ages and medical information.
- Springboot Endpoints: Implement endpoints for health, info, metrics, and trace.
- JSON Output: Produce JSON output from corresponding URLs.
- MVC Design Pattern: Architect SafetyNet Alerts following the Model-View-Controller design pattern while adhering to SOLID principles.

## Technologies Used
- Java
- Spring Boot
- Maven
- RESTful API
- JSON
- Docker (optional)

## Getting Started
To get started with the SafetyNet Spring Boot project, follow these steps:

### 1. Clone the Repository
Clone the repository to your local machine using Git:
```bash
git clone https://github.com/yogeshprajapati1985/SafetyNet.git
```

### 2. Install Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven 3.6+
- Git
- Docker (optional)

### 3. Build the Project
Navigate to the project directory and run the following command to build the project:
```bash
mvn clean install
```
This command will compile the code, run tests, and package the application into a JAR file.

### 4. Run the Application
You can run the application using the following command:
```bash
mvn spring-boot:run
```

### 5. Access the Application
Once running, the application will be available at:
http://localhost:8099

## 🔗 API Endpoints

| Endpoint                                                | Description                                                                                                                                     |
|---------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| `/actuator/health`                                      | Application health status                                                                                                                       |
| `/actuator/info`                                        | Application metadata                                                                                                                            |
| `/actuator/metrics`                                     | Performance and usage metrics                                                                                                                   |
| `/actuator/trace`                                       | HTTP request tracing                                                                                                                            |
| `/alerts/fire`                                          | Fire alert information                                                                                                                          |
| `/firestation?stationNumber=<station_number>`           | This URL should return a list of people serviced by the corresponding fire station.                                                             |
| `/childAlert?address=<address>`                         | This URL should return a list of children (anyone under the age of 18) at that address.                                                         |
| `/phoneAlert?firestation=<firestation_number>`          | This URL should return a list of phone numbers of each person within the fire station’s jurisdiction.                                           |
| `/fire?address=<address>`                               | This URL should return the fire station number that services the provided address as well as a list of all of the people living at the address. |
| `flood/stations?stations=<a list of station_numbers>`   | This should return a list of all the households in each fire station’s jurisdiction.                                                            |
| `/personInfo?firstName=<firstName>&lastName=<lastName>` | This should return the person’s name, address, age, email, list of medications with dosages and allergies.                                      |
| `/communityEmail?city=<city>`                           | This will return the email addresses of all of the people in the city.                                                                          |
| `/person`                                               | This endpoint will provide the following via Http Post/Put/Delete.                                                                              |
| `/firestation`                                          | This endpoint will provide the following via Http Post/Put/Delete.                                                                              |
| `/medicalRecord`                                        | This endpoint will provide the following via Http Post/Put/Delete.                                                                              |
