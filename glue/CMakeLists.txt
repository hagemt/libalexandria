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

if(USE_ANDROID)
  # FIXME git submodule update --init on ./glue/android-cmake?
  set(LA_ANDROID_CMAKE_DIR "${CMAKE_SOURCE_DIR}/glue/android-cmake")
  # Try to find the required toolchain script
  find_file(ANDROID_TOOLCHAIN_CMAKE_SCRIPT
    NAMES "android.toolchain.cmake"
    PATHS "${LA_ANDROID_CMAKE_DIR}" PATH_SUFFIXES "toolchain"
    DOC "Location of the CMake script for the Android toolchain")
  if(NOT ANDROID_TOOLCHAIN_CMAKE_SCRIPT)
    message(FATAL_ERROR "Cannot find android.toolchain.cmake in ${LA_ANDROID_CMAKE_DIR}")
  endif(NOT ANDROID_TOOLCHAIN_CMAKE_SCRIPT)
  # Try to find the Android NDK (check in ./android:./.., and any 'android-ndk' subdirs)
  set(LA_ANDROID_DIR "${CMAKE_SOURCE_DIR}/android")
  find_path(ANDROID_NDK NAMES "ndk-build"
    PATHS "${LA_ANDROID_DIR}" "${CMAKE_SOURCE_DIR}/.."
    PATH_SUFFIXES "android-ndk-r8b" "android-ndk-r5b"
    DOC "Location of the Android NDK")
  if(NOT ANDROID_NDK)
    # There exist some scripts within android-cmake to download the NDK
    set(LA_ANDROID_CMAKE_SCRIPTS_DIR "${LA_ANDROID_CMAKE_DIR}/scripts")
    if("${CMAKE_SYSTEM}" MATCHES "Linux")
      set(SCRIPT_FILE "get_ndk_toolchain_linux.sh")
    endif("${CMAKE_SYSTEM}" MATCHES "Linux")
    # TODO convenience for other platforms? Once android-cmake provides it.
    set(LA_ANDROID_NDK_DL_SCRIPT "${LA_ANDROID_CMAKE_SCRIPTS_DIR}/${SCRIPT_FILE}")
    if(NOT LA_ANDROID_NDK_DL_SCRIPT)
      message(FATAL_ERROR "Unable to locate Android NDK directory!")
    endif(NOT LA_ANDROID_NDK_DL_SCRIPT)
    # If we found a download script, offer it to the user
    option(ANDROID_DOWNLOAD_NDK "Fetch a copy of the Android NDK" OFF)
    if(NOT ANDROID_DOWNLOAD_NDK)
      message(FATAL_ERROR "Please locate Android NDK directory, or activate ANDROID_DOWNLOAD_NDK")
    endif(NOT ANDROID_DOWNLOAD_NDK)
    # Fetch the NDK if necessary
    set(LA_ANDROID_NDK_DL_LOG_SUFFIX "download-ndk.log")
    execute_process(
      COMMAND "${LA_ANDROID_NDK_DL_SCRIPT}" "${LA_ANDROID_DIR}"
      OUTPUT_FILE "output-${LA_ANDROID_NDK_DL_LOG_SUFFIX}"
      ERROR_FILE "error-${LA_ANDROID_NDK_DL_LOG_SUFFIX}"
      RESULT_VARIABLE LA_ANDROID_NDK_DL_FAILURE 
      TIMEOUT 600)
    if(LA_ANDROID_NDK_DL_FAILURE)
      message(FATAL_ERROR "Problem downloading NDK. See: ./glue/*-${LA_ANDROID_NDK_DL_LOG_SUFFIX}")
    endif(LA_ANDROID_NDK_DL_FAILURE)
  endif(NOT ANDROID_NDK)
  # Is this a bad idea? Doesn't help users find it.
  mark_as_advanced(ANDROID_NDK ANDROID_TOOLCHAIN_CMAKE_SCRIPT)
endif(USE_ANDROID)

# Acquire all the glue (kolla) code
file(GLOB KOLLA_SOURCES "*.c" "*.cxx")
add_library(kolla STATIC ${KOLLA_SOURCES})
set_target_properties(kolla PROPERTIES COMPILE_FLAGS -fPIC)
# TODO should install? (probably not)

if(USE_SWIG)
  add_subdirectory(swig)
endif(USE_SWIG)
