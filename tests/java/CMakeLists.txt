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

list(APPEND CMAKE_JAVA_INCLUDE_PATH "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/lib.alexandria.jar")

# Build Java proof-of-concept
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_TARGET_VERSION "${CMAKE_PROJECT_VERSION_SO}")
set(CMAKE_JAVA_JAR_ENTRY_POINT lib/alexandria/proof/POC)
file(GLOB LAJ_PROOF_SOURCES lib/alexandria/proof/*.java)
add_jar(lib.alexandria.poc ${LAJ_PROOF_SOURCES})
add_dependencies(lib.alexandria.poc lib.alexandria)

# Make sure native interfaces are built
add_subdirectory(jni)
if(USE_ANDROID)
  add_subdirectory(android)
endif(USE_ANDROID)

# Setup the library path, which all texts will need at run-time
set(JAVA_CLASS_PATH ${CMAKE_JAVA_INCLUDE_PATH}:$ENV{CLASSPATH} CACHE STRING "Default Java classpath")
set(JAVA_LIBRARY_PATH ${LIBRARY_OUTPUT_PATH}:$ENV{LD_LIBRARY_PATH} CACHE STRING "Default java.library.path")
mark_as_advanced(JAVA_CLASS_PATH JAVA_LIBRARY_PATH)

# Make it easy to add a variety of Java tests
macro(add_java_test TEST_NAME JAR_FILE)
  add_test(${TEST_NAME}
    "${Java_JAVA_EXECUTABLE}" -verbose
    -classpath "${JAVA_CLASS_PATH}"
    -Djava.library.path=${JAVA_LIBRARY_PATH}
    -jar "${JAR_FILE}" ${ARGN})
endmacro(add_java_test)

# Proof-of-concept test(s)
set(JAVA_POC_PATH "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/lib.alexandria.poc.jar")
add_java_test(simple_java_poc "${JAVA_POC_PATH}" "'Hello, World!'")
set_tests_properties(simple_java_poc PROPERTIES PASS_REGULAR_EXPRESSION "Hello, World!")
add_java_test(complex_java_poc "${JAVA_POC_PATH}")
set_tests_properties(simple_java_poc complex_java_poc PROPERTIES LABELS "java;poc")
