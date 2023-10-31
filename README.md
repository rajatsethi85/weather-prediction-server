# weather-prediction-server
This repo holds the server code to fetch the weather forecast report from the Open weather API.

Implementation:
1. Created a springboot microservice and implemented the spring security to secure the app.
2. Used token based API authentication so that API should be accessed with valid token.
3. Created interface throughout the app and follow SOA.
4. Implement Open API spec to give the brief documentation for the API.
5. Create the app image to with docker.

Deployment:
1. Used Jenkins pipeline to deploy the app to AWS EC2 isntance.
2. Used AWS ECS to register the docker image,EKS to create cluster and deploy docker image and made it available on internet, EC2 instance with Amazon Linux server to setup all these services for the deployment of the server app.
3. Set AWS VPC and set routing tables and subnets to access the EC2 instance through SSH.

Run application on Intellij:
1. Pull the code from https://github.com/rajatsethi85/weather-prediction-server.git
2. I have made this repo public so that everyone can access.
3. Open the pom.xml file on intellij as a project and let it build.
4. Run maven command mvn clean install.
5. Application will run on port 8081 by default.

Run application using docker:
1. Install Docker on your machine.
2. Go to the root file of the app and run below command:
    1. docker build -t weather-prediction-server
    2. docker run -d -p 8081:8081 --name weather-prediction-container weather-prediction-server
3. This will build the project create an image and run it on a container.
4. Now you can access the endpoints using localhost:8081.

NOTES:
1. To hit the API you should have the valid api key.
  

# FLOW OF APPLICATION

![flowchart.drawio.svg](flowchart.drawio.svg)
