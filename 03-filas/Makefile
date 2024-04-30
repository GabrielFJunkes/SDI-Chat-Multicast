# GNU Makefile
JAR=jar
JAVA=java
JAVAC=javac
CP=.:/usr/local/rabbitmq-jar/amqp-client-4.0.2.jar:/usr/local/rabbitmq-jar/slf4j-api-1.7.21.jar:/usr/local/rabbitmq-jar/slf4j-simple-1.7.22.jar

JFLAGS = -g -cp $(CP)
.SUFFIXES: .java .class
.java.class:
	$(JAVAC) $(JFLAGS) $*.java

CLASSES = \
	client/Client.java\
	server/Server.java\
	utils/Logger.java\

classes: $(CLASSES:.java=.class)

clean:
	rm -f *.class 
