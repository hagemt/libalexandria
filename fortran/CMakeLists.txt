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

set_property(SOURCE laF_SVM.F laF_print.F PROPERTY Fortran_FORMAT FIXED)

add_library(alexandria laF_SVM.F laF_print.F)

if(NOT BUILD_SHARED_LIBS)
  target_link_libraries(alexandria gfortran)
  set_target_properties(alexandria PROPERTIES COMPILE_FLAGS -fPIC)
endif(NOT BUILD_SHARED_LIBS)
