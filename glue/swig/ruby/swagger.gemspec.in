require 'rubygems'

Gem::Specification.new do |gem|
	gem.name        = "swagger"
	gem.version     = "0.0.1"
	gem.summary     = "Ruby bindings for libalexandria"
	gem.description = "A fast and easy-to-use library for machine learning."

	gem.authors     = [ "Tor E Hagemann" ]
	gem.email       = [ "hagemt@rpi.edu" ]
	gem.homepage    = "http://hagemt.github.com/libalexandria/"

	gem.platform                  = Gem::Platform::RUBY
	gem.rubyforge_project         = "libalexandria"
	gem.required_rubygems_version = ">= 1.3.6"

	gem.extensions   = 'extconf.rb'
	gem.files        = `find ../lib -type f -iname *.rb -print0`.split("\0")
end
