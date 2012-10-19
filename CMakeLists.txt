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

cmake_minimum_required(VERSION 2.8.6 FATAL_ERROR)
project(libalexandria C CXX Fortran Java)
set(CMAKE_PROJECT_VERSION_MAJOR 0)
set(CMAKE_PROJECT_VERSION_MINOR 1)
set(CMAKE_PROJECT_VERSION_PATCH 2)
set(CMAKE_PROJECT_VERSION_TWEAK 0)
set(CMAKE_PROJECT_VERSION_HIGH
  "${CMAKE_PROJECT_VERSION_MAJOR}.${CMAKE_PROJECT_VERSION_MINOR}")
set(CMAKE_PROJECT_VERSION_LOW
  "${CMAKE_PROJECT_VERSION_PATCH}.${CMAKE_PROJECT_VERSION_TWEAK}")
set(CMAKE_PROJECT_VERSION_SO
  "${CMAKE_PROJECT_VERSION_HIGH}.${CMAKE_PROJECT_VERSION_TWEAK}")
set(CMAKE_PROJECT_VERSION
  "${CMAKE_PROJECT_VERSION_HIGH}.${CMAKE_PROJECT_VERSION_LOW}")
set(CMAKE_PROJECT_VERSION_SUFFIX "dev" CACHE STRING "A custom build label")
mark_as_advanced(CMAKE_PROJECT_VERSION_SUFFIX)
set(PROJECT_LOG_TAG "${CMAKE_PROJECT_NAME}${CMAKE_PROJECT_VERSION_SO}-${CMAKE_PROJECT_VERSION_SUFFIX}")

# Force a build type to be declared
set(CMAKE_BUILD_TYPES "(None|Debug|Release|RelWithDebInfo|MinSizeRel)")
if(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
  set(CMAKE_BUILD_TYPE Debug CACHE STRING "${CMAKE_BUILD_TYPES}" FORCE)
endif(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
# Specify some default directories
set(EXECUTABLE_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/bin")
set(LIBRARY_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/lib")
set(BASE_INCLUDE_PATH "${CMAKE_SOURCE_DIR}/include")
include("${CMAKE_SOURCE_DIR}/user.CMakeCache.txt" OPTIONAL)
include_directories("${BASE_INCLUDE_PATH}/libalexandria")
add_definitions(-DLOG_TAG=${PROJECT_LOG_TAG})

### Configuration

## Build options
option(BIND_C            "Produce bindings for C"    OFF)
option(BIND_CXX          "Produce bindings for C++"  OFF)
option(BIND_JAVA         "Produce bindings for Java" OFF)
option(BUILD_SHARED_LIBS "Produce shared libraries"  OFF)
option(BUILD_TESTING     "Produce testing tree"      OFF)

if(BIND_JAVA)
option(USE_ANDROID       "Produce Android options"   OFF)
option(BUILD_JAVADOC     "Produce a limited Javadoc" OFF)
endif(BIND_JAVA)

option(USE_SWIG          "Produce bindings w/ SWIG"  OFF)

## Development tools
add_subdirectory(dev)

## Requirements for Java bindings
if(BIND_JAVA)
  # Set some arguments for Java for safe building
  set(CMAKE_JAVA_TARGET_OUTPUT_DIR "${EXECUTABLE_OUTPUT_PATH}/jar")
  set(CMAKE_JAVA_INCLUDE_PATH "$ENV{CLASSPATH}")
  set(CMAKE_JAVA_COMPILE_FLAGS -Xlint:unchecked CACHE STRING "Additional flags passed to javac" FORCE)
  mark_as_advanced(CMAKE_JAVA_COMPILE_FLAGS)
endif(BIND_JAVA)

## Requirements for Android builds (NDK)
if(USE_ANDROID)
  if(NOT ANDROID)
    message(FATAL_ERROR "Please configure your environment for USE_ANDROID or disable it")
  endif(NOT ANDROID)
  set(ANDROID_NDK_PATH     "$ENV{ANDROID_NDK}"      CACHE PATH   "Path to Android NDK")
  set(ANDROID_SDK_VERSION  "$ENV{ANDROID_PLATFORM}" CACHE STRING "Version of Android SDK")
  set(ANDROID_BUILD_ARCH   "$ENV{ANDROID_ARCH}"     CACHE STRING "Target architecture for Android build")
  mark_as_advanced(ANDROID_NDK_PATH ANDROID_SDK_VERSION ANDROID_BUILD_ARCH)
  set(CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}/${ANDROID_BUILD_ARCH}")
  include_directories("${ANDROID_NDK_PATH}/platforms/${ANDROID_SDK_VERSION}/arch-${ANDROID_BUILD_ARCH}/usr/include")
  add_definitions(-DUSE_ANDROID)
endif(USE_ANDROID)

# TODO FUTURE other libraries? like LAPACK?
find_package(BLAS REQUIRED)
if(NOT BLAS_FOUND)
  message(FATAL_ERROR "Cannot locate BLAS library!")
endif(NOT BLAS_FOUND)
include_directories(${BLAS_INCLUDE_DIRS})
link_directories(${BLAS_LIBRARIES})
# Try to find libcalg using pkg-config
find_package(PkgConfig REQUIRED)
pkg_search_module(CALG libcalg-1.0 libcalg>=1)
if(NOT CALG_FOUND)
  message(FATAL_ERROR "Cannot locate calgorithms library!")
endif(NOT CALG_FOUND)
include_directories(${CALG_INCLUDE_DIRS})
link_directories(${CALG_LIBRARY_DIRS})
# TODO should we include/link in subdirs?

### Build

## Main components

# Process headers
add_subdirectory(include)
# Glue code
add_subdirectory(glue)
# Fortran code
add_subdirectory(fortran)

## Optional components

# Language bindings
add_subdirectory(bindings)
# Android options
if(USE_ANDROID)
  add_subdirectory(android)
endif(USE_ANDROID)

### Testing

include(CTest)
if(BUILD_TESTING)
  set(CTEST_PROJECT_NAME ${CMAKE_PROJECT_NAME})
  add_subdirectory(tests)
endif(BUILD_TESTING)

### Packaging

## TODO how to get `make package` and `cpack` both working?
#set(FROM_CMAKE_BUILD ON CACHE INTERNAL "Tag for `make package`" FORCE)
## Initialize a global configuration
#set(CPACK_PROJECT_CONFIG_FILE "${CMAKE_SOURCE_DIR}/CPackConfig.cmake")
## Initialize source packaging (for future use)
#set(CPACK_SOURCE_PROJECT_CONFIG_FILE "${CMAKE_SOURCE_DIR}/CPackSourceConfig.cmake")
## Set up generators
set(CPACK_GENERATOR "STGZ;TGZ;TBZ2;TZ") # FIXME ZIP is broken?
if(UNIX)
  list(APPEND CPACK_GENERATOR "DEB") # TODO get working
  list(APPEND CPACK_GENERATOR "RPM") # TODO get working
endif(UNIX)
if(WIN32 AND NOT UNIX)
  list(APPEND CPACK_GENERATOR "NSIS") # TODO get working for non-Cygwin?
endif(WIN32 AND NOT UNIX)

## Invoke CPack
#include("${CPACK_PROJECT_CONFIG_FILE}")
#include("${CPACK_SOURCE_PROJECT_CONFIG_FILE}")
include(CPack)
