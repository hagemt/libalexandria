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

file(GLOB LAF_HEADER_FILES laF_*.h)
install(
  FILES ${LAF_HEADER_FILES}
  PUBLIC_HEADER DESTINATION include/libalexandria
  COMPONENT "Base Headers" OPTIONAL
)

if(BIND_C)
  file(GLOB LAC_HEADER_FILES laC_*.h)
  install(
    FILES ${LAC_HEADER_FILES}
    PUBLIC_HEADER DESTINATION include/libalexandria
    COMPONENT "C Bindings" OPTIONAL
  )
endif(BIND_C)

if(BIND_CXX)
  file(GLOB LACXX_HEADER_FILES laCXX_*.h)
  install(
    FILES ${LACXX_HEADER_FILES}
    PUBLIC_HEADER DESTINATION include/libalexandria
    COMPONENT "C++ Bindings" OPTIONAL
  )
endif(BIND_CXX)

if(BIND_JAVA)
  add_subdirectory(jni)
endif(BIND_JAVA)
