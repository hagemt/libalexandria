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
set(POC_JAR_PATH "${EXECUTABLE_OUTPUT_PATH}/jar/jpoc.jar")
if(EXISTS "${POC_JAR_PATH}")
  add_test(simple_java_poc "${Java_JAVA_EXECUTABLE}" -Djava.library.path=${JAVA_LIBRARY_PATH} -jar "${POC_JAR_PATH}" "Hello, World!")
  set_tests_properties(simple_java_poc PROPERTIES PASS_REGULAR_EXPRESSION "Hello, World!")
endif(EXISTS "${POC_JAR_PATH}")
