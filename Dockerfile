FROM amazoncorretto:17-alpine

RUN apk update && apk add --no-cache curl
RUN apt-get update && apt-get install -y curl

COPY target/app.jar /app.jar

EXPOSE 7070

CMD ["java", "-jar", "/app.jar"]