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
  message(FATAL_ERROR "SWIG could not be configured!")
endif(SWIG_USE_FILE)
# Place everything we need right here
set(LIBRARY_OUTPUT_PATH "${CMAKE_CURRENT_BINARY_DIR}")

# Create a cache entry so user can specify SWIG arguments
set(CMAKE_SWIG_FLAGS "-Wall -outdir ${LIBRARY_OUTPUT_PATH}" CACHE STRING "Extra parameters for SWIG")
set(CMAKE_SWIG_OUTDIR "${CMAKE_CURRENT_SOURCE_DIR}" CACHE PATH "Location to generate SWIG wrappers")
mark_as_advanced(CMAKE_SWIG_FLAGS CMAKE_SWIG_OUTDIR)

if(BIND_PYTHON)
  # Locate necessary python libs/includes
  find_package(PythonLibs REQUIRED)
  if(NOT PYTHONLIBS_FOUND)
    message(FATAL_ERROR "Binding selection requires Python!")
  endif(NOT PYTHONLIBS_FOUND)
  include_directories(${PYTHON_INCLUDE_DIRS})
  swig_add_module(laPy python swagger.i)
  swig_link_libraries(laPy kolla calg ${PYTHON_LIBRARIES})
endif(BIND_PYTHON)

if(BIND_RUBY)
# Locate necessary ruby libs/includes
  find_package(Ruby REQUIRED)
  if(NOT RUBY_FOUND)
    message(FATAL_ERROR "Binding selection requires Ruby!")
  endif(NOT RUBY_FOUND)
  include_directories(${RUBY_INCLUDE_DIRS})
  swig_add_module(laRb ruby swagger.i)
  swig_link_libraries(laRb kolla calg ${RUBY_LIBRARY})
  set_target_properties(laRb PROPERTIES COMPILE_FLAGS -fPIC)
endif(BIND_RUBY)

## Install the files generated using egg/gem?
