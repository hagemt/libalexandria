#!/usr/bin/env python

from os import environ

name = environ["SWIG_MODULE_NAME"]

print "Importing '",name,"' module."
import la_glue as glue
print "'la_initialize(42)' reports:"
glue.la_initialize(42)
print "'la_finalize(42)' reports:"
glue.la_finalize(42)
print "'la_print_info_incomplete(\"swig\")' reports:"
glue.la_print_info_incomplete("swig")
print "Attributes of '",name,"' module:"
for attr in dir(glue):
	print "\t(",attr,")"
