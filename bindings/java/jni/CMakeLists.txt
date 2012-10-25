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

# Set some general properties for this target
set(LAJ_LIBRARY_SONAME jalexandria)
list(APPEND LAJ_LIBRARY_INCLUDES "${BASE_INCLUDE_PATH}")
#list(APPEND LAJ_LIBRARY_INCLUDES "${BASE_INCLUDE_PATH}/libalexandria/laJ")
include_directories(
  ${LAJ_LIBRARY_INCLUDES}
  ${JNI_INCLUDE_DIRS}
)

# Build native library code, bound using JNI
file(GLOB LAJ_LIBRARY_SOURCES "laJ_*.c" "laJ_*.cxx")
add_library(${LAJ_LIBRARY_SONAME} SHARED ${LAJ_LIBRARY_SOURCES})
# Make sure javah was invoked (see ./include/libalexandria/laJ/CMakeLists.txt
add_dependencies(${LAJ_LIBRARY_SONAME} javah_lib.alexandria)

# TODO also specify android dependencies? Later... if necessary?
list(APPEND LAJ_LIBRARY_LIBS "alexandria")
list(APPEND LAJ_LIBRARY_LIBS "calg")
target_link_libraries(${LAJ_LIBRARY_SONAME}
  ${LAJ_LIBRARY_LIBS}
  ${JNI_LIBRARIES}
)
set_target_properties(
  ${LAJ_LIBRARY_SONAME} PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)

# Lastly, the install target
install(
  TARGETS ${LAJ_LIBRARY_SONAME}
  LIBRARY DESTINATION lib
  COMPONENT "Java Bindings" OPTIONAL
)
