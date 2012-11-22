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
add_dependencies(lib.alexandria.poc lib.alexandria)

# FIXME but I hate this kludge more... (stupid java classpath)
set(LAJ_POC_JAR_NAME "lib.alexandria.poc-${CMAKE_PROJECT_VERSION_SO}.jar")
set(LAJ_POC_JAR_FILE "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/${LAJ_POC_JAR_NAME}")
set(LAJ_MANIFEST_NAME "lib.alexandria.poc.jar.manifest.extras.txt")
set(LAJ_MANIFEST_FILE "${CMAKE_SOURCE_DIR}/dev/${LAJ_MANIFEST_FILE}")
add_custom_command(OUTPUT "${LAJ_MANIFEST_NAME}"
  COMMAND "${CMAKE_COMMAND}" -E copy_if_different "${LAJ_MANIFEST_FILE}" "${LAJ_MANIFEST_NAME}"
  COMMAND "${Java_JAR_EXECUTABLE}" umf "${LAJ_MANIFEST_NAME}" "${LAJ_POC_JAR_FILE}"
  MAIN_DEPENDENCY lib.alexandria.poc
  WORKING_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIRECTORY}"
  COMMENT "Adding in ${LAJ_MANIFEST_NAME}" VERBATIM
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
