This directory contains glue code for libalexandria.

Like any complex system, we have dependencies:
 * core: libblas libcalg
 * bindings: (none additional)
 * tests: (none additional)
 * android: (android-8 or higher)
Please update here and the wiki if this changes.

You need the c-algorithms library to build libalexandria.
For convenience: git submodule update --init glue/libcalg
pushd glue/libcalg; ./configure && make && make install; popd

If you are working with C++, note that tclap is included. :)
For wonderful header-only cmdline parsing, see: glue/tclap
No compilation required! (just #include "tclap/CmdLine.h")

Please see ./android/README.txt, if you are an Android developer.
Remember: git submodule update --init android/android-cmake
