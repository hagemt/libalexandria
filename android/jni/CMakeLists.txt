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
add_subdirectory(base)
if(NOT NATIVE_LIBRARY_SONAME)
  message(FATAL_ERROR "Unable to configure with NDK. Please address this or disable USE_ANDROID")
endif(NOT NATIVE_LIBRARY_SONAME)

# Note: add any static libs here (they'll need to be built for arch)
set(NATIVE_LIBRARY_DEPENDS "cpufeatures") # TODO e.g. native glue

# Note: we don't need to list math, pthread, or std support libraries
set(NATIVE_LIBRARY_LDLIBS "-landroid -llog")

# FIXME is it a mistake to export these generally?
list(APPEND NATIVE_LIBRARY_EXPORTS "android")
list(APPEND NATIVE_LIBRARY_EXPORTS "log")

# Configure the NDK build scripts using these options
configure_file(Android.mk.in "${CMAKE_SOURCE_DIR}/android/jni/Android.mk" @ONLY)
configure_file(Application.mk.in "${CMAKE_SOURCE_DIR}/android/jni/Application.mk" @ONLY)
