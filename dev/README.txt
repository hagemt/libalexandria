For developers. Here be dragons. Highly recommend:
$ source complete.sh

If you're building libalexandria and are anxious to get started,
we've included a sample user.CMakeCache.txt in this directory.
The bootstrap script snags this; default argument: "build"

From the top level of the repo:
$ ln dev/user.CMakeCache.txt
$ mkdir build-branch && cd build-branch
$ cmake .. && make
Or, you can simply run: ./dev/bootstrap.sh

Note that build generates some (lots of) files.
./bin and ./lib have the most obvious of these,
but we also have ./includes/libalexandria/jni, etc.

Use `git clean -nx` followed by `git clean -fx` to clean!
Remember this will clear your user CMakeCache!

Why don't you add your favorite development tips?
