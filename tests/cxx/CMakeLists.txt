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

# CXX proof-of-concept
add_definitions(-DLA_CXX)
add_executable(simple_cxx_poc simple_poc.cxx)
target_link_libraries(simple_cxx_poc alexandriamm)

# CXX Interface test
add_test(simple_cxx_poc "${EXECUTABLE_OUTPUT_PATH}/simple_cxx_poc" "'Hello, World!'")
set_tests_properties(simple_cxx_poc PROPERTIES
  LABELS "cxx;poc"
  PASS_REGULAR_EXPRESSION "Hello, World!"
)
