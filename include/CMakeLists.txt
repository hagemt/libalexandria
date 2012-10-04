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

# Global header (TODO is this just a bad idea?)
file(GLOB LAF_HEADERS RELATIVE "${LIBRARY_INCLUDE_PATH}" "libalexandria/laF_*.h")
foreach(LAF_HEADER ${LAF_HEADERS})
  set(LAF_INCLUDES "#include \"${LAF_HEADER}\"
${LAF_INCLUDES}")
endforeach(BASE_HEADER ${BASE_HEADERS})
configure_file(libalexandria.h.in libalexandria.h @ONLY)
install(FILES
  "${CMAKE_BINARY_DIR}/libalexandria.h"
  DESTINATION include
  COMPONENT "Base Headers" OPTIONAL
)


install(
  FILES libalexandria.h
  PUBLIC_HEADER DESTINATION include
  COMPONENT "Base Headers" OPTIONAL
)

add_subdirectory(libalexandria)
