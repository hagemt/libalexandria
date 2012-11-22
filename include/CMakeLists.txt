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

# macro to #include all the header files matching ${REGEX}
macro(collect_headers REGEX FILES INCLUDES)
  file(GLOB ${FILES} RELATIVE "${BASE_INCLUDE_PATH}" ${REGEX})
  foreach(HEADER_FILE ${${FILES}})
    set(${INCLUDES} "#include \"${HEADER_FILE}\"
${${INCLUDES}}")
  endforeach(HEADER_FILE)
  file(RELATIVE_PATH DIR "${CMAKE_SOURCE_DIR}" "${CMAKE_CURRENT_SOURCE_DIR}")
  set(${INCLUDES} "${${INCLUDES}}/* for ${REGEX} from ${DIR} */" ${ARGN})
endmacro(collect_headers)

## Allow users the comfort of #include "libalexandria.h"

# Pick up bindings (TODO add more over time)
if(BIND_C)
  collect_headers("libalexandria/laC_*.h" LAC_HEADER_FILES LAC_INCLUDES)
endif(BIND_C)
if(BIND_CXX)
  collect_headers("libalexandria/laCXX_*.h" LACXX_HEADER_FILES LACXX_INCLUDES)
endif(BIND_CXX)
# Pick up javah if necessary (TODO should this be collapsed?)
if(BIND_JAVA)
  add_subdirectory(libalexandria/laJ)
endif(BIND_JAVA)
# Pick up swagger if necessary (TODO should this be collapsed?)
if(USE_SWIG)
  add_subdirectory(libalexandria/laX)
endif(USE_SWIG)
# Pick up remaining libraries
collect_headers("libalexandria/la_*.h"  LA_HEADER_FILES  LA_INCLUDES)
collect_headers("libalexandria/laF_*.h" LAF_HEADER_FILES LAF_INCLUDES)

# Configure global header
set(GLOBAL_HEADER_FILE "${BASE_INCLUDE_PATH}/libalexandria.h")
foreach(PREFIX LA;LAF;LAC;LACXX;LAJ;SWAGGER)
  if(NOT ${PREFIX}_INCLUDES)
    set(${PREFIX}_INCLUDES "/* library was not configured for ${PREFIX} */")
  endif(NOT ${PREFIX}_INCLUDES)
endforeach(PREFIX)
configure_file(libalexandria.h.in "${GLOBAL_HEADER_FILE}" @ONLY)
set_property(SOURCE "${GLOBAL_HEADER_FILE}" PROPERTY GENERATED TRUE)

## Specify install locations

install(
  FILES "${GLOBAL_HEADER_FILE}"
  PUBLIC_HEADER DESTINATION include
  COMPONENT "Base Headers" OPTIONAL
)

install(
  FILES ${LA_HEADER_FILES} ${LAF_HEADER_FILES}
  PUBLIC_HEADER DESTINATION include/libalexandria
  COMPONENT "Base Headers" OPTIONAL
)

if(BIND_C)
  install(
    FILES ${LAC_HEADER_FILES}
    PUBLIC_HEADER DESTINATION include/libalexandria
    COMPONENT "C Bindings" OPTIONAL
  )
endif(BIND_C)

if(BIND_CXX)
  install(
    FILES ${LACXX_HEADER_FILES}
    PUBLIC_HEADER DESTINATION include/libalexandria
    COMPONENT "C++ Bindings" OPTIONAL
  )
endif(BIND_CXX)
