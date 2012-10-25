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
  set(LA_ANDROID_CMAKE_DIR "${CMAKE_SOURCE_DIR}/glue/android-cmake")
  set(LA_ANDROID_CMAKE_SCRIPT "android.toolchain.cmake")
  # Try to find the required toolchain script ^
  find_file(ANDROID_TOOLCHAIN_CMAKE_SCRIPT
    NAMES ${LA_ANDROID_CMAKE_SCRIPT}
    PATHS "${LA_ANDROID_CMAKE_DIR}" PATH_SUFFIXES "toolchain"
    DOC "Location of the CMake script for the Android toolchain")
  if(NOT ANDROID_TOOLCHAIN_CMAKE_SCRIPT)
    if(NOT IS_DIRECTORY "${ANDROID_CMAKE_DIR}")
      message(FATAL_ERROR "Please update the git submodule in '${ANDROID_CMAKE_DIR}'!")
    endif(NOT IS_DIRECTORY "${ANDROID_CMAKE_DIR}")
    message(FATAL_ERROR "Unable to locate ${LA_ANDROID_CMAKE_SCRIPT}!")
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
    if(LINUX)
      set(SCRIPT_FILE "get_ndk_toolchain_linux.sh")
    endif(LINUX)
    # TODO convenience for other platforms? Once android-cmake provides it.
    set(LA_ANDROID_NDK_DOWNLOAD_SCRIPT "${LA_ANDROID_CMAKE_SCRIPTS_DIR}/${SCRIPT_FILE}")
    if(NOT LA_ANDROID_NDK_DOWNLOAD_SCRIPT)
      message(FATAL_ERROR "Unable to locate Android NDK directory!")
    endif(NOT LA_ANDROID_NDK_DOWNLOAD_SCRIPT)
    # If we found a download script, offer it to the user
    option(ANDROID_DOWNLOAD_NDK "Fetch a copy of the Android NDK" OFF)
    if(NOT ANDROID_DOWNLOAD_NDK)
      message(FATAL_ERROR "Please locate Android NDK, or active ANDROID_DOWNLOAD_NDK!")
    endif(NOT ANDROID_DOWNLOAD_NDK)
    # Fetch the NDK if necessary
    execute_process(COMMAND "${LA_ANDROID_NDK_DOWNLOAD_SCRIPT}" "${LA_ANDROID_CMAKE_DIR}")
  endif(NOT ANDROID_NDK)
  # Cache a record of each toolchain that will be required
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
  mark_as_advanced(ANDROID_NDK ANDROID_TARGETS ANDROID_TOOLCHAIN_CMAKE_SCRIPT)
endif(USE_ANDROID)

# Acquire all the glue (kolla) code
file(GLOB KOLLA_SOURCES "*.c" "*.cxx")
add_library(kolla STATIC ${KOLLA_SOURCES})
set_target_properties(kolla PROPERTIES COMPILE_FLAGS -fPIC)
# TODO should install? (probably not)

if(USE_SWIG)
  add_subdirectory(swig)
endif(USE_SWIG)
