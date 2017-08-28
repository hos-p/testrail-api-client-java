# Testrail API Client for java
[![Build Status](https://travis-ci.org/ppadial/testrail-api-client-java.svg?branch=master)]((https://travis-ci.org/ppadial/testrail-api-client-java.svg?branch=master)](https://travis-ci.org/ppadial/testrail-api-client-java))
[![Coverage Status](https://coveralls.io/repos/github/ppadial/testrail-api-client-java/badge.svg?branch=master)](https://coveralls.io/github/ppadial/testrail-api-client-java?branch=master)

# Use this api

Use this dependency in your maven config
```xml
<dependency>
  <groupId>com.github.ppadial</groupId>
  <artifactId>testrail-api-client</artifactId>
  <version>0.1.0</version>
</dependency>
```


# Project Status
This API currently support TestRail 5.4.0 API version.

## API Modules implementation status
| Module          | Status          | Notes | Reference |
| :---            | :----           | :---        | :--- |
| Cases           | Partially       | Update and Add pending | http://docs.gurock.com/testrail-api2/reference-cases |
| Case Fields     | Not Implemented | | |
| Case Types      | All Implemented | | |
| Configurations  | All Implemented | | |
| Milestones      | All Implemented | | |
| Plans           | All Implemented | | |
| Priorities      | All Implemented | | |
| Projects        | All Implemented | | |
| Results         | All Implemented | | |
| Result Fields   | Not Implemented | | |
| Runs            | Partially       | Update, Close, Add getRuns are pending | http://docs.gurock.com/testrail-api2/reference-runs |
| Sections        | All Implemented | | |
| Statuses        | All Implemented | | |
| Suites          | Partially       | Update and Delete pending | http://docs.gurock.com/testrail-api2/reference-suites |
| Templates       | All Implemented | | |
| Tests           | All Implemented | | |
| Users           | All Implemented | | |



# Project Information

This project follows the [Maven base POM](https://github.com/ppadial/base-pom) archetype, see the instructions in 
All the information about:
* Configure dependencies
* Maven commands & targets available

## Specific configuration values
[Maven base POM](https://github.com/ppadial/base-pom) archetype defines a set of default behaviours but in this 
project we have some customisations done:
#### Test level configurations

| Property | Value | Description |
| :---     | :---: | :---        |
| base.check.skip-unit | false | Unit tests are mandatory by default |
| base.check.skip-integration | true | Integration tests aren't mandatory by default |

#### Failure configurations
| Property | Value | Description |
| :---     | :---: | :---        |
| base.check.fail-findbugs                 | false | Not fails if findbugs errors are found       |
| base.check.fail-enforcer                 | false | Not fails if enforcer errors are found       |
| base.check.fail-modernizer               | false | Not fails if modernizer errors are found     |
| base.check.fail-duplicate-finder         | false | Not fails if duplicate code errors are found |
| base.check.skip-dependency               | true  | Not fails if dependencies errors are found   |
| base.check.skip-dependency-version-check | true  | Not fails if depen. version errors are found |
| base.check.fail-pmd                      | false | Not fails if PMD errors are found            |

#### Additional configurations for this project
| Property | Value | Description |
| :---     | :---: | :---        |    

## Quick Start
Easy peasy
* Clone this repo: `git clone https://github.com/ppadial/testrail-api-client-java.git`
* Configure your maven system (settings.xml) as specified in [Base POM](https://github.com/ppadial/base-pom)

Wanna use IntelliJ ?
* Open the pom.xml with intelliJ
* Use the maven projects to build it using install target

Wanna use Command line?
* `mvn install`