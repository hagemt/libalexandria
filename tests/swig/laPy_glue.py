#!/usr/bin/env python

from swagger import kolla as laPy

print "Imported symbols as attributes of 'laPy'"
for attr in dir(laPy):
	if not attr.startswith('_'):
		print "\t(%s)" % attr

print 'laPy.la_initialize(42) reports:'
laPy.la_initialize(42)
print 'laPy.la_finalize(42) reports:'
laPy.la_finalize(42)
print 'laPy.la_mark_incomplete("laPy") reports:'
laPy.la_mark_incomplete("laPy")
