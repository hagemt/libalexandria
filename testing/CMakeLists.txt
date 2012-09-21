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

# C Interface test
add_test(simple_c_poc "${EXECUTABLE_OUTPUT_PATH}/simple_c_poc")
set_tests_properties(simple_c_poc PROPERTIES
  PASS_REGULAR_EXPRESSION "Hello, World!")

# CXX Interface test
add_test(simple_cxx_poc "${EXECUTABLE_OUTPUT_PATH}/simple_cxx_poc" "Hello, World!")
set_tests_properties(simple_cxx_poc PROPERTIES
  PASS_REGULAR_EXPRESSION "Hello, World!")

# Java bindings tests
if(BIND_JAVA)
  find_package(Java COMPONENTS Runtime)
  if(Java_Runtime_FOUND AND EXISTS "${EXECUTABLE_OUTPUT_PATH}/poc.jar")
    set(JAVA_ARGS -Djava.library.path=${LIBRARY_OUTPUT_PATH}:$ENV{LD_LIBRARY_PATH} -jar)
    add_test(simple_java_poc "${Java_JAVA_EXECUTABLE}" ${JAVA_ARGS} "${EXECUTABLE_OUTPUT_PATH}/poc.jar" "Hello, World!")
    set_tests_properties(simple_java_poc PROPERTIES
      PASS_REGULAR_EXPRESSION "Hello, World!")
  endif(Java_Runtime_FOUND AND EXISTS "${EXECUTABLE_OUTPUT_PATH}/poc.jar")
endif(BIND_JAVA)

# TODO add tests as features are implemented
