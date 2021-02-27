<h1 align="center">
    my_ota - Hackathon implementation
</h1>
<p align="center">
    <a href="https://github.com/surajcm/my_ota/commits/" title="Last Commit"><img src="https://img.shields.io/github/last-commit/surajcm/my_ota?style=flat"></a>
    <a href="https://github.com/surajcm/my_ota/actions/workflows/test.yml" title="Tests"><img src="https://github.com/surajcm/my_ota/actions/workflows/test.yml/badge.svg"></a>
    <a href="https://github.com/surajcm/my_ota/issues" title="Open Issues"><img src="https://img.shields.io/github/issues/surajcm/my_ota?style=flat"></a>
    <a href="https://github.com/surajcm/my_ota/blob/master/LICENSE" title="License"><img src="https://img.shields.io/badge/License-MIT-green.svg?style=flat"></a>
</p>
A basic working OTA webservices connecting to IATA NDC sandbox and publishing both REST and GRAPHQL. 

This is for implementing a hackathon idea on **graphql** based ota connectivity.

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



