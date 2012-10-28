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

message(STATUS "Configuring Lannister native sources...")

# Add any static libs here (they'll need to be built for arch)
list(APPEND NATIVE_LIBRARY_DEPS "cpufeatures")

# Add any libraries to link here (make sure they're built for arch)
#list(APPEND NATIVE_LIBRARY_LIBS "android")
list(APPEND NATIVE_LIBRARY_LIBS "log")

# This will generate the proper Android.mk and Application.mk
configure_ndk(alexandria
	"${NATIVE_LIBRARY_DEPS}" # Extra libraries to pre-build
	"${NATIVE_LIBRARY_LIBS}" # Extra libraries to link
	"-Wall -Wextra"          # Extra arguments are treated as flags
)
