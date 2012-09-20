#    This file is part of libalexandria.
#
#    libalexandria is free software: you can redistribute it and/or modify
#    it under the terms of the GNU Lesser General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    libalexandria is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU Lesser General Public License for more details.
#
#    You should have received a copy of the GNU Lesser General Public License
#    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.

# Compilers
CC=gcc
FC=gfortran
JC=javac
# Utilities
JH=javah -jni -d include/
LD=gcc -shared
LN=ln -sf
RM=rm -rf
# Flags
#CFLAGS=-Wall -Wextra -DNDEBUG -I include/
CFLAGS=-Wall -Wextra -g -pedantic -I include/ -fPIC
FFLAGS=-cpp -shared -fPIC -I include/ -fPIC
JFLAGS=-classpath .:bindings/java

all: bin/poc lib/libalexandria.so

bindings/java/libalexandria/POC.class: bindings/java/libalexandria/POC.java
	$(JC) $(JFLAGS) bindings/java/libalexandria/POC.java

# XXX is this name system dependent?
include/libalexandria_POC.h: bindings/java/libalexandria/POC.class
	$(JH) $(JFLAGS) libalexandria.POC

bindings/java/laf_print.c: include/libalexandria_POC.h include/laf_print.h include/laf_constants.h

bindings/java/laf_print.o: bindings/java/laf_print.c
	$(CC) $(CFLAGS) -c bindings/java/laf_print.c -o bindings/java/laf_print.o

lib/liblaf.so.0.1: laf_svm.f laf_print.f include/laf_constants.h
	$(FC) $(FFLAGS) laf_svm.f laf_print.f -o lib/liblaf.so.0.1

lib/liblaf.so.0: lib/liblaf.so.0.1
	$(LN) liblaf.so.0.1 lib/liblaf.so.0

lib/liblaf.so: lib/liblaf.so.0
	$(LN) liblaf.so.0 lib/liblaf.so

lib/libalexandria.so.0.1: bindings/java/laf_print.o lib/liblaf.so
	$(LD) bindings/java/laf_print.o lib/liblaf.so -lc -lgfortran -o lib/libalexandria.so.0.1

lib/libalexandria.so.0: lib/libalexandria.so.0.1
	$(LN) libalexandria.so.0.1 lib/libalexandria.so.0

lib/libalexandria.so: lib/libalexandria.so.0
	$(LN) libalexandria.so.0 lib/libalexandria.so

bin/poc: bindings/java/laf_print.o lib/liblaf.so
	$(FC) bindings/java/laf_print.o lib/liblaf.so -lc -o bin/poc

test: lib/libalexandria.so
	java -cp bindings/java -Djava.library.path=./lib:$(LD_LIBRARY_PATH) libalexandria.POC

clean:
	$(RM) bin/*
	$(RM) lib/*
	$(RM) bindings/java/libalexandria/POC.class
	$(RM) bindings/java/laf_print.o
	$(RM) include/libalexandria_POC.h
	$(RM) laf_print.o

.PHONY: all test clean
