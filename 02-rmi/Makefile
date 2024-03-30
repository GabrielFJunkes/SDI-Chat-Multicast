# GNU Makefile
JAR=jar
JAVA=java
JAVAC=javac

JFLAGS = -g 
.SUFFIXES: .java .class
.java.class:
	$(JAVAC) $(JFLAGS) $*.java

CLASSES = \
	server/Client.java\
	server/IDatabase.java\
	server/IMatrix.java\
	client/ServerDatabase.java\
	client/ServerMatrix.java\

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f ./*/*.class 