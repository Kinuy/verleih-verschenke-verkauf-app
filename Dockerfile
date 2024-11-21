# change setting here
FROM openjdk:23

EXPOSE 8080

COPY backend/target/FuerUmme-App.jar FuerUmme-App.jar

ENTRYPOINT ["java", "-jar", "FuerUmme-App.jar"]