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

## Create test executables

# Gather all Fortran sources that require processing
file(GLOB FORTRAN_SOURCES "*.F")
set_property(SOURCE ${FORTRAN_SOURCES} PROPERTY Fortran_FORMAT FIXED)
# Numerical algorithms
add_executable(test_numerics ${FORTRAN_SOURCES})
target_link_libraries(test_numerics alexander)

## Register named tests

# Kernel accuracy test
add_test(kernel_accuracy "${EXECUTABLE_OUTPUT_PATH}/test_numerics")
set_tests_properties(kernel_accuracy PROPERTIES
	LABELS "fortran;numerical"
	PASS_REGULAR_EXPRESSION "5"
	PASS_REGULAR_EXPRESSION "-1"
	PASS_REGULAR_EXPRESSION "0"
	PASS_REGULAR_EXPRESSION "12"
	PASS_REGULAR_EXPRESSION "8"
)
