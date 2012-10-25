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
if(SWIG_USE_FILE)
  include(UseSWIG)
else(SWIG_USE_FILE)
  message(FATAL_ERROR "SWIG could not be configured. Please address or disable USE_SWIG")
endif(SWIG_USE_FILE)
# Place everything we need right here
set(LIBRARY_OUTPUT_PATH "${CMAKE_CURRENT_BINARY_DIR}")

# Create a cache entry so user can specify SWIG arguments
set(SWIG_EXTRA_FLAGS "" CACHE STRING "Extra parameters for SWIG")
mark_as_advanced(SWIG_EXTRA_FLAGS)

# Locate necessary python libs/includes
find_package(PythonLibs REQUIRED)
include_directories(${PYTHON_INCLUDE_PATH})

## Create python bindings for libkolla
#swig_add_module(la_glue python la_glue.i ${SWIG_EXTRA_FLAGS})
#swig_link_libraries(la_glue kolla calg ${PYTHON_LIBRARIES})
#
## Install the files generated this way
#install(FILES
#  "${CMAKE_CURRENT_BINARY_DIR}/_la_glue.so"
#  "${CMAKE_CURRENT_BINARY_DIR}/la_glue.py"
#  DESTINATION bin/python
#  COMPONENT "Swagger" OPTIONAL
#)
