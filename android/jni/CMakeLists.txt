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

message(STATUS "Configuring Lannister natives...")

# Add any static libs here (they'll need to be built for arch)
list(APPEND NATIVE_LIBRARY_DEPS "cpufeatures")

# Add any libraries to link here (make sure they're built for arch)
list(APPEND NATIVE_LIBRARY_LIBS "android")
list(APPEND NATIVE_LIBRARY_LIBS "log")

# Configure the NDK build scripts for each TARGET
configure_file(Android.mk.in "Android.${ANDROID_APP_ARCH}.mk" @ONLY)
configure_file(Application.mk.in "Application.${ANRDROID_APP_ARCH}.mk" @ONLY)
