libalexandria
=============

This README file contains information regarding libalexandria,
a humorously named for its inclusive breadth of FORTRAN code,
bound using C/C++, all the way "through the ages" into Java,
and further entensible using future interfaces, many and more
of which have even yet to be imagined.

Philosophy
----------

libalexandria is envisioned to address a very defined Separation
of Concerns: those being, specifically, and not otherwise:

Simple Interface <-- (Efficient Bridge) --> Advanced Computation

So broadly stated, we implement this philosophy using the Java
Native Interface (JNI) to provide a data-transformational intermediary
layer, which permits (or to varying degrees, facilitates) the
use of much less abstract (closer-to-the-bits) implementation of
numerically precise and efficient codes (C, C++, even FORTRAN)
that turn the focus away from general usability and instead
maximize the speed and accuracy of computation.

As such, the top layer of libalexandria is quite abstract,
perhaps exceedingly (rediculously) so, by design. Here is where
we allow ourselves this, in the name of the power that abstraction
(see Liskov) give us, to organize concepts and communicate them to
our users with extreme clarity. No expense should be spared to
maintain this transparency.

Naturally, this can make the base of libalexandria, henceforth
referred to as laX (standing for libalexandria X, where X is a
short string encoding the implementation language) quite arcane.
Through more general use, these procedures will become bound
to nodes in the hierarchy of bindings, which will provide
myriad interfaces to your wonderful heap of technical wizardry.

(As an example, the prefix laF is used to reference .F codes.)

So, that is it. Find your niche and start your engines.

Features
--------

The Library of Alexandria will provide:

* A easy mechanism for any program to produce:
     * Maximum-likelihood estimates in an efficient manner
* Many other planned features, bridging many layers of abstraction

History
-------

libalexandria began in September 2012 as a personal project.
If you contribute to it, you may wish to add your name below.

Building
--------

Currently, libalexandria uses `make`; however, anticipate this
to change as the project evolves, in order to offer broader support.

Developer testing of libalexandria involves:

* gcc
* gfortran
* openjdk

Contributors
------------

Tor E Hagemann <hagemt@rpi.edu>
