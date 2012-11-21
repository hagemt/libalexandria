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

# A sample app using libalexandria
set(ANDROID_POC_APP "Lannister")
project(${ANDROID_POC_APP} Java)
cmake_minimum_required(VERSION 2.8.6 FATAL_ERROR)

## Android SDK settings for Lannister

# Configure a baseline API level
set(ANDROID_API_LEVEL "8" CACHE STRING "Target Android API")
# Configure platforms to support
option(ANDROID_TARGET_ARM  "Target ARM devices"  ON )
option(ANDROID_TARGET_MIPS "Target MIPS devices" OFF)
option(ANDROID_TARGET_X86  "Target x86 devices"  OFF)
# TODO actually build for each option above... (not just arm)
#if(ANDROID_TARGET_ARM)
#  list(APPEND LA_ANDROID_TARGETS "arm")
#endif(ANDROID_TARGET_ARM)
#if(ANDROID_TARGET_MIPS)
#  list(APPEND LA_ANDROID_TARGETS "mips")
#endif(ANDROID_TARGET_MIPS)
#if(ANDROID_TARGET_X86)
#  list(APPEND LA_ANDROID_TARGETS "x86")
#endif(ANDROID_TARGET_X86)
set(ANDROID_TARGETS "arm" CACHE STRING "Selected Android architectures" FORCE)
mark_as_advanced(ANDROID_TARGETS FORCE)
# Warn if all architectures are disabled
if(NOT ANDROID_TARGETS)
  message(WARNING "You have disabled all supported Android architectures...")
  # TODO force ARM?
endif(NOT ANDROID_TARGETS)

## Android NDK settings for Lannister

# Mark that this project uses Android
include("UseAndroid.cmake")
# add_app severely messes with the project (replaces compilers)
if(NOT USE_ANDROID)
  # Add this subproject as an app, if it's all we're doing
  add_app("${ANDROID_POC_APP}" "${CMAKE_CURRENT_SOURCE_DIR}")
endif(NOT USE_ANDROID)
