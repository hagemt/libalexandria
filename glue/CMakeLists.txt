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

# Acquire all the glue (kolla) code
file(GLOB KOLLA_SOURCES "*.c")
add_library(kolla STATIC ${KOLLA_SOURCES})
set_target_properties(kolla PROPERTIES COMPILE_FLAGS -fPIC)
# TODO should install? (probably not)

if(USE_SWIG)
  add_subdirectory(swig)
endif(USE_SWIG)
