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
mark_as_advanced(ANDROID_TOOLCHAIN_CMAKE_SCRIPT FORCE)

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
  execute_process(
    COMMAND "${ANDROID_CMAKE_SCRIPT}" "${CMAKE_CURRENT_SOURCE_DIR}"
    OUTPUT_FILE "${CMAKE_CURRENT_SOURCE_DIR}/android-download-ndk-output.log"
    ERROR_FILE "${CMAKE_CURRENT_SOURCE_DIR}/android-download-ndk-error.log"
    RESULT_VARIABLE ANDROID_CMAKE_SCRIPT_RESULT
    TIMEOUT 600)
  # Catch failures
  if(ANDROID_CMAKE_SCRIPT_RESULT)
    message(FATAL_ERROR "Problem downloading NDK. See logs: ${CMAKE_CURRENT_SOURCE_DIR}")
  endif(ANDROID_CMAKE_SCRIPT_RESULT)
endif(NOT IS_DIRECTORY "${ANDROID_NDK}")
mark_as_advanced(ANDROID_NDK FORCE)

### Step 3: find a ANDROID_NDK_TOOLCHAIN_ROOT

## Provide a default toolchain path (from NDK doc)
if(NOT ANDROID_NDK_TOOLCHAIN_ROOT)
  set(ANDROID_NDK_TOOLCHAIN_ROOT "/opt/android-toolchain" CACHE PATH "The default Android NDK toolchain location")
endif(NOT ANDROID_NDK_TOOLCHAIN_ROOT)

## Generate a toolchain if one does not already exist
if(NOT IS_DIRECTORY "${ANDROID_NDK_TOOLCHAIN_ROOT}")
  set(MAKE_TOOLCHAIN_SCRIPT "./build/tools/make-standalone-toolchain.sh")
  set(ANDROID_NDK_TOOLCHAIN_ROOT "${CMAKE_CURRENT_SOURCE_DIR}/android-toolchain")
  message(STATUS "Generating Android toolchain in: ${ANDROID_NDK_TOOLCHAIN_ROOT}")
  # FIXME how to specify architectures? unnecessary?
  execute_process(
    COMMAND ${MAKE_TOOLCHAIN_SCRIPT}
      --platform="android-${ANDROID_API_LEVEL}"
      --install-dir="${ANDROID_NDK_TOOLCHAIN_ROOT}"
    WORKING_DIRECTORY "${ANDROID_NDK}"
    OUTPUT_FILE "${CMAKE_CURRENT_SOURCE_DIR}/android-toolchain-make-standalone-output.log"
    ERROR_FILE "${CMAKE_CURRENT_SOURCE_DIR}/android-toolchain-make-standalone-error.log"
    RESULT_VARIABLE MAKE_TOOLCHAIN_SCRIPT_RESULT
    TIMEOUT 60)
  # Catch failures
  if(MAKE_TOOLCHAIN_SCRIPT_RESULT)
    set(ANDROID_NDK_TOOLCHAIN_ROOT)
    message(FATAL_ERROR "Problem generating android toolchain. See logs: ${CMAKE_CURRENT_SOURCE_DIR}")
  endif(MAKE_TOOLCHAIN_SCRIPT_RESULT)
endif(NOT IS_DIRECTORY "${ANDROID_NDK_TOOLCHAIN_ROOT}")

## Cache the value for the user (it either exists, or we made it)
set(ANDROID_NDK_TOOLCHAIN_ROOT "${ANDROID_NDK_TOOLCHAIN_ROOT}" CACHE PATH "The Android NDK toolchain to use" FORCE)
mark_as_advanced(ANDROID_NDK_TOOLCHAIN_ROOT FORCE)

### Step 4: configure every script with these settings

# The following were set above:
#  ANDROID_TOOLCHAIN_CMAKE_SCRIPT ".../android-cmake/toolchain/android.toolchain.cmake" (submodule)
#  ANDROID_NDK                    ".../android-ndk-r8b" (or up to two levels up)
#  ANDROID_NDK_TOOLCHAIN_ROOT     ".../android-toolchain" (generated, or found elsewhere)

# They need to be configured in every app script
file(GLOB APP_SCRIPTS RELATIVE "${CMAKE_CURRENT_SOURCE_DIR}" "*.jo" "*.ha" "*.kyu")
foreach(APP_SCRIPT "${APP_SCRIPTS}")
  message(STATUS "Configuring app script: ${APP_SCRIPT}")
  configure_file("${APP_SCRIPT}" "${CMAKE_CURRENT_SOURCE_DIR}/${APP_SCRIPT}.cmake" @ONLY)
endforeach(APP_SCRIPT)

### Step 5: provide some macros for working with apps

### Adds an important configuration function for the NDK
### See how it is used below in add_app
macro(configure_ndk TARGET DEPS LIBS)
message(STATUS "Configure w/ NDK: '${TARGET}' which depends on '${DEPS}' and links '${LIBS}' with flags '${ARGN}'")
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
set(NATIVE_LIBRARY_CFLAGS "${ARGN}")
set(NATIVE_LIBRARY_CXXFLAGS "${ARGN}")

# Prepare includes (from toolchain)
set(NATIVE_LIBRARY_INCLUDES "${ANDROID_NDK_TOOLCHAIN_ROOT}/sysroot/usr/include")
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
  add_dependencies(${NATIVE_LIBRARY_SONAME} ${NATIVE_LIBRARY_DEPENDENCY})
  # And for the NDK makefile
  set(NATIVE_LIBRARY_DEPENDS "${NATIVE_LIBRARY_DEPENDS} ${DEPENDENCY}")
  set(NATIVE_LIBRARY_IMPORTS "\$(call import-module,${DEPENDENCY})
${NATIVE_LIBRARY_IMPORTS}")
endforeach(DEPENDENCY)
set(NATIVE_LIBRARY_IMPORTS "${NATIVE_LIBRARY_IMPORTS}# for ${NATIVE_LIBRARY_SONAME}")

# Link each library internally and externally
set(NATIVE_LIBRARY_LDLIBS)
foreach(LIBRARY ${LIBS})
  set(NATIVE_LIBRARY_LDLIBS "${NATIVE_LIBRARY_LDLIBS} -l${LIBRARY}")
  find_library(LIBRARY_PATH "${LIBRARY}"
    PATHS "${ANDROID_NDK_TOOLCHAIN_ROOT}/sysroot/usr/lib"
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
mark_as_advanced(ANDROID_APP_${NAME}_NAME ANDROID_APP_${NAME}_ARCH FORCE)
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
