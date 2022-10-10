<h1 align="center">
    my_ota - Hackathon implementation
</h1>
<p align="center">
    <a href="https://github.com/surajcm/my_ota/commits/" title="Last Commit"><img src="https://img.shields.io/github/last-commit/surajcm/my_ota?style=flat"></a>
    <a href="https://github.com/surajcm/my_ota/actions/workflows/run_tests.yml" title="Tests"><img src="https://github.com/surajcm/my_ota/actions/workflows/run_tests.yml/badge.svg"></a>
    <a href="https://github.com/surajcm/my_ota/issues" title="Open Issues"><img src="https://img.shields.io/github/issues/surajcm/my_ota?style=flat"></a>
    <a href="https://github.com/surajcm/my_ota/blob/master/LICENSE" title="License"><img src="https://img.shields.io/badge/License-MIT-green.svg?style=flat"></a>
    <a href="https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg" title="code_of_conduct.md"><img src="https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg"></a>
    <a href="https://github.com/surajcm/my_ota/pulls?q=is%3Apr+is%3Amerged+created%3A2022-10-01..2022-10-31" title="Hacktoberfest 2022 stats"><img src="https://img.shields.io/github/hacktoberfest/2022/surajcm/my_ota?label=Hacktoberfest+2022"></a>
</p>
<a href="https://foojay.io/today/works-with-openjdk"><img align="right" src="https://github.com/foojayio/badges/raw/main/works_with_openjdk/Works-with-OpenJDK.png" width="100"></a>

A basic working OTA webservices connecting to IATA NDC sandbox and publishing both REST and GRAPHQL. 

This is for implementing a hackathon idea on **graphql** based ota connectivity.

## Author

👤 **Suraj Muraleedharan**

* Github: [@surajcm](https://github.com/surajcm)

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page](https://github.com/surajcm/my_ota/issues).
If you contribute please commit to a new branch and explain details in your pull request not in your commit.

Remember to abide by our [Code of Conduct](CODE_OF_CONDUCT.md) , adapted from ![Contributor Covenant 1.3](https://img.shields.io/badge/Contributor%20Covenant-1.3-4baaaa.svg)
## Show your support

Give a ⭐️ if this project helped you!


##### Initial Setup
Set the environment variables: 
M2_HOME, JAVA_HOME etc

##### Build command :
`./mvnw clean build`

##### To Run the application :
`./mvnw spring-boot:run`

##### If you have a ~/.m2/settings.xml that points to your organisations maven repo
`./mvnw -s settings.xml spring-boot:run`

#### Swagger UI
Please check at http://localhost:8090/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

for request response samples, please head out to [dev readme](https://github.com/surajcm/my_ota/tree/master/dev)


