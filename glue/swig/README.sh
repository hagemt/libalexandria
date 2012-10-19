#!/usr/bin/env bash

SWIG_CMD=$(which swig)
CC_CMD=$(which cc)
LD_CMD=$(which ld)
GIT_CMD=$(which git)

INTERFACES="la_glue.i"
SOURCES="./la_glue_wrap.c ../la_glue.c"
INCLUDES="-I/usr/include/python2.6 -I/usr/local/include/libcalg-1.0 -I../../include/libalexandria"
export SWIG_MODULE_NAME="_la_glue.so"

# Experimenting with SWIG using the following:
$SWIG_CMD -python $INTERFACES
$CC_CMD -c $SOURCES -fPIC $INCLUDES
$LD_CMD -shared *.o -lcalg -o $SWIG_MODULE_NAME
./laPy_glue.py
$GIT_CMD clean -fx
