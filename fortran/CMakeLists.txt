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

## Setup Fortran <---> C bridge

#set(FortranCInterface_GLOBAL_SYMBOLS
#  mysub_
#  my_sub__
#  MYSUB_
#)
#set(FortranCInterface_MODULE_SYMBOLS
#  __mymodule_MOD_mysub
#  __my_module_MOD_my_sub
#)
include(FortranCInterface)
FortranCInterface_HEADER(laF_mangle.h
	MACRO_NAMESPACE "LAF_"
	SYMBOL_NAMESPACE "LAF_"
	#SYMBOLS mysub [<module>:]<function> ...
)
# Run verification (data bridge)
FortranCInterface_VERIFY(QUIET)
if(BIND_CXX)
FortranCInterface_VERIFY(CXX)
endif(BIND_CXX)

## Build the core library

file(GLOB FORTRAN_SOURCES "*.F")
# TODO does fixed format make sense?
set_property(SOURCE ${FORTRAN_SOURCES} PROPERTY Fortran_FORMAT FIXED)

add_library(alexander ${FORTRAN_SOURCES})
target_link_libraries(alexander kolla ${CALG_LIBRARIES})
set_target_properties(alexander PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)

if(NOT BUILD_SHARED_LIBS)
  if(CMAKE_Fortran_COMPILER_ID STREQUAL GNU)
    target_link_libraries(alexander gfortran)
  endif(CMAKE_Fortran_COMPILER_ID STREQUAL GNU)
  set_target_properties(alexander PROPERTIES COMPILE_FLAGS -fPIC)
endif(NOT BUILD_SHARED_LIBS)

install(
  TARGETS alexander
  ARCHIVE DESTINATION lib
  LIBRARY DESTINATION lib
  COMPONENT "Base Library"
)
