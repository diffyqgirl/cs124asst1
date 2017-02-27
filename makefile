JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	pigs.java \
	MinHeap.java \
	HeapElt.java \
	Vertex.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class