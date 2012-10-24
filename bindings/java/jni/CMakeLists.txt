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

# Check setup
if(NOT JNI_INCLUDE_DIRS OR NOT JNI_LIBRARIES)
  find_package(JNI)
  if(NOT JNI_FOUND)
    message(FATAL_ERROR "Unable to find JNI. Please locate or disable BIND_JAVA")
  endif(NOT JNI_FOUND)
endif(NOT JNI_INCLUDE_DIRS OR NOT JNI_LIBRARIES)

# Set some global properties of libalexandria JNI builds
set(LAJ_LIBRARY_SONAME jalexandria)
file(GLOB LAJ_LIBRARY_SOURCES "laJ_*.c")
add_library(${LAJ_LIBRARY_SONAME} SHARED ${LAJ_LIBRARY_SOURCES})
# Make sure javah was invoked (see ./include/libalexandria/laJ/CMakeLists.txt
add_dependencies(${LAJ_LIBRARY_SONAME} javah_lib.alexandria)

if(NOT COMMAND configure_native_target)
  include("${CMAKE_SOURCE_DIR}/glue/ConfigureNativeTarget.cmake")
endif(NOT COMMAND configure_native_target)

# Set all necessary variables in this scope
list(APPEND LAJ_LIBRARY_INCLUDES "${BASE_INCLUDE_PATH}")
# TODO specify LDLIBS as dependencies? Later...?
list(APPEND LAJ_LIBRARY_LIBS "alexandria")
list(APPEND LAJ_LIBRARY_LIBS "calg")
configure_native_target(${LAJ_LIBRARY_SONAME}
  "-Wall -Wextra"
  "${LAJ_LIBRARY_INCLUDES}"
  "${LAJ_LIBRARY_LIBS}"
  "${LAJ_LIBRARY_LIBS}"
)

# Build native library, bound using JNI
include_directories(
  ${NATIVE_LIBRARY_INCLUDES}
  ${JNI_INCLUDE_DIRS}
)
target_link_libraries(${NATIVE_LIBRARY_SONAME}
  ${NATIVE_LIBRARY_EXPORTS}
  ${JNI_LIBRARIES}
)
set_target_properties(
  ${NATIVE_LIBRARY_SONAME} PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)

# Lastly, the install target
install(
  TARGETS ${NATIVE_LIBRARY_SONAME}
  LIBRARY DESTINATION lib
  COMPONENT "Java Bindings" OPTIONAL
)

