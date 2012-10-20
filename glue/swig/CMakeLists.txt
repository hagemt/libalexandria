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

# Locate SWIG TODO minimum version or components?
find_package(SWIG REQUIRED)
# Create a cache entry so user can specify SWIG arguments
set(CMAKE_SWIG_FLAGS "" CACHE STRING "Extra parameters for SWIG")
mark_as_advanced(CMAKE_SWIG_FLAGS)
# Check everythig is kosher before moving on
if(NOT SWIG_USE_FILE)
  message(FATAL_ERROR "SWIG could not be configured. Please address or disable USE_SWIG")
endif(NOT SWIG_USE_FILE)
include(${SWIG_USE_FILE})

# Locate necessary python libs
find_package(PythonLibs REQUIRED)
include_directories(
  ${PYTHON_INCLUDE_PATH}
  "${CMAKE_CURRENT_SOURCE_DIR}"
)
swig_add_module(la_glue python la_glue.i ../la_glue.c)
swig_link_libraries(la_glue calg ${PYTHON_LIBRARIES})
# TODO install _la_glue.so and la_glue.py file
