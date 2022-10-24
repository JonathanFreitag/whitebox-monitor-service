# Java Service Monitor
This monitor will be used to monitor the status of multiple web services.  

Requirements for running the application:

Java 11
Docker/Docker compose
Postman - (There is a postman collection in the projet folder that you may import)

How to run the application:

- Clone the repository 
- The project has two modules (java-monitor-service and notification-service)
- In your terminal:
    - cd to the root folder
    - run: docker-compose up mysql-db
    - cd to java-monitor-service root folder
    - run: mvn clean package
    - cd ../notification-service
    - run: mvn clean package
    - cd ..
    - run: docker-compose up (wait for the services to come up)
    - run: docker ps (You will see four services running: mysql-db, rabbitmq, java-monitor-service and notification-service)
    
 - Open Postman and import WhiteBox.postman_collection.json from the project folder.
 - Start sending requests.
    
