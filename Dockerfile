FROM amazoncorretto:17
ADD /target/ims-events-processor-0.0.1-0.1.jar ims-events-processor-0.0.1-0.1.jar
ENTRYPOINT ["java","-jar","ims-events-processor-0.0.1-0.1.jar"]