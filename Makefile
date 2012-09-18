CC=gcc
FC=f77
JC=javac
JH=javah
RM=rm -rf

#CFLAGS=-Wall -Wextra -DNDEBUG
CFLAGS=-Wall -Wextra -g -pedantic
JFLAGS=-classpath .:bindings/java

all: poc

bindings/java/libalexandria/POC.class: bindings/java/libalexandria/POC.java
	$(JC) $(JFLAGS) bindings/java/libalexandria/POC.java

include/libalexandria_POC.h: bindings/java/libalexandria/POC.class
	$(JH) $(JFLAGS) libalexandria.POC -o include/libalexandria_POC.h

libalexandria_POC.o: include/libalexandria_POC.h
	$(CC) $(CFLAGS) -c bindings/java/libalexandria_POC.c

laf_print.o: laf_print.f
	$(FC) -c laf_print.f

poc: libalexandria_POC.o laf_print.o
	$(CC) $(CFLAGS) libalexandria_POC.o laf_print.o -lc

clean:
	$(RM) bindings/java/libalexandria/POC.class
	$(RM) include/libalexandria_POC.h
	$(RM) libalexandria_POC.o
	$(RM) laf_print.o
	$(RM) poc

.PHONY: all poc clean
