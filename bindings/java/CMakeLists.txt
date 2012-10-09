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

# Setup to compile Java sources
find_package(Java 1.6 COMPONENTS Development)
if(Java_Development_FOUND)
  include(UseJava)
else(Java_Development_FOUND)
  message(FATAL_ERROR "Unable to find build tools for Java!")
endif(Java_Development_FOUND)

# Glob Java sources, FIXME dunno if I like this... but GLOB_RECURSE?
file(GLOB LAJ_SOURCES               libalexandria/*.java)
file(GLOB LAJ_FUNCTIONAL_SOURCES    libalexandria/functional/*.java)
file(GLOB LAJ_KERNELS_SOURCES       libalexandria/functional/kernels/*.java)
file(GLOB LAJ_PARAMS_SOURCES        libalexandria/functional/params/*.java)
file(GLOB LAJ_WAVELETS_SOURCES      libalexandria/functional/wavelets/*.java)
file(GLOB LAJ_REINFORCEMENT_SOURCES libalexandria/reinforcement/*.java)
file(GLOB LAJ_NEURALNETWORK_SOURCES libalexandria/reinforcement/nn/*.java)
file(GLOB LAJ_SAMPLING_SOURCES      libalexandria/sampling/*.java)
file(GLOB LAJ_SUPERVISED_SOURCES    libalexandria/supervised/*.java)
file(GLOB LAJ_UNSUPERVISED_SOURCES  libalexandria/unsupervised/*.java)
file(GLOB LAJ_PROOF_SOURCES         libalexandria/proof/*.java)

# Create POC target
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_TARGET_VERSION "${CMAKE_PROJECT_VERSION_SO}")
set(CMAKE_JAVA_JAR_ENTRY_POINT libalexandria/proof/POC)
add_jar(jpoc
  ${LAJ_SOURCES}
  ${LAJ_FUNCTIONAL_SOURCES}
  ${LAJ_KERNEL_SOURCES}
  ${LAJ_PARAMS_SOURCES}
  ${LAJ_WAVELETS_SOURCES}
  ${LAJ_REINFORCEMENT_SOURCES}
  ${LAJ_NEURALNETWORK_SOURCES}
  ${LAJ_SAMPLING_SOURCES}
  ${LAJ_SUPERVISED_SOURCES}
  ${LAJ_UNSUPERVISED_SOURCES}
  ${LAJ_PROOF_SOURCES}
)
# TODO how to add to components? maybe this w/ extra args
install_jar(jpoc "${CMAKE_INSTALL_PREFIX}/bin/jar")

# Build native library, bound using JNI
include_directories(
  ${JNI_INCLUDE_DIRS}
  "${CMAKE_BINARY_DIR}/include"
  "${BASE_INCLUDE_PATH}"
  "${BASE_INCLUDE_PATH}/libalexandria/laJ"
)
file(GLOB NATIVE_SOURCES "laJ_*.c" "laJ_*.cxx")
add_library(jalexandria SHARED ${NATIVE_SOURCES})
target_link_libraries(jalexandria alexandria ${JNI_LIBRARIES})
set(CMAKE_JAVA_TARGET_OUTPUT_DIR "${EXECUTABLE_OUTPUT_PATH}/jar")
set_target_properties(jalexandria PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)
# Make sure javah was invoked (see ./include/libalexandria/laJ/CMakeLists.txt
add_dependencies(jalexandria javah)

# Lastly
install(
  TARGETS jalexandria
  LIBRARY DESTINATION lib
  COMPONENT "Java Bindings" OPTIONAL
)

if(BUILD_JAVADOC)
  set(JAVA_DOC_TITLE "<version>libalexandria.v.${CMAKE_PROJECT_VERSION}</version><br/><pre>What.Is.Your.Intent?</pre>")
  create_javadoc(jalexandria
    PACKAGES libalexandria
      libalexandria.functional
      libalexandria.functional.kernels
      libalexandria.functional.params
      libalexandria.functional.wavelets
      libalexandria.reinforcement
      libalexandria.reinforcement.nn
      libalexandria.sampling
      libalexandria.supervised
      libalexandria.unsupervised
      libalexandria.proof
    SOURCEPATH "${CMAKE_CURRENT_SOURCE_DIR}"
    CLASSPATH "${CMAKE_JAVA_INCLUDE_PATH}"
    WINDOWTITLE "The Library of Alexandria"
    DOCTITLE "${JAVA_DOC_TITLE}"
    AUTHOR TRUE USE TRUE VERSION TRUE
  )
endif(BUILD_JAVADOC)
