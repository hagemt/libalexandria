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

SHELL = /bin/sh

label  := swagger
type   := RELEASE
prefix := $(shell readlink -f .)

CC   := $(shell which gcc)
LD   := $(shell which ld)
LN   := $(shell which ln) -s
RM   := $(shell which rm) -fv
PC   := $(shell which pkg-config)
SWIG := $(shell which swig)
PYI  := PYTHONPATH=./lib $(shell which python) -iSv
RBI  := RUBYPATH=./lib   $(shell which ruby)    -Sv

# TODO can do ln -st for lib SONAMEs?
INSTALL := $(shell which install) -st $(prefix)
SAY     := $(shell which echo) >/dev/tty -e '[$(label)]'
# TODO can make this text (label, here) red? ^

CFLAGS_DEBUG   := -g -pedantic -DLA_LOG_NAME=$(label)-dbg
CFLAGS_RELEASE := -O3 -DNDEBUG -DLA_LOG_NAME=$(label)-dev

CFLAGS_CALG    := $(shell $(PC) --cflags libcalg-1.0)
CFLAGS_EXTRA   := -I./include $(CFLAGS_CALG) $(CFLAGS_$(type))
CFLAGS         := -Wall -Wextra -fPIC $(CFLAGS_EXTRA)

CFLAGS_PYTHON  := -I/usr/include/python2.6
CFLAGS_RUBY    := -I/usr/include/ruby-1.9.1

LDFLAGS_CALG   := $(shell $(PC) --libs libcalg-1.0)
LDFLAGS_EXTRA  := -L./lib $(LDFLAGS_CALG)
LDFLAGS        := -shared $(LDFLAGS_EXTRA)

LDFLAGS_PYTHON := -L/usr/lib/python/2.6/config -lpython2.6
LDFLAGS_RUBY   := -L/usr/lib/ruby/1.9.1/x86_64-linux -lruby-1.9.1

### phony targets

all: doc test

swagger: lib/swagger swagger.i kolla

kolla: kolla.python.so kolla.ruby.so

.PHONY: all swagger kolla

clean:
	$(RM) *.py
	$(RM) *.rb
	$(RM) *_wrap.c
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
	@touch $(prefix)/lib/swagger/__init__.py
	@cp kolla.py $(prefix)/lib/swagger
	@cp kolla.python.so ./lib/swagger/_kolla.so
	@$(SAY) "running sample python script(s)"
	$(PYI) ./spec/laPy_glue.py
	@cp ruby/swagger.rb ./lib
	@cp ruby/kolla.rb ./lib/swagger
	@cp kolla.ruby.so ./lib/swagger/kolla.so
	@$(SAY) "running sample ruby script(s)"
	$(RBI) ./spec/laRb_glue.rb
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

## % patterns

# for python

%_python_wrap.c: %.i
	$(SWIG) -python -o $@ $+

%_python_wrap.o: %_python_wrap.c
	$(CC) $(CFLAGS) $(CFLAGS_PYTHON) -c $+ -o $@

%.python.so: %_python_wrap.o lib%.so
	$(LD) $(LDFLAGS) $(LDFLAGS_PYTHON) $+ -o $@

# for ruby

%_ruby_wrap.c: %.i
	$(SWIG) -ruby -o $@ $+

%_ruby_wrap.o: %_ruby_wrap.c
	$(CC) $(CFLAGS) $(CFLAGS_RUBY) -c $+ -o $@

%.ruby.so: %_ruby_wrap.o lib%.so
	$(LD) $(LDFLAGS) $(LDFLAGS_RUBY) $+ -o $@

# for C

la_%.o: src/la_%.c
	$(CC) $(CFLAGS) -c $+ -o $@

## libkolla

src/la_glue.c: include/la_glue.h include/la_helper.h include/la_log.h

src/la_helper.c: include/la_glue.h include/la_helper.h

src/la_log.c: include/la_log.h

libkolla.so: la_glue.o la_helper.o la_log.o
	$(LD) $(LDFLAGS) $+ -o $@
