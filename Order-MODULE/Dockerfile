FROM openjdk:14-jdk-alpine
MAINTAINER Kotov Svyatoslav
ADD /build/libs/order_module-0.0.1-SNAPSHOT.jar order-module.jar
ENV SERVER_PORT=8083

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://192.168.99.100:5432/order_module
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

ENV SPRING_RABBITMQ_HOST=192.168.99.100
ENV SPRING_RABBITMQ_PORT=5672
ENV SPRING_RABBITMQ_USERNAME=root
ENV SPRING_RABBITMQ_PASSWORD=root

ENTRYPOINT java -jar /order-module.jar