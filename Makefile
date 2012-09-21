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
RM=rm -rfv
# Flags
#CFLAGS=-Wall -Wextra -DNDEBUG -I include/ -fPIC
CFLAGS=-Wall -Wextra -g -pedantic -I include/ -fPIC
FFLAGS=-cpp -shared -fPIC -I include/
JFLAGS=-classpath .:bindings/java

all: bin/poc lib/libalexandria.so

# FIXME break this into per-directory files? (or switch build system) CMake?

# For Java/JNI bridge

bindings/java/libalexandria/proof/POC.class: bindings/java/libalexandria/proof/POC.java
	$(JC) $(JFLAGS) bindings/java/libalexandria/proof/POC.java

# XXX is this name system dependent?
include/libalexandria_POC.h: bindings/java/libalexandria/proof/POC.class
	$(JH) $(JFLAGS) libalexandria.proof.POC

# For libalexandriaF

bindings/java/laF_print.c: include/libalexandria_POC.h include/laF_print.h include/laF_constants.h

bindings/java/laF_print.o: bindings/java/laF_print.c
	$(CC) $(CFLAGS) -c bindings/java/laF_print.c -o bindings/java/laF_print.o

lib/libalexandriaF.so.0.1: laF_SVM.F laF_print.F include/laF_constants.h
	$(FC) $(FFLAGS) laF_SVM.F laF_print.F -o lib/libalexandriaF.so.0.1

lib/libalexandriaF.so.0: lib/libalexandriaF.so.0.1
	$(LN) libalexandriaF.so.0.1 lib/libalexandriaF.so.0

lib/libalexandriaF.so: lib/libalexandriaF.so.0
	$(LN) libalexandriaF.so.0 lib/libalexandriaF.so

# For libalexandria
# TODO needs java library path for JNI?

lib/libalexandria.so.0.1: bindings/java/laF_print.o lib/libalexandriaF.so.0.1
	$(LD) bindings/java/laF_print.o lib/libalexandriaF.so.0.1 -lc -lgfortran -o lib/libalexandria.so.0.1

lib/libalexandria.so.0: lib/libalexandria.so.0.1
	$(LN) libalexandria.so.0.1 lib/libalexandria.so.0

lib/libalexandria.so: lib/libalexandria.so.0
	$(LN) libalexandria.so.0 lib/libalexandria.so

# For proof-of-concept

bin/poc: bindings/java/laF_print.o lib/libalexandriaF.so
	$(FC) bindings/java/laF_print.o lib/libalexandriaF.so -lc -o bin/poc

test: lib/libalexandria.so
	java -cp bindings/java -Djava.library.path=./lib:$(LD_LIBRARY_PATH) libalexandria.proof.POC

clean:
	$(RM) bin/*
	$(RM) lib/*
	# TODO find a (better) way to remove all class and object files
	$(RM) bindings/java/proof/*.class
	$(RM) *.o bindings/java/*.o
	# TODO find a way to remove all generated headers
	$(RM) include/libalexandria_*.h

.PHONY: all test clean
