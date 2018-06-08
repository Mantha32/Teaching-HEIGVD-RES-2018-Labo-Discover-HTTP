FROM java:openjdk-8-jre-alpine 


LABEL description="Create the basic http server \
zhis server provide a basic clock \
The client can request the current time and can post an alter time to modify the current one .\
The default http port is 8080. You can change the http port as you will.\
The defualt port is 8080. "
LABEL authors="Yosra Harbaoui, Iando Rafidimalala"
LABEL version="1.0"


#
# Replace the directory with your own directory that stands the source file
# example /tmp/http/src
#
ENV DIRECTORY /tmp/http/src/

ADD src DIRECTORY

WORKDIR DIRECTORY

#The container listen in this specific ports
#The port 8080/tcp is used to smtp server
#EXPOSE 8080


#The default argument for the ENTREPOINT
#The argument "-p 8080" stand for the port the http server listen to do the http conversation with the client such browser, netcat, telnet, curl

ENTRYPOINT ["java","-jar","DiscoverHTTP-1.0-SNAPSHOT-standalone.jar"]
CMD ["172.17.0.2","8080","GET","/","text/html", ""]
