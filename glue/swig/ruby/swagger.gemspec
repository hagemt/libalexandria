Gem::Specification.new do |s|
	s.name              = "swagger"
	s.version           = "0.0.1"
	s.platform          = Gem::Platform::RUBY
	s.authors           = ["Tor E Hagemann"]
	s.email             = ["hagemt@rpi.edu"]
	s.homepage          = "http://hagemt.github.com/libalexandria/"
	s.summary           = "Ruby bindings for libalexandria"
	s.description       = "A fast and easy-to-use library for machine learning."
	s.rubyforge_project = "libalexandria"

	s.required_rubygems_version = ">= 1.3.6"

	# s.add_runtime_dependency "other", "~> 1.2"
	# s.add_development_dependency "another", "= 0.9"

	# s.files        = `git ls-files`.split("\n")
	# s.executables  = `git ls-files`.split("\n").map{|f| f =~ /^bin\/(.*)/ ? $1 : nil}.compact
	# s.extensions   = `git ls-files ext/extconf.rb`.split("\n")
	s.require_path = 'lib'
	# s.extensions   = "ext/extconf.rb"
end
