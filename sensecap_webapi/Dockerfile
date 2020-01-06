FROM maven:3.3.3
RUN mkdir /sensecap_webapi
WORKDIR /sensecap_webapi
ADD ./sensecap_webapi/pom.xml  .
ADD ./sensecap_webapi/src  ./src

# maven package application
RUN cd /sensecap_webapi/ && mvn -q -DskipTests=true package
<<<<<<< HEAD

# move jar to the destination
=======
>>>>>>> c59770ddddedec936518c177429c3f30169d5c43
RUN cd /sensecap_webapi \
        && mv target/sensecap_webapi-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT java -jar  /app.jar
EXPOSE 9001
