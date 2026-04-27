# **Currency Conversion Test Framework (BDD)**

# Overview
This project uses:
* Cucumber
* Java 
* Maven

# Features
* Multi-currency conversion validation
* CSV / JSON / YAML input support
* Parallel execution
* Tolerance-based validation (±1%)
* Positive and negative test scenarios
* API integration for real-time exchange rates

# Tech Stack
* Java
* Cucumber (BDD)
* RestAssured
* Jackson (JSON/YAML)
* OpenCSV

# Prerequisites
Ensure you have the following installed:
* Java 11+ (java -version)
* Maven (mvn -version)
* IntelliJ IDEA x
* Git 

# Setup
## Cloning
`$ git clone "https://github.com/swathi-guduguntla/currency-conversion-framework.git"

`$ cd currency-conversion-framework`
## Install dependencies 
`mvn clean install`

# Run tests
### Using maven
`mvn clean test`
or
`mvn test -Dcucumber.options="--tags @smoke"`

### Using TestRunner
`Right-click TestRunner → Run`
