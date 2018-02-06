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

This project follows the [Maven base POM](https://github.com/ppadial/mavenbase) archetype, see the instructions in 
All the information about:
* Configure dependencies
* Maven commands & targets available

## Quick Start

Easy peasy
* Clone this repo: `git clone https://github.com/ppadial/testrail-api-client-java.git`
* Configure your maven system (settings.xml) as specified in [Maven base POM](https://github.com/ppadial/mavenbase)

Wanna use IntelliJ ?
* Open the pom.xml with intelliJ
* Use the maven projects to build it using install target

Wanna use Command line?
* `mvn install`

## Meta

Paulino Padial – [@ppadial](https://github.com/ppadial) – github.com/ppadial

Distributed under the GPLv2 license. See [LICENSE](LICENSE) for more information.

[https://github.com/ppadial/testrail-api-client-java](https://github.com/ppadial/)

## Contributing

1. Fork it (<https://github.com/ppadial/testrail-api-client-java/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

<!-- Markdown link & img dfn's -->
[wiki]: https://github.com/ppadial/testrail-api-client-java/wiki