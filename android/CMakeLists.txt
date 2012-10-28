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

# A sample app using libalexandria
project(Lannister Java)
cmake_minimum_required(VERSION 2.8.6 FATAL_ERROR)

# Mark that this project uses Android
include("UseAndroid.cmake")

if(NOT USE_ANDROID)
  # UseAndroidApps.cmake messes with the compilers
  include("UseAndroidApps.cmake")
  # Add the Lannister project (this directory)
  add_app("${CMAKE_PROJECT_NAME}" "${CMAKE_CURRENT_SOURCE_DIR}")
endif(NOT USE_ANDROID)

