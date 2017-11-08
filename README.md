# TicketServiceChallenge

## Description

Provides a service that allows seats of a venue to be held and then reserved. The hold on the seats will expire after some time if they are not changed to reservations.

## Instructions

1. Open the command line and navigate to the root folder of the project.
## See: (TicketService.java)[src\main\java\services\TicketService.java]

### Build and run tests

```
gradlew clean
gradlew build
```

### Run tests only

```
gradlew clean
gradlew test
```

## Assumptions

```
java -version
java version "1.8.0_152"
Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
Java HotSpot(TM) 64-Bit Server VM (build 25.152-b16, mixed mode)
```

1. In this implementation, the venue is a rectangular grid of seats. The width of the rows are all the same and all of the seats are initially available.
1. The email address is returned in the SeatHold object. It is assumed that this is okay, even though it could be a security issue.
1. Null is returned for error conditions for the findAndHoldSeats and reserveSeats methods.
1. Currently filling rows left to right. Centering might be more ideal, but could be a future enhancement.

## Javadoc

[Javadoc](http://htmlpreview.github.io/?https://github.com/mgove131/TicketServiceChallenge/blob/master/build/docs/javadoc/index.html)