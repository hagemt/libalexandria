# Compilers
CC=gcc
FC=gfortran
JC=javac
# Utilities
JH=javah -jni -d include/
RM=rm -rf
# Flags
#CFLAGS=-Wall -Wextra -DNDEBUG -I include/
CFLAGS=-Wall -Wextra -g -pedantic -I include/
FFLAGS=-g -c
JFLAGS=-classpath .:bindings/java

all: poc

bindings/java/libalexandria/POC.class: bindings/java/libalexandria/POC.java
	$(JC) $(JFLAGS) bindings/java/libalexandria/POC.java

include/libalexandria_POC.h: bindings/java/libalexandria/POC.class
	$(JH) $(JFLAGS) libalexandria.POC

libalexandria_POC.o: include/libalexandria_POC.h
	$(CC) $(CFLAGS) -c bindings/java/libalexandria_POC.c

laf_print.o: laf_print.f
	$(FC) $(FFLAGS) laf_print.f

poc: libalexandria_POC.o laf_print.o
	$(FC) $(CFLAGS) libalexandria_POC.o laf_print.o -lc -o poc

clean:
	$(RM) bindings/java/libalexandria/POC.class
	$(RM) include/libalexandria_POC.h
	$(RM) libalexandria_POC.o
	$(RM) laf_print.o
	$(RM) poc

.PHONY: all poc clean
