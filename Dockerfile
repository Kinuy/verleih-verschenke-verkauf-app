# change setting here
FROM --platform=linux/amd64 openjdk:23

EXPOSE 8080

COPY backend/target/FuerUmmeApp.jar FuerUmmeApp.jar

ENTRYPOINT ["java", "-jar", "FuerUmmeApp.jar"]