[![Build Status](https://travis-ci.org/kislayverma/rulette-server.svg?branch=master)](https://travis-ci.org/kislayverma/rulette-server)

# What is it?
Rulette server provides a REST API and a UI to interface with any MySQL backed rulette instances. This allows applications built on the non-Java stack to be able to use Rulette and a simple yet powerful out of the box UI to see and evaluate rules.

* You can manage one or more Rulette instances in a single server deployment and use them in all the ways that you would use Rulette in-JVM.    
* Essentially, this allows you to expose your rule system(s) as a service.

# Under the hood
Rulette Server is built using Spring Boot and Thymeleaf.

# Build
To run the project via Maven, run the following command on the build directory:

	mvn clean package

This will create an executable jar under the target folder with the name 

	rulette-server-{version}.jar

To run the jar file created via package

	java -jar target/rulette-server-{version}.jar --spring.config.location={config-file-directory-path} --spring.config.name={config-file-name}

This is the standard syntax for passing external configurations to Spring Boot. Note that the config file name does not include the extension (.propertiers/.yml)

# Configure
The external configuration file is used for some configuring Spring Boot itself (port number, log level etc) and also for specifying the Rulette instances to be loaded. You can use either properties file or YAML file to specify the propoerties.

A sample file (sample-application.yml) is included in the repository to show how Rulette configuration should be done.

# Docker
Docker image for rulette-server is published on Docker Hub and can be downloaded bu running

    docker pull kislayverma/rulette-server:0.0.1-SNAPSHOT

The tag should reflect the version you want to pull.

#### Building the Docker image

You can build the docker image by first building the project as described above and then running the follwing command:

    docker build --build-arg JAR_FILE=target/rulette-server-0.0.1-SNAPSHOT.jar -t rulette-server .

You should the correct jarname generated from the maven build process as the JAR_FILE argument.

The generated docker image can now be be run as follows:

    docker run -v /absolute-path-to-local/application.yml:/application.yml  -p 8081:8081  rulette-server

We are passing in a application.yml file located at the given path to the docker image. If you are trying to connect to a local MySQL server, you should use "host.docker.internal" as the MySQL IP address in the application.yml file.
