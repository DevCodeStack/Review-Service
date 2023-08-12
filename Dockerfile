FROM openjdk:8

COPY ./target/Review-Service.jar reviewservice.jar

EXPOSE 8084

CMD ["java","-jar","-Dspring.profile.active=local","reviewservice.jar"]