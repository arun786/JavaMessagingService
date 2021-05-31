# Java messaging service

Prerequisites

## Download Apache Active MQ Artemis

step 1 : download the ZIP version of the Active MQ

    https://activemq.apache.org/components/artemis/download/


step 2 : unzip the folder and go to bin/artemis

![folder structure](https://github.com/arun786/SpringJMS/blob/main/src/images/activemq%20folder%20structure.png)

step 3 : create a broker (artemisBroker)

![create a broker](https://github.com/arun786/SpringJMS/blob/main/src/images/broker.png)

![broker folder structure](https://github.com/arun786/SpringJMS/blob/main/src/images/artmeis%20broker%20folder%20structure.png)

step 4 : Once broker is created, go to the folder of artemisBroker/bin

![start a broker](https://github.com/arun786/SpringJMS/blob/main/src/images/artemisServer.png)

run the below command

    ./artemis run

the server will start on port 8161

step 4 : set up a gradle project and add the below dependency in build.gradle

     implementation 'org.springframework.boot:spring-boot-starter-artemis'

step 5 : when you go to localhost:1861

![server](https://github.com/arun786/SpringJMS/blob/main/src/images/screenshot%20of%20server.png)

    username and password will be the same as provided while creating a broker.


# Create a gradle Project

add the below dependency

    implementation 'org.apache.activemq:artemis-jms-client-all:2.17.0'

create a jndi.properties




    
    
    
