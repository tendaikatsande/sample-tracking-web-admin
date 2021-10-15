FROM openjdk:17
EXPOSE 80
ADD ./target/sample-tracking-0.0.1.jar sample-tracking-0.0.1.jar
ENTRYPOINT ["java","-jar", "/sample-tracking-0.0.1.jar"]
