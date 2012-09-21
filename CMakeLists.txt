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

# Force a build type to be declared
set(CMAKE_BUILD_TYPES "(None|Debug|Release|RelWithDebInfo|MinSizeRel)")
if(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
  set(CMAKE_BUILD_TYPE Debug CACHE STRING "${CMAKE_BUILD_TYPES}" FORCE)
endif(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
# Specify some default directories
set(EXECUTABLE_OUTPUT_PATH "${CMAKE_BINARY_DIR}/bin")
set(LIBRARY_OUTPUT_PATH "${CMAKE_BINARY_DIR}/lib")
include_directories("${CMAKE_SOURCE_DIR}/include")

### Configuration

option(BIND_JAVA "Produce bindings for Java" ON)

option(BUILD_SHARED_LIBS "Produce shared libraries where possible" ON)

## Requirements

if(BIND_JAVA)
  find_package(JNI)
  if(JNI_FOUND)
    message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
  else(JNI_FOUND)
    message(FATAL "Could not find JNI.")
  endif(JNI_FOUND)
endif(BIND_JAVA)

### Build

if(BIND_JAVA)
  add_subdirectory(bindings/java)
endif(BIND_JAVA)

add_subdirectory(fortran)

if(BUILD_TESTING)
  # A simple proof-of-concept
  add_executable(simple_poc bindings/java/laF_print.c)
  target_link_libraries(simple_poc libalexandriaF)
endif(BUILD_TESTING)

if(BIND_JAVA)
  include_directories(${JNI_INCLUDE_DIRS})
  add_library(libalexandria bindings/java/laF_print.c)
  target_link_libraries(libalexandria libalexandriaF ${JNI_LIBRARIES})
endif(BIND_JAVA)

## TODO symbolic linking of library components

### Testing

include(CTest)
if(BUILD_TESTING)
  set(CTEST_PROJECT_NAME ${CMAKE_PROJECT_NAME})
  add_test(simple_poc "${EXECUTABLE_OUTPUT_PATH}/simple_poc")
  set_tests_properties(simple_poc PROPERTIES
    PASS_REGULAR_EXPRESSION "Hello, World!")
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