FROM amazoncorretto:17.0.6

WORKDIR /app

COPY target/WebStore-*.jar /app/WebStore.jar

CMD ["java", "-jar", "WebStore.jar"]