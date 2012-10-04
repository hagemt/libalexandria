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
of Concerns, those being, specifically, and not otherwise:

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
(see Liskov) gives us, to organize concepts and communicate them to
our users with extreme clarity. No expense should be spared to
maintain this transparency.

Naturally, this can make the base of libalexandria, henceforth
referred to as laX (standing for libalexandria X, where X is a
short string encoding the implementation language) quite arcane.
Through more general use, these procedures will become bound
to nodes in the hierarchy of bindings, which will provide
myriad interfaces to your wonderful heap of technical wizardry.

(As an example, the prefix laF is used to reference .F codes.)

Note: in general, try to use upper case for X labels.

Note: the native code for JNI currently does not conform.

Hopefully, you will agree that there are at least two concerns
represented here, and so this division makes logical sense to you.
If not, please discuss alternatives via the project wiki.
So, that is it. Find your niche and start your engines.

Features
--------

The Library of Alexandria will provide:

* Maximum-likelihood estimates in an efficient manner (planned)
* Many varieties of sampling tools (planned, sorta written)
* Neat (Android!) interfaces to (native optimized) machine learning
    * Reinforcement: "cortical" HTM using sparse representations (planned)
    * Reinforcement: traditional Artifical neural network (planned)
    * Reinforcement: Hidden markov models (planned, currently abstract)
    * Supervised: SVM w/ or w/o Kernel (planned, Kernel implemented)
    * Supervised: Linear/Logistic Regression (planned)
    * Unsupervised: K-means and other clustering algorithms (planned)
    * Unsupervised: Signal separation algorithms (not yet planned)
* Extreme levels of introspection for at top layer (Java)
* Many other yet-undocumented features, bridging many layers of abstraction

# TODO FUTURE flesh this out with more of the awesome, especially Java API

History
-------

libalexandria began in September 2012 as a personal project.

If you contribute to it, you may wish to add your name as per the section below.

See [Building] for development notes.

Licensing
---------

See ./LICENSE (note that all of libalexandira uses LGPL v3, including all bindings)
See ./LICENCE.txt for form to include in files

For those familiar with bashisms: (a lot of people)
declare -a LANGUAGES
LANGUAGES=('C', 'Fortran')

A  ./LICENSE.txt suitable for inclusion in any file written in a language
that supports LANG-style comment syntax can be found in LICENSE.$l, where
LANG is a member of ${LANGUAGES[@]}.

You may ask yourself "But why LGPL then? Wouldn't the some BSD be better?"
For most applications that seek to capitalize, sure. But this is a library.

Not only that, it is a library, which if you contribute code to, the authors feel
that you are obligated to giving those contributions back to the community.

"But I can't use your library because I don't want to use GPL for my app."

A common misconception. Feel free to use a GPL-compatiple BSD license, just for example.
Where you put the source is really up to you. If you doubt our juris prudence, see [1],
where it explicitly notes "Linking from code with a different license" with 'Yes'.

[1] http://en.wikipedia.org/wiki/GNU_Lesser_General_Public_License

Note: to distinguish terms, The Free Software Foundation calls the GPL-style restrictions
(you must release any modifications under the same licence) 'copyleft'. [2]

Always note that, when selecting a license generally, it is good to do your homework.

If libalexandria were licensed under a general BSD license, for example, the contributors
to libalexandria would not be obligated to give back their contributions, and there is
some question as to weather an app could be licensed under GPL v2+.

# TODO FUTURE check this last statement rigorously with law professional

[2] http://en.wikipedia.org/wiki/List_of_FSF_approved_software_licenses

Building
--------

Currently, libalexandria uses `cmake` in order to offer broad support
for building on multiple platforms and under a variety of configurations.

Developer testing of libalexandria currently targets:

* GNU Toolchain (or Eclipse with CMake abilities)
    * gcc
    * gfortran
    * gcj (TODO fix strange symbol problem? dev is using proprietary JDK for now)

* MVSC? (TODO someone willing to test this?)
    * TODO also add a minimum version system/IDE

* Xcode? (TODO someone willing to test this?)
    * TODO also add a minimum version system/IDE

Contributors
------------

See ./AUTHORS.txt

Please add your name to this file in the commit for any pull-request you send me.
