JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)
JVM= java 
MAIN = Main
all: program

program: $(classes)

build: $(classes)

run: $(MAIN).class
	$(JVM) $(MAIN)
	
clean:
	rm -f *.class

%.class: %.java
	$(JAVAC) $<

jar: $(classes)
	jar cvf program.jar $(classes)

.PHONY: all program clean jar
