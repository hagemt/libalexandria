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

# Create a cache entry so user can specify SWIG arguments
set(CMAKE_SWIG_OUTDIR "${LIBRARY_OUTPUT_PATH}/swagger" CACHE PATH "Location to generate SWIG wrappers")
set(CMAKE_SWIG_FLAGS "-Wall" CACHE STRING "Extra parameters for SWIG")
mark_as_advanced(CMAKE_SWIG_FLAGS CMAKE_SWIG_OUTDIR FORCE)
if(NOT IS_DIRECTORY "${CMAKE_SWIG_OUTDIR}")
  file(MAKE_DIRECTORY "${CMAKE_SWIG_OUTDIR}")
endif(NOT IS_DIRECTORY "${CMAKE_SWIG_OUTDIR}")
set(LIBRARY_OUTPUT_PATH "${CMAKE_SWIG_OUTDIR}")

if(SWIG_PYTHON)
  # Locate necessary python libs/includes
  find_package(PythonLibs REQUIRED)
  if(NOT PYTHONLIBS_FOUND)
    message(FATAL_ERROR "Binding selection requires Python!")
  endif(NOT PYTHONLIBS_FOUND)
  include_directories(${PYTHON_INCLUDE_DIRS})
  # FIXME the underscore thing is a pain
  swig_add_module(kolla python kolla.i)
  swig_link_libraries(kolla kolla calg ${PYTHON_LIBRARIES})
  list(APPEND SWIG_TARGETS _kolla)
  # Make the folder recognized as a module
  configure_file("python/__init__.py.in" "${CMAKE_SWIG_OUTDIR}/__init__.py" @ONLY)
endif(SWIG_PYTHON)

if(SWIG_RUBY)
# Locate necessary ruby libs/includes
  find_package(Ruby REQUIRED)
  if(NOT RUBY_FOUND)
    message(FATAL_ERROR "Binding selection requires Ruby!")
  endif(NOT RUBY_FOUND)
  include_directories(${RUBY_INCLUDE_DIRS})
  # TODO work out better language-specific names
  swig_add_module(swagger ruby kolla.i)
  swig_link_libraries(swagger kolla calg ${RUBY_LIBRARY})
  list(APPEND SWIG_TARGETS swagger)
endif(SWIG_RUBY)

if(SWIG_TARGETS AND CMAKE_COMPILER_IS_GNUCC)
  set_target_properties(${SWIG_TARGETS} PROPERTIES
    COMPILE_FLAGS "-w"
  )
endif(SWIG_TARGETS AND CMAKE_COMPILER_IS_GNUCC)

## Install the files generated using egg/gem?
