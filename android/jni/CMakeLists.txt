#    This file is part of libalexandria.
#
#    libalexandria is free software: you can redistribute it and/or modify
#    it under the terms of the GNU Lesser General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    libalexandria is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU Lesser General Public License for more details.
#
#    You should have received a copy of the GNU Lesser General Public License
#    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.

# Will set up the requisite variables
if(NOT ANDROID_NDK)
  message(FATAL_ERROR "Cannot find Android NDK! Please locate or disable USE_ANDROID")
endif(NOT ANDROID_NDK)

# Try to find the android-toolchain for each platform
foreach(TARGET ${ANDROID_TARGETS})
  message(STATUS "Enabled Android build for ${TARGET} architecture!")
  # TODO match up the toolchains and configure individual Android.mk?
endforeach(TARGET)

### TODO IS ALL OF THIS THE WRONG DIRECTION?
#
## Add any static libs here (they'll need to be built for arch)
#list(APPEND NATIVE_LIBRARY_DEPS "cpufeatures")
## Add any libraries to link here (make sure they're built for arch)
#list(APPEND NATIVE_LIBRARY_LIBS "android")
#list(APPEND NATIVE_LIBRARY_LIBS "log")
#
## Should set the necessary variables in our scope
#if(NOT COMMAND configure_native_target)
#  include("${CMAKE_SOURCE_DIR}/glue/ConfigureNativeTarget.cmake")
#endif(NOT COMMAND configure_native_target)
#configure_native_target(jalexandria
#  "-Wall -Wextra"
#  "${BASE_INCLUDE_PATH}"
#  "${NATIVE_LIBRARY_DEPS}"
#  "${NATIVE_LIBRARY_LIBS}"
#)
#
## Configure the NDK build scripts using these options
#configure_file(Android.mk.in "${CMAKE_SOURCE_DIR}/android/jni/Android.mk" @ONLY)
#configure_file(Application.mk.in "${CMAKE_SOURCE_DIR}/android/jni/Application.mk" @ONLY)
