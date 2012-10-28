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

project(Lannister Java)
cmake_minimum_required(VERSION 2.8.6 FATAL_ERROR)

## Android SDK settings

# Target platforms to support
set(ANDROID_API_LEVEL "8" CACHE STRING "Target Android API")
option(ANDROID_TARGET_ARM  "Target ARM devices"  ON )
option(ANDROID_TARGET_MIPS "Target MIPS devices" OFF)
option(ANDROID_TARGET_X86  "Target x86 devices"  OFF)

# Cache a record (list) of toolchains that will be required
if(ANDROID_TARGET_ARM)
  list(APPEND LA_ANDROID_TARGETS "arm")
endif(ANDROID_TARGET_ARM)
if(ANDROID_TARGET_MIPS)
  list(APPEND LA_ANDROID_TARGETS "mips")
endif(ANDROID_TARGET_MIPS)
if(ANDROID_TARGET_X86)
  list(APPEND LA_ANDROID_TARGETS "x86")
endif(ANDROID_TARGET_X86)
set(ANDROID_TARGETS "${LA_ANDROID_TARGETS}" CACHE STRING "Selected Android architectures" FORCE)
if(NOT ANDROID_TARGETS)
  message(WARNING "You have disabled all supported Android architectures...")
endif(NOT ANDROID_TARGETS)
mark_as_advanced(ANDROID_TARGETS)

## Toolchain configuration (assumes Android NDK was configured)

# We'll need these to configure the toolchain
if(NOT IS_DIRECTORY ANDROID_NDK)
  message(FATAL_ERROR "ANDROID_NDK must point to the correct directory.")
endif(NOT IS_DIRECTORY ANDROID_NDK)

# Provide a default toolchain path (from NDK doc)
if(NOT ANDROID_NDK_TOOLCHAIN_ROOT)
  set(ANDROID_NDK_TOOLCHAIN_ROOT "/opt/android-toolchain")
endif(NOT ANDROID_NDK_TOOLCHAIN_ROOT)

# Generate a toolchain if one does not already exist
if(NOT IS_DIRECTORY "${ANDROID_NDK_TOOLCHAIN_ROOT}")
  set(MAKE_TOOLCHAIN_SCRIPT "./build/tools/make-standalone-toolchain.sh")
  set(MAKE_TOOLCHAIN_SCRIPT_LOG_SUFFIX "make-standalone-toolchain.log")
  set(ANDROID_NDK_TOOLCHAIN_ROOT "${CMAKE_CURRENT_SOURCE_DIR}/android-toolchain")
  message(STATUS "Generating Android toolchain in ${ANDROID_NDK_TOOLCHAIN_ROOT}...")
  # FIXME how to specify architectures? unnecessary?
  execute_process(
    COMMAND ${MAKE_TOOLCHAIN_SCRIPT}
      --platform="android-${ANDROID_API_LEVEL}"
      --install-dir="${ANDROID_NDK_TOOLCHAIN_ROOT}"
    WORKING_DIRECTORY "${ANDROID_NDK}"
    OUTPUT_FILE "output-${TOOLCHAIN_SCRIPT_LOG_SUFFIX}"
    ERROR_FILE "error-${TOOLCHAIN_SCRIPT_LOG_SUFFIX}"
    RESULT_VARIABLE MAKE_TOOLCHAIN_RESULT
    TIMEOUT 60)
  if(MAKE_TOOLCHAIN_RESULT)
    set(ANDROID_NDK_TOOLCHAIN_ROOT)
    message(FATAL_ERROR "Problem generating Android toolchain. See: ./android/*-${TOOLCHAIN_SCRIPT_LOG_SUFFIX}")
  endif(MAKE_TOOLCHAIN_RESULT)
endif(NOT IS_DIRECTORY "${ANDROID_NDK_TOOLCHAIN_ROOT}")

# Cache the value for the user
set(ANDROID_NDK_TOOLCHAIN_ROOT "${ANDROID_NDK_TOOLCHAIN_ROOT}" CACHE PATH "The Android NDK toolchain to use" FORCE)
mark_as_advanced(ANDROID_NDK_TOOLCHAIN_ROOT)

## Build apps as necessary

# Provided we didn't come from any parent project (UseApps messes with the compilers)
if(NOT USE_ANDROID)
  include("UseAndroidApps.cmake")
  # Add the Lannister project (this directory)
  add_app("${CMAKE_PROJECT_NAME}" "${CMAKE_CURRENT_SOURCE_DIR}")
endif(NOT USE_ANDROID)
