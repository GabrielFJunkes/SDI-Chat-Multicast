# GNU Makefile
JAR=jar
JAVA=java
JAVAC=javac

JFLAGS = -g 
.SUFFIXES: .java .class
.java.class:
	$(JAVAC) $(JFLAGS) $*.java

CLASSES = \
	server/ClientThread.java\
	server/Multicast.java\
	server/Server.java\
	client/Client.java\
	client/Multicast.java\

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f *.class 