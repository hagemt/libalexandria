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

if(NOT JNI_INCLUDE_DIRS OR NOT JNI_LIBRARIES)
  message(FATAL_ERROR "Where did the JNI headers/libraries go?")
endif(NOT JNI_INCLUDE_DIRS OR NOT JNI_LIBRARIES)

include_directories(
  "${BASE_INCLUDE_PATH}"
  #"${BASE_INCLUDE_PATH}/libalexandria/laJ"
  ${JNI_INCLUDE_DIRS}
)

# TODO make macro for building JNI lib?
add_library(jpoc SHARED laJ_print.c)
add_dependencies(jpoc javah_lib.alexandria.poc)
target_link_libraries(jpoc
  jalexandria
  ${JNI_LIBRARIES}
)

set_target_properties(jpoc PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)
