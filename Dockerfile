FROM eclipse-temurin:22-jdk

WORKDIR /app

COPY target/VStore_ProductService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]