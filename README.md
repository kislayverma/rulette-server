[![Build Status](https://travis-ci.org/kislayverma/rulette-server.svg?branch=master)](https://travis-ci.org/kislayverma/rulette-server)

# Understand
Inspired by ElasticSearch, this is a standalone deployment built on RestExpress framework to expose Rulette APIs over REST. This allows non-java users to partake of the goodness.    

* You can create one or more rule-system under a single instance and use them in all the ways that you would use Rulette in-JVM.    
* Essentially, use this to expose your rule system as a service.

# Build
To run the project via Maven:

	mvn clean package exec:java

To create a 'fat' runnable jar file:

	mvn clean package

To run the jar file created via package

	java -jar target/{project-name}.jar [environment]

# Configure
By default, the 'mvn package' goal will create a fat jar file including the configuration files in src/main/resources.
These are loaded from the classpath at runtime. However, to override the values embedded in the jar file, simply create
a new configuration file on the classpath for the desired environment. For example, './config/dev/environment.properties'
and any settings in that file will get added to, or override settings embedded in the jar file.

## Rulette configuration
rulette-server takes a data store configuration identical to Rulette engine. In your environment properties,
specify 2 properties:
* storage.engine - This is the type of database from which rules will be loaded. The only supported value for now is MYSQL.
* storage.configFilePath - This is the path of the file from which datastore connection configurations will be loaded (same as what rulette-datasource.properties does to rulette-engine).