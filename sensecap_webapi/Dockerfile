FROM maven:3.3.3
ARG ./sensecap_webapi/target/sensecap_webapi-0.0.1-SNAPSHOT.jar
ADD ./sensecap_webapi/target/sensecap_webapi-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT java -jar  /app.jar
EXPOSE 9001