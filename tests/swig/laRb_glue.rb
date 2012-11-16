#!/usr/bin/env ruby

puts "Hello, World!"
puts "You're a gem!"

begin
	require 'swagger'
	Kollab.oration
rescue LoadError
	puts "[error] 'swagger::kolla' (cannot load module)"
end
