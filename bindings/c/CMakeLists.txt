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

add_definitions(-DLA_C)
file(GLOB LAC_SOURCES "laC_*.c")

# Build the C bindings into a library
add_library(alexandria ${LAC_SOURCES})
target_link_libraries(alexandria alexander)
set_target_properties(alexandria PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)

install(
  TARGETS alexandria
  ARCHIVE DESTINATION lib
  LIBRARY DESTINATION lib
  COMPONENT "C Bindings" OPTIONAL
)
