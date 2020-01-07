FROM maven:3.3.3
RUN mkdir /sensecap_webapi
WORKDIR /sensecap_webapi
ADD ./sensecap_webapi/pom.xml  .
ADD ./sensecap_webapi/src  ./src

# maven package application
RUN cd /sensecap_webapi/ && mvn -DskipTests=true package

# move jar to the destination
RUN cd /sensecap_webapi \
        && mv target/sensecap_webapi-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT java -jar  /app.jar
EXPOSE 9001
