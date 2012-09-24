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

# TODO is this a poor method?
file(GLOB_RECURSE STANDARD_HEADER_FILES la*.h)
file(GLOB_RECURSE JNI_HEADER_FILES libalexandria*.h)

install(
  FILES ${STANDARD_HEADER_FILES}
  PUBLIC_HEADER DESTINATION include/libalexandria
  COMPONENT "Standard Headers" OPTIONAL
)

install(
  FILES ${JNI_HEADER_FILES}
  PUBLIC_HEADER DESTINATION include/libalexandria/jla
  COMPONENT "JNI Headers" OPTIONAL
)
