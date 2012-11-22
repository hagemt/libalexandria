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

include_directories("${BASE_INCLUDE_PATH}")

if(BIND_C)
  add_subdirectory(c)
endif(BIND_C)

if(BIND_CXX)
  add_subdirectory(cxx)
endif(BIND_CXX)

if(BIND_JAVA)
  add_subdirectory(java)
endif(BIND_JAVA)

# TODO add more as bindings are added

if(USE_SWIG)
  add_subdirectory(swig)
endif(USE_SWIG)
