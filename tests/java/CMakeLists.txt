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

# FIXME I hate this whole kludge...
set(LAJ_JAR_NAME "lib.alexandria-${CMAKE_PROJECT_VERSION_SO}.jar")
set(LAJ_JAR_FILE "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/${LAJ_JAR_NAME}")
set(LAJ_POC_JAR_NAME "lib.alexandria.poc-${CMAKE_PROJECT_VERSION_SO}.jar")
set(LAJ_POC_JAR_FILE "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/${LAJ_POC_JAR_NAME}")
set(LAJ_POC_MANIFEST "${CMAKE_CURRENT_BINARY_DIR}/META-INF/MANIFEST.MF")
configure_file("manifest.extras.in" "${LAJ_POC_MANIFEST}" @ONLY)

# Build Java proof-of-concept
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_INCLUDE_PATH "${LAJ_JAR_FILE}")
set(CMAKE_JAVA_JAR_ENTRY_POINT lib/alexandria/proof/POC)
set(CMAKE_JAVA_TARGET_VERSION "${CMAKE_PROJECT_VERSION_SO}")
file(GLOB LAJ_PROOF_SOURCES   lib/alexandria/proof/*.java)
file(GLOB LAJ_TESTING_SOURCES lib/alexandria/testing/*.java)
add_jar(lib.alexandria.poc
  ${LAJ_PROOF_SOURCES}
  ${LAJ_TESTING_SOURCES}
)
# FIXME but I hate this kludge more... (stupid Class-Path: @#$%)
add_custom_target(lib.alexandria.poc.manifest DEPENDS ${LAJ_POC_JAR_FILE}
  COMMAND "${Java_JAR_EXECUTABLE}" umf "${LAJ_POC_MANIFEST}" "${LAJ_POC_JAR_FILE}"
  COMMAND "${Java_JAR_EXECUTABLE}" i "${LAJ_POC_JAR_FILE}"
  COMMENT "Repairing manifest for ${LAJ_POC_JAR_NAME}" VERBATIM
)
# Note: the lib.alexandria.poc and lib.alexandria.poc.jar targets differ
add_dependencies(lib.alexandria.poc
  lib.alexandria
  lib.alexandria.poc.manifest
)

# Make sure native interfaces are built
add_subdirectory(jni)
if(USE_ANDROID)
  add_subdirectory(android)
endif(USE_ANDROID)

# Make it easy to add a variety of Java tests
macro(add_java_test TEST_NAME JAR_FILE)
  add_test(${TEST_NAME}
    "${Java_JAVA_EXECUTABLE}" -ea
    -classpath "${CMAKE_JAVA_TARGET_OUTPUT_DIR}"
    -Djava.library.path=${LIBRARY_OUTPUT_PATH}
    -jar "${JAR_FILE}" ${ARGN})
endmacro(add_java_test)

# Proof-of-concept test(s)
add_java_test(simple_java_poc "${LAJ_POC_JAR_FILE}" "'Hello, World!'")
add_java_test(complex_java_poc "${LAJ_POC_JAR_FILE}")
set_tests_properties(simple_java_poc PROPERTIES
  LABELS "java;poc"
  PASS_REGULAR_EXPRESSION "Hello, World!"
)
set_tests_properties(complex_java_poc PROPERTIES
  LABELS "java;poc;long"
)
