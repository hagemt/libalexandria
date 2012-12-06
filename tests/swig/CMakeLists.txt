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

if(SWIG_PYTHON)
  find_package(PythonInterp)
  # TODO check PYTHON_VERSION_STRING?
  if(NOT PYTHONINTERP_FOUND)
    message(FATAL_ERROR "Cannot locate Python interpreter!")
  endif(NOT PYTHONINTERP_FOUND)
  set(ENV{PYTHONPATH} "${LIBRARY_OUTPUT_PATH}")
  # Small POC script
  configure_file(laPy_glue.py "${LIBRARY_OUTPUT_PATH}/__main__.py")
  add_test(swig_laPy_glue ${PYTHON_EXECUTABLE} -S ${LIBRARY_OUTPUT_PATH})
  set_tests_properties(swig_laPy_glue PROPERTIES
    LABELS "swig;python;poc"
    PASS_REGULAR_EXPRESSION "feature not yet complete"
  )
endif(SWIG_PYTHON)

if(SWIG_RUBY)
  find_package(Ruby)
  # TODO check RUBY_VERSION?
  if(NOT RUBY_FOUND)
    message(FATAL_ERROR "Cannot locate Ruby interpreter!")
  endif(NOT RUBY_FOUND)
  set(ENV{RUBYPATH} "${LIBRARY_OUTPUT_PATH}")
  # Small POC script
  add_test(swig_laRb_glue ${RUBY_EXECUTABLE} -S "${CMAKE_CURRENT_SOURCE_DIR}/laRb_glue.rb")
  set_tests_properties(swig_laRb_glue PROPERTIES
    LABELS "swig;ruby;poc"
    PASS_REGULAR_EXPRESSION "cannot load module"
  )
endif(SWIG_RUBY)
