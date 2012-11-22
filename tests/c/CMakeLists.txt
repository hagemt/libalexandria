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

# C proof-of-concept
add_definitions(-DLA_C)
add_executable(simple_c_poc simple_poc.c)
target_link_libraries(simple_c_poc alexandria)

# C Interface test
add_test(simple_c_poc "${EXECUTABLE_OUTPUT_PATH}/simple_c_poc" "Hello, World!")
set_tests_properties(simple_c_poc PROPERTIES
  LABELS "c;poc"
  PASS_REGULAR_EXPRESSION "Hello, World!"
)
