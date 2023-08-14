# Smart4aviation Aero Application README

Welcome to the Smart4aviation Aero Application README! This document provides essential information on how to run and
test the application. Please follow the instructions below to get started.

## Table of Contents

- [Introduction](#introduction)
- [Running the Application](#running-the-application)
- [Testing the Application](#testing-the-application)
- [Accessing Documentation](#accessing-documentation)
- [Building a Docker Image](#building-a-docker-image)
- [Possible Improvement](#possible-improvement)

## Introduction

The Smart4aviation Aero Application is designed to provide [brief description of what the application does]. This README
will guide you through the process of running and testing the application.

## Running the Application

To run the application, follow these steps:

1. Open a terminal window.

2. Navigate to the root directory of the project.

3. Execute the following command:

   ```sh
   ./gradlew bootRun
   ```

   This command will start the application. You should see logs indicating the application's startup process.

## Testing the Application

There were written end-to-end tests to check all existing functionality calling APIs.

To test the application, follow these steps:

1. Open a terminal window.

2. Navigate to the root directory of the project.

3. Run the following command:

   ```sh
   ./gradlew test
   ```

   This command will execute the application's tests. The results will be displayed in the terminal, showing whether the
   tests passed or failed.

## Building a Docker Image

To facilitate deployment and distribution, you can create a Docker image for the Smart4aviation Aero Application. Docker
images provide a consistent and isolated environment for running your application across different systems. Follow these
steps to build a Docker image:

1. Open a terminal window.

2. Navigate to the root directory of the project.

3. Execute the following command:

   ```sh
   ./gradlew bootBuildImage
   ```

   This command initiates the creation of a Docker image for the application. The process includes packaging your
   application along with its dependencies into an image that can be run as a container.

4. Once the process completes, you'll find the generated Docker image ready for deployment.

## Accessing Documentation

After successfully starting the application, you can access the API documentation using Swagger UI. Follow these steps:

1. Open a web browser.

2. Enter the following URL: http://localhost:8080/swagger-ui/index.html

3. The Swagger UI page will be displayed, providing comprehensive documentation for the API endpoints, request and
   response formats, and other relevant information.

## Possible Improvement

1. All weights return values in both units (It means weights contain common sums in different units).
   As task details didn't contain how exactly values should be returned, it was chosen the current response version
   format. As option, it could return separately values in KG and LBs.

2. Data are stored in memory, as task details didn't mention this point.
   However, the current implementation could be changed to a different one.
   For example, it can be used Spring Data JPA by creating repositories that extend existed repository interfaces
   and the JpaRepository one.
