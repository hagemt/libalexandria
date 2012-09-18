# Compilers
CC=gcc
FC=gfortran
JC=javac
# Utilities
JH=javah -jni -d include/
LD=gcc -shared
LN=ln -s
RM=rm -rf
# Flags
#CFLAGS=-Wall -Wextra -DNDEBUG -I include/
CFLAGS=-Wall -Wextra -g -pedantic -I include/ -fPIC
FFLAGS=-shared -fPIC
JFLAGS=-classpath .:bindings/java

all: bin/poc lib/libalexandria.so

bindings/java/libalexandria/POC.class: bindings/java/libalexandria/POC.java
	$(JC) $(JFLAGS) bindings/java/libalexandria/POC.java

include/libalexandria_POC.h: bindings/java/libalexandria/POC.class
	$(JH) $(JFLAGS) libalexandria.POC

libalexandria_POC.o: include/libalexandria_POC.h
	$(CC) $(CFLAGS) -c bindings/java/libalexandria_POC.c

lib/liblaf.so: laf_print.f
	$(FC) $(FFLAGS) laf_print.f -o lib/liblaf.so

lib/libalexandria.so.0.1: libalexandria_POC.o lib/liblaf.so
	$(LD) libalexandria_POC.o lib/liblaf.so -lc -lgfortran -o lib/libalexandria.so.0.1

lib/libalexandria.so.0: lib/libalexandria.so.0.1
	$(LN) libalexandria.so.0.1 lib/libalexandria.so.0

lib/libalexandria.so: lib/libalexandria.so.0
	$(LN) libalexandria.so.0 lib/libalexandria.so

bin/poc: libalexandria_POC.o lib/liblaf.so
	$(FC) libalexandria_POC.o lib/liblaf.so -lc -o bin/poc

test: lib/libalexandria.so
	echo "Testing..." | java -cp bindings/java -Djava.library.path=$(LD_LIBRARY_PATH) libalexandria.POC

clean:
	$(RM) bin/*
	$(RM) lib/*
	$(RM) bindings/java/libalexandria/POC.class
	$(RM) include/libalexandria_POC.h
	$(RM) libalexandria_POC.o

.PHONY: all test clean
