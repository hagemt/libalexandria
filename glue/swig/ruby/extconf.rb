#!/usr/bin/env ruby

require 'mkmf'

create_makefile('kolla')

open("Makefile", "a") { |mf| puts EOM }
