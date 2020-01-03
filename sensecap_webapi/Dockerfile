FROM maven:3.3.3
RUN mkdir /sensecap_webapi
WORKDIR /sensecap_webapi
ADD ./sensecap_webapi/pom.xml  .
ADD ./sensecap_webapi/src  ./src
RUN cd /sensecap_webapi/ && mvn -q -DskipTests=true package
     #构建应用

RUN cd /sensecap_webapi \
        #拷贝编译结果到指定目录
        && mv target/sensecap_webapi-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT java -jar  /app.jar
EXPOSE 9001