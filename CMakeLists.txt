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
set(CMAKE_PROJECT_VERSION_PATCH 1)
set(CMAKE_PROJECT_VERSION_TWEAK 0)
set(CMAKE_PROJECT_VERSION_HIGH
  "${CMAKE_PROJECT_VERSION_MAJOR}.${CMAKE_PROJECT_VERSION_MINOR}")
set(CMAKE_PROJECT_VERSION_LOW
  "${CMAKE_PROJECT_VERSION_PATCH}.${CMAKE_PROJECT_VERSION_TWEAK}")
set(CMAKE_PROJECT_VERSION_SO
  "${CMAKE_PROJECT_VERSION_HIGH}.${CMAKE_PROJECT_VERSION_TWEAK}")
set(CMAKE_PROJECT_VERSION
  "${CMAKE_PROJECT_VERSION_HIGH}.${CMAKE_PROJECT_VERSION_LOW}")

# Force a build type to be declared
set(CMAKE_BUILD_TYPES "(None|Debug|Release|RelWithDebInfo|MinSizeRel)")
if(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
  set(CMAKE_BUILD_TYPE Debug CACHE STRING "${CMAKE_BUILD_TYPES}" FORCE)
endif(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
# Specify some default directories
set(EXECUTABLE_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/bin")
set(LIBRARY_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/lib")
set(BASE_INCLUDE_PATH "${CMAKE_SOURCE_DIR}/include")
include("${CMAKE_SOURCE_DIR}/settings.user.txt" OPTIONAL)
include_directories("${BASE_INCLUDE_PATH}/libalexandria")

### Configuration

## Build options
option(BIND_C            "Produce bindings for C"    OFF)
option(BIND_CXX          "Produce bindings for C++"  OFF)
option(BIND_JAVA         "Produce bindings for Java" OFF)
option(BUILD_SHARED_LIBS "Produce shared libraries"  OFF)
option(BUILD_TESTING     "Produce testing tree"      OFF)

if(BIND_JAVA)
option(BUILD_JAVADOC     "Produce Javadoc"           OFF)
endif(BIND_JAVA)

## Development tools
add_subdirectory(dev)

## Requirements

if(BIND_JAVA)
  find_package(JNI)
  if(NOT JNI_FOUND)
    message(FATAL_ERROR "Unable to find JNI. Please locate or disable BIND_JAVA")
  endif(NOT JNI_FOUND)
  # Set some arguments for Java for safe building
  set(CMAKE_JAVA_TARGET_OUTPUT_DIR "${EXECUTABLE_OUTPUT_PATH}/jar")
  set(CMAKE_JAVA_COMPILE_FLAGS -Xlint:unchecked)
endif(BIND_JAVA)

# TODO FUTURE other libraries? like BLAS
if(BIND_JAVA)
  find_package(PkgConfig REQUIRED)
  pkg_check_modules(CALG libcalg-1.0)
  include_directories(${CALG_INCLUDE_DIRS})
  link_directories(${CALG_LIBRARY_DIRS})
endif(BIND_JAVA)

### Build

## Main components

# Process headers
add_subdirectory(include)
# Fortran code
add_subdirectory(fortran)
# Language bindings
add_subdirectory(bindings)

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
