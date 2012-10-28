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

# TODO anything that should run, global configuration?

##########################################################################

### Adds an important configuration function for the NDK
### See how it is used below in add_app
macro(configure_ndk TARGET DEPS LIBS)
message(STATUS "Configure w/ NDK: '${TARGET}' '${DEPS}' '${LIBS}' '${ARGN}'")
if(NOT IS_DIRECTORY "${ANDROID_NDK_TOOLCHAIN_ROOT}")
  message(FATAL_ERROR "Cannot locate Android toolchain for ${TARGET}!")
endif(NOT IS_DIRECTORY "${ANDROID_NDK_TOOLCHAIN_ROOT}")

# First set the SONAME target
set(NATIVE_LIBRARY_SONAME android_arm_${TARGET})
message(STATUS "Adding target: ${NATIVE_LIBRARY_SONAME}")
add_custom_target(${NATIVE_LIBRARY_SONAME})
#set_target_properties(${NATIVE_LIBRARY_SONAME} PROPERTIES
#  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
#  VERSION   ${CMAKE_PROJECT_VERSION_SO}
#)
#install(
#  TARGETS ${NATIVE_LIBRARY_SONAME}
#  LIBRARY DESTINATION lib/${CMAKE_LIBRARY_ARCHITECTURE}
#  COMPONENT "Android NDK Libraries" OPTIONAL
#)

# Prepare flags for C/C++
if(CMAKE_COMPILER_IS_GNUCC)
  set(NATIVE_LIBRARY_CFLAGS "${CMAKE_C_FLAGS}")
else(CMAKE_COMPILER_IS_GNUCC)
  set(NATIVE_LIBRARY_CFLAGS "${ARGN}")
endif(CMAKE_COMPILER_IS_GNUCC)
if(CMAKE_COMPILER_IS_GNUCXX)
  set(NATIVE_LIBRARY_CXXFLAGS "${CMAKE_CXX_FLAGS}")
else(CMAKE_COMPILER_IS_GNUCXX)
  set(NATIVE_LIBRARY_CXXFLAGS "${ARGN}")
endif(CMAKE_COMPILER_IS_GNUCXX)

# Prepare includes (from toolchain)
set(NATIVE_LIBRARY_INCLUDES "${ANDROID_NDK_TOOLCHAIN_ROOT}/include")
include_directories(BEFORE SYSTEM "${NATIVE_LIBRARY_INCLUDES}")

# Fetch the sources from the target
get_target_property(TARGET_SOURCES ${TARGET} SOURCES)
set(NATIVE_LIBRARY_SOURCES "${TARGET_SOURCES}")

# Note each dependency internally and externally
set(NATIVE_LIBRARY_DEPENDS)
set(NATIVE_LIBRARY_IMPORTS)
foreach(DEPENDENCY ${DEPS})
  set(NATIVE_LIBRARY_DEPENDENCY "${NATIVE_LIBRARY_SONAME}_dep_${DEPENDENCY}")
  message(STATUS "Adding dependency target: ${NATIVE_LIBRARY_DEPENDENCY}")
  add_custom_target(${NATIVE_LIBRARY_DEPENDENCY})
  list(APPEND NATIVE_LIBRARY_DEPENDENCIES "${NATIVE_LIBRARY_DEPENDENCY}")
  # For the NDK makefile
  set(NATIVE_LIBRARY_IMPORTS "${NATIVE_LIBRARY_IMPORTS}
\$(call import-module,${DEPENDENCY})")
endforeach(DEPENDENCY)
add_dependencies(${NATIVE_LIBRARY_SONAME} ${NATIVE_LIBRARY_DEPENDENCIES})

# Link each library internally and externally
set(NATIVE_LIBRARY_LDLIBS)
foreach(LIBRARY ${LIBS})
  set(NATIVE_LIBRARY_LDLIBS "${NATIVE_LIBRARY_LDLIBS} -l${LIBRARY}")
  find_library(LIBRARY_PATH "${LIBRARY}"
    PATHS "${ANDROID_NDK_TOOLCHAIN_ROOT}/lib"
    DOC "Library path for lib${LIBRARY}")
  target_link_libraries(${NATIVE_LIBRARY_SONAME} ${LIBRARY_PATH})
endforeach(LIBRARY)

# Export the name of this library? TODO something else...
set(NATIVE_LIBRARY_EXPORTS "${NATIVE_LIBRARY_SONAME}")

configure_file(Android.mk.in "${CMAKE_CURRENT_SOURCE_DIR}/${CMAKE_LIBRARY_ARCHITECTURE}.Android.mk" @ONLY)
configure_file(Application.mk.in "${CMAKE_CURRENT_SOURCE_DIR}/${CMAKE_LIBRARY_ARCHITECTURE}.Application.mk" @ONLY)

endmacro(configure_ndk)

##########################################################################

### Provides a robust mechanism to build Android apps
### See http://en.wikipedia.org/wiki/Jo-ha-kyu
macro(add_app NAME LOCATION)
message(STATUS "Adding app: ${NAME} in '${LOCATION}'")
if(NOT EXISTS "${ANDROID_TOOLCHAIN_CMAKE_SCRIPT}")
  message(FATAL_ERROR "Cannot locate Android CMake script for ${NAME}!")
endif(NOT EXISTS "${ANDROID_TOOLCHAIN_CMAKE_SCRIPT}")

# jo [beginning] (prelude script, run before anything else)
include(${LOCATION}/${NAME}.jo.cmake OPTIONAL)
# Specify some Android options
set(ANDROID_APP_${NAME}_NAME "Android_${NAME}" CACHE STRING "The name of this app" FORCE)
set(ANDROID_APP_${NAME}_ARCH "${CMAKE_LIBRARY_ARCHITECTURE}" CACHE STRING "The arch of this app" FORCE)
mark_as_advanced(ANDROID_APP_${NAME}_NAME ANDROID_APP_${NAME}_ARCH)
# Force a build type to be declared (default to Debug)
set(CMAKE_BUILD_TYPES "(None|Debug|Release|RelWithDebInfo|MinSizeRel)")
if(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
  set(CMAKE_BUILD_TYPE Debug CACHE STRING "${CMAKE_BUILD_TYPES}" FORCE)
endif(NOT CMAKE_BUILD_TYPE MATCHES "${CMAKE_BUILD_TYPES}")
# Specify some default directories/definitions (architecture specific)
set(EXECUTABLE_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/${ANDROID_APP_${NAME}_ARCH}/bin")
set(LIBRARY_OUTPUT_PATH "${CMAKE_SOURCE_DIR}/${ANDROID_APP_${NAME}_ARCH}/lib")

# Where the magic happens
include(${ANDROID_TOOLCHAIN_CMAKE_SCRIPT})
if(NOT ANDROID)
  message(FATAL_ERROR "Cannot enable ANDROID using ${ANDROID_TOOLCHAIN_CMAKE_SCRIPT}!")
endif(NOT ANDROID)
# ha [break] (quickening script, run after Android CMake, before including res/src/jni)
include(${LOCATION}/${APP_NAME}.ha.cmake OPTIONAL)

# Resource files
add_subdirectory(${LOCATION}/res)
# Java source files
add_subdirectory(${LOCATION}/src)
# Native source files
if(EXISTS "${LOCATION}/jni")
  add_subdirectory(${LOCATION}/jni)
endif(EXISTS "${LOCATION}/jni")
# kyu [Rapid] (climax script, run after res/src/jni)
include(${LOCATION}/${APP_NAME}.kyu.cmake OPTIONAL)

endmacro(add_app)

##########################################################################
