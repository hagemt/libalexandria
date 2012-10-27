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

### Provides a robust mechanism to build Android apps as subprojects
### See http://en.wikipedia.org/wiki/Jo-ha-kyu
macro(add_app NAME LOCATION)

# jo [beginning] (prelude script, run before project definition)
include(${LOCATION}/${NAME}.jo.cmake OPTIONAL)
set(CMAKE_ANDROID_APP_NAME "Android_${NAME}")
set(CMAKE_ANDROID_APP_ARCH "${NAME}/arm")
project(${CMAKE_ANDROID_APP_NAME} C CXX Java)

# Specify some Android options
set(ANDROID_APP_${NAME}_NAME "${CMAKE_ANDROID_APP_NAME}" CACHE STRING "The name of this app" FORCE)
set(ANDROID_APP_${NAME}_ARCH "${CMAKE_ANDROID_APP_ARCH}" CACHE STRING "The arch of this app" FORCE)
mark_as_advanced(ANDROID_APP_${APP_NAME}_NAME ANDROID_APP_${APP_NAME}_ARCH)
set(ANDROID_APP_NAME "${ANDROID_APP_${NAME}_NAME")
set(ANDROID_APP_ARCH "${ANDROID_APP_${NAME}_ARCH")
message("Adding ${ANDROID_APP_NAME} in ${LOCATION} (${ARGN})")

# Force a build type to be declared (default to Debug)
set(CMAKE_BUILD_TYPES "(None|Debug|Release|RelWithDebInfo|MinSizeRel)")
if(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
  set(CMAKE_BUILD_TYPE Debug CACHE STRING "${CMAKE_BUILD_TYPES}" FORCE)
endif(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
# Specify some default directories/definitions (architecture specific)
set(EXECUTABLE_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/${ANDROID_APP_ARCH}/bin")
set(LIBRARY_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/${ANDROID_APP_ARCH}/lib")
set(CMAKE_INSTALL_DIR "${CMAKE_INSTALL_DIR}/${ANDROID_APP_ARCH}")

## Where the magic happens
#include(${ANDROID_TOOLCHAIN_CMAKE_SCRIPT})
#if(NOT ANDROID)
#  message(FATAL_ERROR "Cannot enable USE_ANDROID; see README files")
#endif(NOT ANDROID)

# ha [break] (quickening script, run before res/src/jni)
include(${LOCATION}/${APP_NAME}.ha.cmake OPTIONAL)

# Resource files
add_subdirectory(${LOCATION}/res)
# Java source files
add_subdirectory(${LOCATION}/src)
# From the Android toolchain, we'll need headers and libraries
include_directories(BEFORE SYSTEM "${ANDROID_NDK_TOOLCHAIN_ROOT}/include")
link_directories("${ANDROID_NDK_TOOLCHAIN_ROOT}/lib")
# Native source files
add_subdirectory(${LOCATION}/jni)

# kyu [Rapid] (climax script, run after res/src/jni)
include(${LOCATION}/${APP_NAME}.kyu.cmake OPTIONAL)

endmacro(add_app)
