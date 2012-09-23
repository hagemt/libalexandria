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
if(BIND_C)
  add_test(simple_c_poc "${EXECUTABLE_OUTPUT_PATH}/simple_c_poc" "Hello, World!")
  set_tests_properties(simple_c_poc PROPERTIES PASS_REGULAR_EXPRESSION "Hello, World!")
endif(BIND_C)

# CXX Interface test
if(BIND_CXX)
  add_test(simple_cxx_poc "${EXECUTABLE_OUTPUT_PATH}/simple_cxx_poc" "Hello, World!")
  set_tests_properties(simple_cxx_poc PROPERTIES PASS_REGULAR_EXPRESSION "Hello, World!")
endif(BIND_CXX)

# Tests for Java bindings
if(BIND_JAVA)
  if(EXISTS "${Java_JAVA_EXECUTABLE}")
    set(JAVA_LIBRARY_PATH ${LIBRARY_OUTPUT_PATH}:$ENV{LD_LIBRARY_PATH} CACHE STRING "Library path for java runtime")
    mark_as_advanced(JAVA_LIBRARY)
    if(EXISTS "${EXECUTABLE_OUTPUT_PATH}/jpoc.jar")
      add_test(simple_java_poc "${Java_JAVA_EXECUTABLE}" -Djava.library.path=${JAVA_LIBRARY_PATH} -jar "${EXECUTABLE_OUTPUT_PATH}/jpoc.jar" "Hello, World!")
      set_tests_properties(simple_java_poc PROPERTIES PASS_REGULAR_EXPRESSION "Hello, World!")
    endif(EXISTS "${EXECUTABLE_OUTPUT_PATH}/jpoc.jar")
  else(EXISTS "${Java_JAVA_EXECUTABLE}")
    find_package(Java COMPONETNS Runtime)
    if(NOT Java_Runtime_FOUND)
      message("Skipping Java tests: unable to find Java runtime")
    endif(NOT Java_Runtime_FOUND)
  endif(EXISTS "${Java_JAVA_EXECUTABLE}")
endif(BIND_JAVA)

# TODO add tests as features are implemented
