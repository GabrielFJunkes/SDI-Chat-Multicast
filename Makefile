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

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f *.class 