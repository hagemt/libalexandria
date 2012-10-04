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

# TODO also specify any required external libs
configure_file(libalexandria.pc.in libalexandria.pc @ONLY)

set(EXTRA_INCLUDE_DIRS "-I\${includedir}/libalexandria")

if(BIND_C)
  set(EXTRA_LABELS "(C Bindings)")
  set(EXTRA_LIBRARIES "-lalexandriam")
  configure_file(libalexandria.pc.in libalexandriam.pc @ONLY)
endif(BIND_C)

if(BIND_CXX)
  set(EXTRA_LABELS "(C++ Bindings)")
  set(EXTRA_LIBRARIES "-lalexandriamm")
  configure_file(libalexandria.pc.in libalexandriamm.pc @ONLY)
endif(BIND_CXX)

set(EXTRA_INCLUDE_DIRS "${EXTRA_INCLUDE_DIRS}/jni")

if(BIND_JAVA)
  set(EXTRA_LABELS "(JNI)")
  set(EXTRA_LIBRARIES "-ljalexandria")
  configure_file(libalexandria.pc.in libjalexandria.pc @ONLY)
endif(BIND_JAVA)

file(GLOB PC_FILES "${CMAKE_BINARY_DIR}/dev/*.pc")

install(
  FILES ${PC_FILES}
  DESTINATION lib/pkgconfig
  COMPONENT "Developer Tools" OPTIONAL
)
