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

## Subdirectories

if(BUILD_EXAMPLES)
  add_subdirectory(examples)
endif(BUILD_EXAMPLES)

# Configure javadoc

if(BUILD_JAVADOC)
  set(JAVA_DOC_VERSION "<version>lib.alexandria.${CMAKE_PROJECT_VERSION_STRING}</version>")
  set(JAVA_DOC_MOTTO "<pre>What.Is.Your.Intent?</pre>")
  set(JAVA_DOC_TITLE "${JAVA_DOC_VERSION}<br/>${JAVA_DOC_MOTTO}")
  create_javadoc(jalexandria
    PACKAGES lib.alexandria
      lib.alexandria.functional
      lib.alexandria.functional.kernels
      lib.alexandria.functional.params
      lib.alexandria.functional.sigmoid
      lib.alexandria.functional.wavelets
      lib.alexandria.logging
      lib.alexandria.naming
      lib.alexandria.pipeline
      lib.alexandria.reinforcement
      lib.alexandria.reinforcement.nn
      lib.alexandria.sampling
      lib.alexandria.supervised
      lib.alexandria.unsupervised
    SOURCEPATH "${CMAKE_SOURCE_DIR}/bindings/java"
    CLASSPATH "${CMAKE_JAVA_INCLUDE_PATH}"
    INSTALLPATH "${CMAKE_SOURCE_DIR}/doc"
    WINDOWTITLE "The Library of Alexandria"
    DOCTITLE "${JAVA_DOC_TITLE}"
    AUTHOR TRUE USE TRUE VERSION TRUE
  )
endif(BUILD_JAVADOC)

## Doxygen configuration

if(BUILD_DOXYGEN)
  # Locate necessary executables, etc.
  find_package(Doxygen)
  if(NOT DOXYGEN_FOUND)
    message(FATAL_ERROR "Could not locate Doxygen package!")
  endif(NOT DOXYGEN_FOUND)
  if(NOT EXISTS "${DOXYGEN_DOT_PATH}")
    message(WARNING "GraphViz 'dot' not found (diagrams disabled)")
  endif(NOT EXISTS "${DOXYGEN_DOT_PATH}")
  configure_file(Doxyfile.in Doxyfile @ONLY)
  add_custom_target(doc
    "${DOXYGEN_EXECUTABLE}" "${CMAKE_CURRENT_BINARY_DIR}/Doxyfile"
    WORKING_DIRECTORY "${CMAKE_CURRENT_BINARY_DIR}"
    COMMENT "Generating API documentation with Doxygen" VERBATIM
  )
endif(BUILD_DOXYGEN)
