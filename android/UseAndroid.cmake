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

### Step 1: find the ANDROID_TOOLCHAIN_CMAKE_SCRIPT

## !!! Important: this directory is a submodule:
set(ANDROID_CMAKE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/android-cmake")
# FIXME do git submodule update --init on android-cmake?
# If this were a submodule with recursive fetch, it's automatic!
if(NOT IS_DIRECTORY "${ANDROID_CMAKE_DIR}")
  message(WARNING "Missing expected directory: ${ANDROID_CMAKE_DIR}")
  message(FATAL_ERROR "Hint: git submodule update --init android-cmake")
endif(NOT IS_DIRECTORY "${ANDROID_CMAKE_DIR}")

## Try to find the required toolchain script in there (advanced option)
find_file(ANDROID_TOOLCHAIN_CMAKE_SCRIPT
  NAMES "android.toolchain.cmake"
  PATHS "${ANDROID_CMAKE_DIR}" PATH_SUFFIXES "toolchain"
  DOC "Location of the CMake script for the Android toolchain")

## And make sure we were successful:
if(NOT EXISTS "${ANDROID_TOOLCHAIN_CMAKE_SCRIPT}")
  message(FATAL_ERROR "Cannot find android.toolchain.cmake in ${ANDROID_CMAKE_DIR}")
endif(NOT EXISTS "${ANDROID_TOOLCHAIN_CMAKE_SCRIPT}")
mark_as_advanced(ANDROID_TOOLCHAIN_CMAKE_SCRIPT)

### Step 2: find the ANDROID_NDK

## Check in any 'android-ndk' subdir here or up one/two level(s)
find_path(ANDROID_NDK NAMES "ndk-build" "ndk-gdb" "ndk-stack"
  PATHS "${CMAKE_CURRENT_SOURCE_DIR}" "${CMAKE_CURRENT_SOURCE_DIR}/.." "${CMAKE_CURRENT_SOURCE_DIR}/../.."
  PATH_SUFFIXES "android-ndk-r8b" "android-ndk-r7b" "android-ndk-r6b" "android-ndk-r5b"
  DOC "Location of the Android NDK (release build)")

## And make sure we were successful:
if(NOT IS_DIRECTORY "${ANDROID_NDK}")
  # Okay, well, so that didn't work.
  set(ANDROID_CMAKE_SCRIPT_DIR "${ANDROID_CMAKE_DIR}/scripts")
  # There exist some scripts within android-cmake to download the NDK
  set(ANDROID_CMAKE_SCRIPT_FILE "get_ndk_toolchain.sh")
  if("${CMAKE_SYSTEM}" MATCHES "Linux")
    set(ANDROID_CMAKE_SCRIPT_FILE "get_ndk_toolchain_linux.sh")
  endif("${CMAKE_SYSTEM}" MATCHES "Linux")
  # TODO convenience for other platforms? Once android-cmake provides it.
  set(ANDROID_CMAKE_SCRIPT "${LA_ANDROID_CMAKE_SCRIPT_DIR}/${ANDROID_CMAKE_SCRIPT_FILE}")
  # No script means we need to rely on the user...
  if(NOT EXISTS "${ANDROID_CMAKE_SCRIPT}")
    message(FATAL_ERROR "Unable to locate Android NDK directory!")
  endif(NOT EXISTS "${ANDROID_CMAKE_SCRIPT}")
  # If we found a download script, offer it up as an option
  option(ANDROID_DOWNLOAD_NDK "Fetch a copy of the Android NDK using CMake" OFF)
  if(NOT ANDROID_DOWNLOAD_NDK)
    message(FATAL_ERROR "Please locate Android NDK directory, or activate ANDROID_DOWNLOAD_NDK")
  endif(NOT ANDROID_DOWNLOAD_NDK)
  # Fetch the NDK, by now it's necessary and approved
  set(LOG_SUFFIX "download-ndk.log")
  execute_process(
    COMMAND "${ANDROID_CMAKE_SCRIPT}" "${CMAKE_CURRENT_SOURCE_DIR}"
    OUTPUT_FILE "${CMAKE_CURRENT_SOURCE_DIR}/output-${LOG_SUFFIX}"
    ERROR_FILE "${CMAKE_CURRENT_SOURCE_DIR}/error-${LOG_SUFFIX}"
    RESULT_VARIABLE ANDROID_CMAKE_SCRIPT_RESULT
    TIMEOUT 600)
  # Catch failures
  if(ANDROID_CMAKE_SCRIPT_RESULT)
    message(FATAL_ERROR "Problem downloading NDK. See logs: ${CMAKE_CURRENT_SOURCE_DIR}/*-${LOG_SUFFIX}")
  endif(ANDROID_CMAKE_SCRIPT_RESULT)
endif(NOT IS_DIRECTORY "${ANDROID_NDK}")
mark_as_advanced(ANDROID_NDK)

### Step 3: find a ANDROID_NDK_TOOLCHAIN_ROOT

## Provide a default toolchain path (from NDK doc)
if(NOT ANDROID_NDK_TOOLCHAIN_ROOT)
  set(ANDROID_NDK_TOOLCHAIN_ROOT "/opt/android-toolchain" CACHE PATH "The default Android NDK toolchain location")
endif(NOT ANDROID_NDK_TOOLCHAIN_ROOT)

## Generate a toolchain if one does not already exist
if(NOT IS_DIRECTORY "${ANDROID_NDK_TOOLCHAIN_ROOT}")
  set(MAKE_TOOLCHAIN_SCRIPT "./build/tools/make-standalone-toolchain.sh")
  set(LOG_SUFFIX "make-standalone-toolchain.log")
  set(ANDROID_NDK_TOOLCHAIN_ROOT "${CMAKE_CURRENT_SOURCE_DIR}/android-toolchain")
  message(STATUS "Generating Android toolchain in: ${ANDROID_NDK_TOOLCHAIN_ROOT}")
  # FIXME how to specify architectures? unnecessary?
  execute_process(
    COMMAND ${MAKE_TOOLCHAIN_SCRIPT}
      --platform="android-${ANDROID_API_LEVEL}"
      --install-dir="${ANDROID_NDK_TOOLCHAIN_ROOT}"
    WORKING_DIRECTORY "${ANDROID_NDK}"
    OUTPUT_FILE "${CMAKE_CURRENT_SOURCE_DIR}/output-${LOG_SUFFIX}"
    ERROR_FILE "${CMAKE_CURRENT_SOURCE_DIR}/error-${LOG_SUFFIX}"
    RESULT_VARIABLE MAKE_TOOLCHAIN_SCRIPT_RESULT
    TIMEOUT 60)
  # Catch failures
  if(MAKE_TOOLCHAIN_SCRIPT_RESULT)
    set(ANDROID_NDK_TOOLCHAIN_ROOT)
    message(FATAL_ERROR "Problem generating NDK toolchain. See logs: ${CMAKE_CURRENT_SOURCE_DIR}/*-${LOG_SUFFIX}")
  endif(MAKE_TOOLCHAIN_SCRIPT_RESULT)
endif(NOT IS_DIRECTORY "${ANDROID_NDK_TOOLCHAIN_ROOT}")

## Cache the value for the user (it either exists, or we made it)
set(ANDROID_NDK_TOOLCHAIN_ROOT "${ANDROID_NDK_TOOLCHAIN_ROOT}" CACHE PATH "The Android NDK toolchain to use" FORCE)
mark_as_advanced(ANDROID_NDK_TOOLCHAIN_ROOT)

### Step 4: configure every script with these settings

# The following were set above:
#  ANDROID_TOOLCHAIN_CMAKE_SCRIPT ".../android-cmake/toolchain/android.toolchain.cmake" (submodule)
#  ANDROID_NDK                    ".../android-ndk-r8b" (or up to two levels up)
#  ANDROID_NDK_TOOLCHAIN_ROOT     ".../android-toolchain" (generated, or found elsewhere)

# They need to be configured in every app script
file(GLOB APP_SCRIPTS "*.cmake.in")
foreach(APP_SCRIPT "${APP_SCRIPTS}")
  
endforeach(APP_SCRIPT)
