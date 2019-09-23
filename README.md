# Stock Handling  &middot; [![Build Status](https://travis-ci.com/francbreno/stock-handling.svg?branch=master)](https://travis-ci.com/francbreno/stock-handling) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.breno.projects%3Astock-handling&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.breno.projects%3Astock-handling) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.breno.projects%3Astock-handling&metric=coverage)](https://sonarcloud.io/dashboard?id=com.breno.projects%3Astock-handling) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.breno.projects%3Astock-handling&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.breno.projects%3Astock-handling)

A simple product stocking control application made in *Java* using *Spring Boot*.

## Basic Architecture

I've decided to use some concepts from **DDD** and **Hexagonal Arcuitecture** to develop the application. It allows me to use the Inversion Control principle at the architectural level. The benefits are:

- Dependencies go outside in, so the core application is normally decoupled from frameworks and libs.
- Adapters are plugable structures, so you can use different implementations for each port, e.g.: an adapter to implement an in memory data structure to be used in tests and a database accessing a real database for production.
- Tests are easier to implement
- Ports can isolate the core application from receiving domain data directly from the entry adapters, like data from a controller. It need to send data as simple classes just to deliver the necessary data to the application. So, the received data can be used to create domain objects to execute the application's use cases.
- It makes the application's use cases more explicit

### Ports & Adapters

#### Ports

Ports are interfaces of communication between the application core and the outside world. Ports that are used by the outside world to communicate with the application are called *entry* ports. A Rest Controller that need to communicate with the core application needs to interact through an entry port, for example, to update a user profile. The port implementation code is kept inside the application. Another kind of port is the ones that are used by the application to comunicate to the world outside. They are aled *exit* ports. When the application needs to persist some data, it uses a port to communicate with a persistence adapter responsible for persisting the data.  

#### Adapter

We have two kinds of adapters: *Drivers* (**in**) and *Driven* (**out**). Drivers are the ones that call the application core. The Driver adapter is the agent that starts the interaction. E.g.: Rest Controllers 

Driven adapters are the ones that the application core call when it needs to interact with the outside world. In this case, the action originates from the applpication. E.g: JPA Persistence adapter



The main motivation to use this architecture is keeping my application core isolated from frameworks code.

The source code is organized by feature as follows:

```
com
    └── breno
        └── projects
            └── stockhandling
                ├── DataLoader.java
                ├── DemoApplication.java
                ├── shared
                │   ├── adapter
                │   ├── application
                │   └── utils
                ├── statistics
                │   ├── adapter
                │   ├── application
                │   └── domain
                └── stock
                    ├── adapter
                    ├── application
                    └── model
```

Each module has 3 packages:

### model

It's the domain model. Classes on this module keep the business rules related to each concept

### application

Above the domain model, the application is responsible for keeping the domain isolated from the outside world and coordinating the use cases execution.

Outside world communication is granted though ports, interfaces that need to be implement as adapters.

### adapter

Adapters implement ports defined by the application. The injection of those objects is done by de IoC container.

### Data Structures

Some classes represents just a way to transfer data from one layer to another. We can call them DTOs. Normally they are immutable and have no behaviors. They must be considered just a data strcture. When delivered to the application, those objects are converted into domain objects.

## The App Modules

### Stock

```
├── adapter
│   ├── in
│   │   └── api
│   │       ├── exception
│   │       │   ├── IfMatchETagNotPresent.java
│   │       │   ├── InvalidIfMatchEtagException.java
│   │       │   └── StockApiExceptionHandler.java
│   │       ├── GetStockController.java
│   │       ├── helpers
│   │       │   ├── ETagStringCreator.java
│   │       │   └── HashGenerator.java
│   │       ├── request
│   │       │   └── UpdateStockRequest.java
│   │       ├── response
│   │       │   └── GetStockResponse.java
│   │       └── UpdateStockController.java
│   └── out
│       └── persistence
│           └── jpa
│               ├── StockEntity.java
│               ├── StockPersistenceAdapter.java
│               └── StockRepository.java
├── application
│   ├── port
│   │   ├── in
│   │   │   ├── GetStockQuery.java
│   │   │   ├── UpdateStockCommand.java
│   │   │   └── UpdateStockUseCase.java
│   │   └── out
│   │       ├── LoadStockPort.java
│   │       ├── NotifyStatisticsMediatorPort.java
│   │       ├── StockUpdatedData.java
│   │       └── UpdateStockStatePort.java
│   └── service
│       ├── GetStockService.java
│       └── UpdateStockService.java
└── model
    ├── exception
    │   ├── FutureStockTimestampException.java
    │   └── InvalidStockQuantityException.java
    └── Stock.java
```

### Statistics

```
├── adapter
│   ├── in
│   │   └── api
│   │       ├── GetStatisticsController.java
│   │       └── response
│   │           ├── AvailableProductDto.java
│   │           ├── GetStatisticsResponse.java
│   │           └── ProductSellsDto.java
│   └── out
│       └── persistence
│           └── mongo
│               ├── ProductStatisticsDocument.java
│               ├── ProductStatisticsPersistenceAdapter.java
│               ├── ProductStatisticsRepository.java
│               └── StockUpdateEvent.java
├── application
│   ├── port
│   │   ├── in
│   │   │   ├── LoadStatisticsQuery.java
│   │   │   ├── TopStatistics.java
│   │   │   ├── UpdateStockStatisticsCommand.java
│   │   │   └── UpdateStockStatisticsUseCase.java
│   │   └── out
│   │       ├── LoadProductStatisticsPort.java
│   │       ├── LoadStatisticsPort.java
│   │       └── UpdateStockStatisticsStatePort.java
│   └── service
│       ├── LoadStatisticsService.java
│       └── UpdateStockStatisticsService.java
└── domain
    ├── ProductStatistics.java
    ├── ProductStatisticsSummarized.java
    ├── StatisticsRangeFilterType.java
    └── StockEvent.java
```

**Spring** is responsible for wiring dependencies and **Spring Boot** is responsible for providing necessary configurations to make the app run.

## Getting Started

Clone the repo into your current working dir:

```
git clone https://github.com/francbreno/stock-handling.git
```

Go to the cloned project dir

```
cd stock-handling
```

### Prerequisites

You just need **Java 8** or higher to run the application. Check your current installation version using the following command:  

```
java -version
```

You can get a new Java version from Oracle [here](https://www.java.com).

### Installing

You can download the dependencies using the embedded Maven

```
./mvnw clean install
```

You can run and debug this application application as you would any other Java program.

Before running the application, make sure you have the ports `8282` (Server) and `37017` (Embedded MongoDB) free. You can change them in the `application.properties` file.

## Running the tests

Just use the [maven wrapper](https://github.com/takari/maven-wrapper)

```
./mvnw clean test
```

You can also run with your regular [Maven](https://maven.apache.org/) Installation

```
mvn clean test
```

## Built With

* [Spring Boot](https://github.com/spring-projects/spring-boot/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Junit Jupiter](https://github.com/junit-team/junit5/) - Use to execute *unit* and *integrations* tests
* [Lombok](https://github.com/rzwitserloot/lombok/) - Used to reduce Java source code verbosity and eliminate boilerplate code
* [Embedded MongoDB](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo/) - Use to store product stock statistics data
* [H2](https://github.com/h2database/h2database/) - Used to keep product stock's last data

## Authors

* **Breno Soares** - [francbreno](https://github.com/francbreno)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


