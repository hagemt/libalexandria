For developers. Here be dragons.

If you're building libalexandria and are anxious to get started,
we've included a sample user.CMakeCache.txt in this directory.

From the top level of the repo:
$ ln dev/user.CMakeCache.txt
$ mkdir build-branch && cd build-branch
$ cmake .. && make

Note that build generates some files.
./bin and ./lib are the most obvious of these, but for Java:
We also have ./includes/libalexandria/jni, etc...
Use `git clean -nx` followed by `git clean -fx` to clean!
Remember this will clear your user CMakeCache!
