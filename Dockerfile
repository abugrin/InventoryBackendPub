FROM eclipse-temurin:20

LABEL authors="Anton B"
COPY ./target/Inventory-0.0.1-SNAPSHOT.jar /tmp
#COPY ./src/main/resources /tmp



WORKDIR /tmp
ENTRYPOINT ["java", "-jar" , "/tmp/Inventory-0.0.1-SNAPSHOT.jar"]
EXPOSE 8443/tcp
