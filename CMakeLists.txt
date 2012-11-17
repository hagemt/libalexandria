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
set(CMAKE_PROJECT_VERSION_STRING "v${CMAKE_PROJECT_VERSION_SO}-${CMAKE_PROJECT_VERSION_SUFFIX}")
set(PROJECT_LOG_TAG "${CMAKE_PROJECT_NAME} ${CMAKE_PROJECT_VERSION_STRING}")

# Force a build type to be declared
set(CMAKE_BUILD_TYPES "(None|Debug|Release|RelWithDebInfo|MinSizeRel)")
if(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
  set(CMAKE_BUILD_TYPE Debug CACHE STRING "${CMAKE_BUILD_TYPES}" FORCE)
endif(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
# Specify some default directories/definitions
set(EXECUTABLE_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/bin")
set(LIBRARY_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/lib")
set(BASE_INCLUDE_PATH "${CMAKE_SOURCE_DIR}/include")
include("${CMAKE_SOURCE_DIR}/user.CMakeCache.txt" OPTIONAL)
include_directories("${BASE_INCLUDE_PATH}/libalexandria")
add_definitions(-DLA_LOG_TAG=${PROJECT_LOG_TAG})

### Configuration

## Global build options
# Fully-supported language bindings
option(BIND_C              "Produce bindings for C"    OFF)
option(BIND_CXX            "Produce bindings for C++"  OFF)
option(BIND_JAVA           "Produce bindings for Java" OFF)
# Formats / Testing / Documentation
option(BUILD_EXAMPLES      "Produce examples"          OFF)
option(BUILD_SHARED_LIBS   "Produce shared libraries"  OFF)
option(BUILD_TESTING       "Produce testing tree"      OFF)
option(BUILD_DOXYGEN       "Produce documentation"     OFF)
if(BUILD_DOXYGEN)
  option(FORMAT_HTML       "Produce manual (HTML)"     ON)
  option(FORMAT_LATEX      "Produce manual (PDF)"      OFF)
  option(FORMAT_MAN        "Produce manual (man.3)"    OFF)
  option(FORMAT_RDF        "Produce manual (RDF)"      OFF)
  option(FORMAT_XML        "Produce manual (XML)"      OFF)
  mark_as_advanced(
    FORMAT_HTML
    FORMAT_LATEX
    FORMAT_MAN
    FORMAT_RDF
    FORMAT_XML
  )
endif(BUILD_DOXYGEN)
# Additional options for Java language bindings
if(BIND_JAVA)
  option(BUILD_JAVADOC     "Produce a limited Javadoc" OFF)
  option(USE_ANDROID       "Produce Android options"   OFF)
endif(BIND_JAVA)
# Additional options for additional language bindings
option(USE_SWIG            "Produce bindings w/ SWIG"  OFF)
if(USE_SWIG)
  option(BIND_PYTHON       "Run SWIG with -python"     ON)
  option(BIND_RUBY         "Run SWIG with -ruby"       ON)
endif(USE_SWIG)

## Main library dependencies
# TODO FUTURE other libraries? like LAPACK?
if(NOT BLAS_INCLUDE_DIRS OR NOT BLAS_LIBRARIES)
  find_package(BLAS REQUIRED)
endif(NOT BLAS_INCLUDE_DIRS OR NOT BLAS_LIBRARIES)
if(NOT BLAS_FOUND)
  message(FATAL_ERROR "Cannot locate BLAS library!")
endif(NOT BLAS_FOUND)
include_directories(${BLAS_INCLUDE_DIRS})
link_directories(${BLAS_LIBRARIES})
# Try to find libcalg using pkg-config
if(NOT CALG_INCLUDE_DIRS OR NOT CALG_LIBRARY_DIRS)
  find_package(PkgConfig REQUIRED)
  pkg_search_module(CALG libcalg-1.0 libcalg>=1)
endif(NOT CALG_INCLUDE_DIRS OR NOT CALG_LIBRARY_DIRS)
if(NOT CALG_FOUND)
  message(FATAL_ERROR "Cannot locate c-algorithms library!")
endif(NOT CALG_FOUND)
include_directories(${CALG_INCLUDE_DIRS})
link_directories(${CALG_LIBRARY_DIRS})
# TODO should we include/link in subdirs?

## Requirements for Java bindings
if(BIND_JAVA)
  set(JAVA_VERSION_MINIMUM 6 CACHE STRING "Minimum Java version")
  mark_as_advanced(JAVA_VERSION_MINIMUM)
  list(APPEND JAVA_COMPONENTS "Development")
  if(BUILD_TESTING)
    list(APPEND JAVA_COMPONENTS "Runtime")
  endif(BUILD_TESTING)
  # Setup to compile/run Java sources/binaries
  if(NOT Java_FOUND)
    message(STATUS "Looking for Java ${JAVA_VERSION_MINIMUM} (${JAVA_COMPONENTS};JNI)")
    find_package(Java 1.${JAVA_VERSION_MINIMUM} COMPONENTS ${JAVA_COMPONENTS})
  endif(NOT Java_FOUND)
  if(NOT Java_Development_FOUND)
    message(FATAL_ERROR "Unable to find Java development kit!")
  endif(NOT Java_Development_FOUND)
  # And run them, if necessary
  if(BUILD_TESTING AND NOT Java_Runtime_FOUND)
    message(FATAL_ERROR "Unable to find Java runtime environment!")
  endif(BUILD_TESTING AND NOT Java_Runtime_FOUND)
  # Setup to use JNI (FIXME correct method?) and init
  find_package(JNI 1.${JAVA_VERSION_MINIMUM})
  if(NOT JNI_FOUND)
    message(FATAL_ERROR "Unable to find JNI! Please locate or disable BIND_JAVA")
  endif(NOT JNI_FOUND)
  include(UseJava)
  # Set some Java arguments for safe building into correct location
  set(CMAKE_JAVA_COMPILE_FLAGS -Xlint:unchecked CACHE STRING "Additional flags passed to javac" FORCE)
  set(CMAKE_JAVA_TARGET_OUTPUT_DIR "${LIBRARY_OUTPUT_PATH}/jar" CACHE PATH "Location to place .jar files")
  mark_as_advanced(CMAKE_JAVA_COMPILE_FLAGS CMAKE_JAVA_TARGET_OUTPUT_DIR)
endif(BIND_JAVA)

## Prepare headers / development tools / documentation
add_subdirectory(include)
add_subdirectory(dev)
add_subdirectory(doc)

### Build

# Glue code (libkolla)
add_subdirectory(glue)
# Fortran code
add_subdirectory(fortran)
# Language bindings
add_subdirectory(bindings)

### Testing

include(CTest)
if(BUILD_TESTING)
  set(CTEST_PROJECT_NAME ${CMAKE_PROJECT_NAME})
  configure_file("CTestCustom.cmake.in" "CTestCustom.cmake" @ONLY)
  add_subdirectory(tests)
endif(BUILD_TESTING)

### Subprojects

# Android activities
if(USE_ANDROID)
  add_subdirectory(android)
endif(USE_ANDROID)

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
if(WIN32)
  if(NOT CYGWIN)
    list(APPEND CPACK_GENERATOR "NSIS") # TODO get working
  endif(NOT CYGWIN)
endif(WIN32)

## Invoke CPack
#include("${CPACK_PROJECT_CONFIG_FILE}")
#include("${CPACK_SOURCE_PROJECT_CONFIG_FILE}")
include(CPack)
