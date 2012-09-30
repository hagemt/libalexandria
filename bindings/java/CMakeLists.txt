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

# Set some Java arguments, FIXME better output dir?
set(CMAKE_JAVA_TARGET_OUTPUT_DIR "${EXECUTABLE_OUTPUT_PATH}")
set(CMAKE_JAVA_COMPILE_FLAGS -Xlint:unchecked)

# Glob Java sources, FIXME dunno if I like this...
file(GLOB JLA_SOURCES              libalexandria/*.java)
file(GLOB JLA_ANN_SOURCES          libalexandria/ann/*.java)
file(GLOB JLA_FUNCTIONAL_SOURCES   libalexandria/functional/*.java)
file(GLOB JLA_KERNELS_SOURCES      libalexandria/functional/kernels/*.java)
file(GLOB JLA_PARAMS_SOURCES       libalexandria/functional/params/*.java)
file(GLOB JLA_SAMPLING_SOURCES     libalexandria/sampling/*.java)
file(GLOB JLA_SUPERVISED_SOURCES   libalexandria/supervised/*.java)
file(GLOB JLA_UNSUPERVISED_SOURCES libalexandria/unsupervised/*.java)
file(GLOB JLA_PROOF_SOURCES        libalexandria/proof/*.java)

# Create POC target
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_TARGET_VERSION "${CMAKE_PROJECT_VERSION_SO}")
set(CMAKE_JAVA_JAR_ENTRY_POINT libalexandria/proof/POC)
add_jar(jpoc
  ${JLA_SOURCES}
  ${JLA_ANN_SOURCES}
  ${JLA_FUNCTIONAL_SOURCES}
  ${JLA_KERNEL_SOURCES}
  ${JLA_PARAMS_SOURCES}
  ${JLA_SAMPLING_SOURCES}
  ${JLA_SUPERVISED_SOURCES}
  ${JLA_UNSUPERVISED_SOURCES}
  libalexandria/functional/wavelets/Wavelet.java
  libalexandria/reinforcement/MarkovDecisionProcess.java
)
install_jar(jpoc "${CMAKE_INSTALL_PREFIX}/lib/jar")

# Generate JNI headers
add_custom_command(TARGET jpoc POST_BUILD
  COMMAND "${Java_JAVAH_EXECUTABLE}" -jni -d "${CMAKE_SOURCE_DIR}/include"
    "libalexandria.ann.Cortex"
    "libalexandria.functional.kernels.Kernel"
    "libalexandria.functional.wavelets.Wavelet"
    "libalexandria.proof.POC"
  WORKING_DIRECTORY "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/CMakeFiles/jpoc.dir"
  COMMENT "       Updating JNI headers..." VERBATIM)
# FIXME can the working directory be fetched from add_jar?

# Build native library, bound using JNI
include_directories(${JNI_INCLUDE_DIRS})
file(GLOB NATIVE_SOURCES "jla_*.c")
add_library(jalexandria SHARED ${NATIVE_SOURCES})
target_link_libraries(jalexandria alexandria ${JNI_LIBRARIES})
set_target_properties(jalexandria PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)
add_dependencies(jalexandria jpoc)

# Specify install information
install(
  TARGETS jalexandria
  ARCHIVE DESTINATION lib
  LIBRARY DESTINATION lib
  COMPONENT "Java Bindings" OPTIONAL
)
