FROM java:openjdk-8-jre
MAINTAINER Anders Borch <anders@cyborch.com>

ENV SERVER_PREFIX /opt/omh-dsu-ri/private-server
 
RUN mkdir -p $SERVER_PREFIX
ADD private-server.jar $SERVER_PREFIX/
EXPOSE 8084

CMD /usr/bin/java -jar $SERVER_PREFIX/private-server.jar --spring.config.location=file:$SERVER_PREFIX/
