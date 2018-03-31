JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		jobscheduler.java \
		Job.java \
		MinHeapImplementation.java \
		RedBlackImplementation.java 

default: classes
	
classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class