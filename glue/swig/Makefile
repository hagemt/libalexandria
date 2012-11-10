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

### experimental

label  := swagger
prefix := .

CC   := `which gcc`
LD   := `which ld`
LN   := `which ln` -s
RM   := `which rm` -fv
SWIG := `which swig`
PYI  := PYTHONPATH=./lib `which python` -iSv
RBI  := `which ruby`

# TODO can do ln -st for lib SONAMEs?
INSTALL := `which install` -st $(prefix)
SAY     := >/dev/tty `which echo` -e '[$(label)]'
# TODO can make this text (label, here) red? ^

CFLAGS_CALG    := `pkg-config --cflags libcalg-1.0`
CFLAGS_DEBUG   := -g -pedantic
CFLAGS_RELEASE := -O3 -DNDEBUG
CFLAGS         := -Wall -Wextra -fPIC -I./include $(CFLAGS_CALG)

CFLAGS_PYTHON  := -I/usr/include/python2.6
CFLAGS_RUBY    := -I/usr/include/ruby-1.9.1

LDFLAGS_CALG   := `pkg-config --libs libcalg-1.0`
LDFLAGS        := -shared $(LDFLAGS_CALG)

### phony targets

all: doc test
	@touch ./lib/swagger/__init__.py
	@cp kolla.py ./lib/swagger
	@cp kolla.python.so ./lib/swagger/_kolla.so
	@$(SAY) "running python script..."
	$(PYI) ./spec/laPy_glue.py
	@$(SAY) "running ruby script..."
	@cp swagger.rb ./lib
	@cp kolla.rb ./lib/swagger
	#$(RBI) ./spec/laRb_glue.rb

swagger: lib/swagger swagger.i kolla

kolla: kolla.python.so kolla.ruby.so

.PHONY: all swagger kolla

clean:
	$(RM) *.py
	$(RM) *_wrapper.c
	$(RM) *.o
	$(RM) *.so
	$(RM) -r lib/swagger
	$(RM) include lib src spec

install: swagger
	$(INSTALL)/lib/swagger *kolla*.so*

uninstall:
	$(RM) $(prefix)/lib/swagger/*kolla*.so*

.PHONY: clean install uninstall

configure: include lib src spec

doc: configure
	@$(SAY) "no documentation"

test: configure swagger
	@$(SAY) "no tests"

.PHONY: doc test

### file targets

include:
	$(LN) ../../include/libalexandria $@

lib:
	$(LN) ../../lib $@

lib/%: lib
	mkdir -p $@

src:
	$(LN) .. $@

spec:
	$(LN) ../../tests/swig $@

## patterns

%_python_wrapper.c: %.i
	$(SWIG) -python -o $@ $+

%_python_wrapper.o: %_python_wrapper.c
	$(CC) $(CFLAGS) $(CFLAGS_PYTHON) -c $+ -o $@

%.python.so: %_python_wrapper.o lib%.so
	$(LD) $(LDFLAGS) $(LDFLAGS_PYTHON) $+ -o $@

%_ruby_wrapper.c: %.i
	$(SWIG) -ruby -o $@ $+

%_ruby_wrapper.o: %_ruby_wrapper.c
	$(CC) $(CFLAGS) $(CFLAGS_RUBY) -c $+ -o $@

%.ruby.so: %_ruby_wrapper.o lib%.so
	$(LD) $(LDFLAGS) $(LDFLAGS_RUBY) $+ -o $@

la_%.o: src/la_%.c
	$(CC) $(CFLAGS) -c $+ -o $@

## libkolla

src/la_glue.c: include/la_glue.h include/la_helper.h include/la_log.h

src/la_helper.c: include/la_glue.h include/la_helper.h

src/la_log.c: include/la_log.h

libkolla.so: la_glue.o la_helper.o la_log.o
	$(LD) $(LDFLAGS) $+ -o $@
