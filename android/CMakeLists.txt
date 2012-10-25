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

# We'll need these to configure the toolchain
if(NOT ANDROID_NDK OR NOT ANDROID_TOOLCHAIN_CMAKE_SCRIPT)
  message(FATAL_ERROR "Unable to configure Android with CMake")
endif(NOT ANDROID_NDK OR NOT ANDROID_TOOLCHAIN_CMAKE_SCRIPT)

# Android SDK settings
set(ANDROID_API_LEVEL "8" CACHE STRING "Target Android API")

# Provide a default toolchain path
if(NOT ANDROID_NDK_TOOLCHAIN_ROOT)
  set(ANDROID_NDK_TOOLCHAIN_ROOT "/opt/android-toolchain")
endif(NOT ANDROID_NDK_TOOLCHAIN_ROOT)
# Find a valid toolchain path if one does not already exist
if(NOT EXISTS "${ANDROID_NDK_TOOLCHAIN_ROOT}")
  set(TOOLCHAIN_SCRIPT "./build/tools/make-standalone-toolchain.sh")
  set(ANDROID_NDK_TOOLCHAIN_ROOT "${CMAKE_SOURCE_DIR}/glue/android-toolchain")
  execute_process(
    COMMAND ${TOOLCHAIN_SCRIPT} --platform=android-${ANDROID_API_LEVEL} --install-dir=${ANDROID_NDK_TOOLCHAIN_ROOT}
    WORKING_DIRECTORY "${ANDROID_NDK}" TIMEOUT 60 OUTPUT_QUIET ERROR_FILE "make-toolchain.log" ERROR_VARIABLE FAILURE)
  if(FAILURE)
    set(ANDROID_NDK_TOOLCHAIN_ROOT)
    message(FATAL_ERROR "Problem generating Android toolchain. See make-toolchain.log")
  endif(FAILURE)
endif(NOT EXISTS "${ANDROID_NDK_TOOLCHAIN_ROOT}")
# Cache the value for the user
set(ANDROID_NDK_TOOLCHAIN_ROOT "${ANDROID_NDK_TOOLCHAIN_ROOT}" CACHE PATH "The Android NDK toolchain to use")
mark_as_advanced(ANDROID_NDK_TOOLCHAIN_ROOT)

# Where the magic happens
include(${ANDROID_TOOLCHAIN_CMAKE_SCRIPT})
if(NOT ANDROID)
  message(FATAL_ERROR "Cannot enable USE_ANDROID; see README files")
endif(NOT ANDROID)
add_subdirectory(jni)
