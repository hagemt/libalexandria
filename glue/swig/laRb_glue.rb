#!/usr/bin/env ruby

puts "Hello, World!"
puts "You're a gem!"

require 'swagger.kolla'

puts "Result: #{Kolla.la_mark_incomplete(0)}"
