For developers. Here be dragons.

If you're building libalexandria and are anxious to get started,
we've included a sample user.CMakeCache.txt in settings.user.txt

From the top level of the repo:
$ ln dev/settings.user.txt user.CMakeCache.txt
$ mkdir build-branch && cd build-branch
$ cmake .. && make
