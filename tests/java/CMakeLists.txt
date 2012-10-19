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

# TODO ensure Java 1.6 COMPONENTS Development?

# Build Java proof-of-concept
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_TARGET_VERSION "${CMAKE_PROJECT_VERSION_SO}")
set(CMAKE_JAVA_JAR_ENTRY_POINT lib/alexandria/proof/POC)
file(GLOB JAVA_POC_SOURCES lib/alexandria/proof/*.java)
add_jar(lib.alexandria.poc
  ${JAVA_POC_SOURCES}
  "${CMAKE_JAVA_TARGET_OUTPUT_PATH}/lib.alexandria.jar"
)

# Make sure native interfaces are built
add_subdirectory(jni)

# Make sure we can run Java code
if(NOT EXISTS "${Java_JAVA_EXECUTABLE}")
  find_package(Java 1.6 COMPONENTS Runtime)
  if(Java_Runtime_FOUND)
    include(UseJava)
  else(Java_Runtime_FOUND)
    message(FATAL_ERROR "Unable to find Java runtime! Cannot run Java tests!")
  endif(Java_Runtime_FOUND)
endif(NOT EXISTS "${Java_JAVA_EXECUTABLE}")

# Setup the library path, which all texts will need at run-time
set(JAVA_LIBRARY_PATH ${LIBRARY_OUTPUT_PATH}:$ENV{LD_LIBRARY_PATH} CACHE STRING "Default java.library.path")
mark_as_advanced(JAVA_LIBRARY_PATH)

# Proof-of-concept test
set(JAVA_POC_PATH "${CMAKE_JAVA_TARGET_OUTPUT_PATH}/lib.alexandria.poc.jar")
if(EXISTS "${POC_JAR_PATH}")
  add_test(simple_java_poc "${Java_JAVA_EXECUTABLE}" -Djava.library.path=${JAVA_LIBRARY_PATH} -jar "${JAVA_POC_PATH}" "Hello, World!")
  set_tests_properties(simple_java_poc PROPERTIES PASS_REGULAR_EXPRESSION "Hello, World!")
  add_test(complex_java_poc "${Java_JAVA_EXECUTABLE}" -Djava.library.path=${JAVA_LIBRARY_PATH} -jar "${JAVA_POC_PATH}")
endif(EXISTS "${POC_JAR_PATH}")
