In order to use Eclipse for libalexandria Android development:

 1) Copy or link the files in this directory to your project.
 2) Note the 'jni' tree is empty; it is organized in: ./bindings/java/jni
    The presence of this folder is merely a reminder that the NDK expects it.
    If you configure using CMake, a sample Android.mk will be generated here.
    However you prefer to develop, we want libalexandria to support it.

Here are some Android CMake installation notes;
the process is adapted from the android-cmake project:

We recomment running the following command in ./glue:
$ hg clone https://code.google.com/p/android-cmake/

# This should match your app's project.properties
export ANDROID_PLATFORM=android-8
export ANDROID_NDK=/opt/android-ndk

# This file is included in the NDK in r5b and later
export NDK_TC_SCRIPT=$ANDROID_NDK/build/tools/make-standalone-toolchain.sh
# The recommended location for your toolchain is:
export NDK_TC_TARGET=$ABSOLUTE_PATH_TO_REPO/glue/android-toolchain
# And the following command will generate your libalexandria toolchain:
$NDK_TC_SCRIPT --platform=$ANDROID_PLATFORM --install-dir=$NDK_TC_TARGET

Assuming android-cmake and android-toolchain is located in ./glue:

 0) You must specify the following (this creates a link in /opt):

sudo ln -fs $NDK_TC_TARGET /opt/android-toolchain
export ANDROID_STANDALONE_TOOLCHAIN=/opt/android-toolchain

 1) In addition, add the following to your environment:

export NDK_CM_SCRIPT=.../android-cmake/toolchain/android.toolchain.cmake

 2) An alias is also useful, to enable simple builds:

alias android-cmake='cmake -DCMAKE_TOOLCHAIN_FILE=\$NDK_CM_SCRIPT'

NOTES:

The following files lack copyright headers:
==> ./android/AndroidManifest.xml <==
==> ./android/res/layout/activity_lannister.xml <==
==> ./android/res/menu/activity_lannister.xml <==
==> ./android/res/values/strings.xml <==
==> ./android/res/values/styles.xml <==
Can someone fix this a nice way?

See ./tests/android for unit testing framework
