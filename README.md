libalexandria
=============

This README file contains information regarding libalexandria,
a humorously named for its inclusive breadth of FORTRAN code,
bound using C/C++, all the way "through the ages" into Java,
and further extensible using future interfaces, many and more
of which have even yet to be imagined.

libalexandria is a library for machine learning! In the
coming months, we plan to implement a large variety of
models. So stay tuned!

Philosophy
----------

libalexandria is envisioned to address a very defined Separation
of Concerns, those being, specifically, and not otherwise:

> Simple Interface <-- Efficient Bridge --> Advanced Computation

So broadly stated, we implement this philosophy using the Java
Native Interface (JNI) to provide a data-transformational intermediary
layer, which permits (or to varying degrees, facilitates) the
use of much less abstract (closer-to-the-bits) implementation of
numerically precise and efficient codes (C, C++, even FORTRAN)
that turn the focus away from general usability and instead
maximize the speed and accuracy of computation.

As such, the top layer of libalexandria is quite abstract,
perhaps exceedingly (ridiculously) so, by design. Here is where
we allow ourselves this, in the name of the power that abstraction
(see Liskov) gives us, to organize concepts and communicate them to
our users with extreme clarity. No expense should be spared to
maintain this transparency.

Naturally, this can make the base of libalexandria, henceforth
referred to as la, quite arcane. (Instances of "la" within code
stand for "libalexandria." Oftentimes, this prefix may include
a few additional characters which encode the implementation
language. For example, laF_ denotes FORTRAN codes for "la.")
Through more general use, these procedures will become bound
to nodes in the hierarchy of bindings, which will provide a
myriad of interfaces which you may leverage as you please
with your own technical wizardry.

Hopefully, you will agree that there are at least two concerns
represented here, and so this division makes logical sense to you.
If not, please discuss alternatives via the project wiki.
So, that is it. Find your niche and start your engines.

Features
--------

The Library of Alexandria will provide easy methods for:

* Efficiently calculating maximum-likelihood estimates (planned)
* Many varieties of sampling tools (planned, sorta written)
* Neat (Android!) interfaces to (native optimized) machine learning
    * Reinforcement: "cortical" HTM using sparse representations (planned)
    * Reinforcement: traditional Artificial neural network (planned)
    * Reinforcement: Hidden Markov models (planned, currently abstract)
    * Supervised: SVM w/ or w/o Kernel (planned, Kernel implemented)
    * Supervised: Linear/Logistic Regression (planned)
    * Unsupervised: K-means and other clustering algorithms (planned)
    * Unsupervised: Signal separation algorithms (not yet planned)
* Extreme levels of introspection for at top layer (Java)
* Many other yet-undocumented features, bridging many layers of abstraction

History
-------

libalexandria began in September 2012 as a personal project.

See [Building] for development notes.

If you contribute to it, you may wish to seek recognition as per the section below.

Licensing
---------

See ./LICENSE (note that all of libalexandria uses LGPL v3, including all bindings)
See ./LICENSE.txt for form to include in files

For those familiar with python: (a lot of people)
LANGUAGE_LOOKUP = {
	'C': 'C'
	'C': 'C++'
	'C': 'Java'
	'F': 'FORTRAN'
	'TXT': 'cmake'
	'TXT': 'python'
	'TXT': 'shell'
	'TXT': 'text'
}

A  ./LICENSE.txt suitable for inclusion in files written in languages
enumerated above (value entries) can be found in LICENSE.LANG, where LANG
is the corresponding key in the LANGUAGE_LOOKUP dictionary.

You may ask yourself "But why LGPL? Wouldn't some BSD be better?"

For most applications that seek to capitalize, sure. But this is a library. Not
only that, it is a library, which if you contribute code to, the authors feel
that you are obligated to giving those contributions back to the community.

"But I can't use your library because I don't want to use GPL for my app."

That's fine. No one is forcing you to use GPL, me included. This is a common
misconception with licensing. Not all GPL-licensed code is "viral" like that.

Feel free to use a GPL-compatible BSD license, just for example. Where you
put the source is really up to you. If you doubt our juris prudence, see [1],
where it explicitly addresses "Linking from code with a different license."

[1] http://en.wikipedia.org/wiki/GNU_Lesser_General_Public_License

Note: to distinguish terms, The Free Software Foundation calls the GPL-style restrictions
(you must release any modifications under the same license) 'copyleft'. [2]

Always note that, when selecting a license generally, it is good to do your homework.

If libalexandria were licensed under a general BSD license, for example, the contributors
to libalexandria would not be obligated to give back their contributions, and there is
some question as to whether an linked app even could be licensed under GPL v2+.

[2] http://en.wikipedia.org/wiki/List_of_FSF_approved_software_licenses

Building
--------

Currently, libalexandria uses `cmake` in order to offer broad support
for building on multiple platforms and under a variety of configurations.

Developer testing of libalexandria currently targets:

* GNU Toolchain (or Eclipse with CMake abilities)
    * `gcc`
    * `gfortran`
    * `gcj` (TODO fix strange symbol problem? dev is using proprietary JDK for now)
* MVSC? (TODO someone willing to test this?)
    * TODO also add a minimum version system/IDE
* Xcode? (TODO someone willing to test this?)
    * TODO also add a minimum version system/IDE

Contributors
------------

See ./AUTHORS.txt

Please add your name to that file in the commit for any pull-request you send.
