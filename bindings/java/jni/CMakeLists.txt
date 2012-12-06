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

# Check setup (shouldn't be a problem)
if(NOT JNI_INCLUDE_DIRS OR NOT JNI_LIBRARIES)
  message(FATAL_ERROR "Where did the JNI headers/libraries go?")
endif(NOT JNI_INCLUDE_DIRS OR NOT JNI_LIBRARIES)
include_directories(${JNI_INCLUDE_DIRS})
add_definitions(-DLA_JNI)

# Build native library code, bound using JNI
file(GLOB LAJ_LIBRARY_SOURCES "laJ_*.c")
# This is what gets System.loadLibrary(String)'d
add_library(jalexandria SHARED ${LAJ_LIBRARY_SOURCES})
# Make sure javah was invoked (see ./include/libalexandria/laJ/CMakeLists.txt
add_dependencies(jalexandria javah_lib.alexandria)
# TODO also specify android dependencies? Later... if necessary?
target_link_libraries(jalexandria
  alexander
  ${JNI_LIBRARIES}
)

# Advanced target information
set_target_properties(jalexandria PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)
# GCC should be able to discard these warnings
if(CMAKE_COMPILER_IS_GNUCC)
  set_target_properties(jalexandria PROPERTIES
    COMPILE_FLAGS "-Wno-unused-parameter"
  )
endif(CMAKE_COMPILER_IS_GNUCC)

# Lastly, the install
install(
  TARGETS jalexandria
  LIBRARY DESTINATION lib
  COMPONENT "Java Bindings" OPTIONAL
)
