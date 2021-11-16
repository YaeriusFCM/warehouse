# Simple Data Warehouse

Author: [Paweł Dobrzański](https://www.linkedin.com/in/pawel-dobrzanski/)  
Created: November 2021  
Technology stack: Spring Boot, Groovy, PostgreSQL

## Description

Simple backend application that exposes data via an API.

## Usage

Application is hosted on [Heroku](https://heroku.com) cloud under [this](https://ya-warehouse.herokuapp.com) address.
For testing purposes it is not needed to deploy a local instance (but if you want to, there are installation steps below).
Be warned that first app request may take some time, because Heroku shuts down instances which are idle for some time.

### Example requests

- `/datasources` to identify the id of a datasource of interest
- `/dsmetrics/2` to see total metrics for Twitter Ads
- `/campaigns/snow` to see all campaigns with "snow" word included in the name
- `/cpmetrics/40/2019-01-01/2019-02-01` to see total metrics for Snowboard campaign in January 2019
- `/dsmetricsovertime/3/2020-01-01/2020-02-01` to see daily metrics for Facebook Ads in January 2020
- `/cpmetricsovertime/99` to see all daily metrics for Touristik City Guide_iOS campaign

## Installation guide

### Prerequisites

- [Java 8+](https://www.java.com/pl/download/)
- [PostgreSQL](https://www.postgresql.org/download/) (optional - the application uses a cloud database hosted online, 
  even for local app deployment it is not needed to install a local db instance)
- [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) (optional)

Please install the above software by following appropriate links.

### Installation steps

1. Clone git repository: `git clone https://github.com/YaeriusFCM/warehouse.git`  
   (Alternatively you can download a ZIP file and unpack it if you don't want to install Git)
1. Enter the **warehouse** folder and compile the project by running: `gradlew build` (gradlew.bat if on Windows)
1. Start the application by running: `java -jar build/libs/warehouse-0.0.1.jar`
1. Open your browser and enter: `localhost:8080`
1. Enjoy :)
