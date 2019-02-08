FROM maven:3-jdk-8-alpine

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY . .

RUN mvn clean package

EXPOSE 8888

CMD ["java", "-jar", "./lolibox-server/target/lolibox-server-0.2.6-SNAPSHOT.jar"]