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

set_property(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}" PROPERTY GENERATED TRUE)

if(NOT Java_JAVAH_EXECUTABLE)
  message(FATAL_ERROR "Cannot locate javah executable. Please specify or disable Java bindings")
endif(NOT Java_JAVAH_EXECUTABLE)

# Generate JNI headers TODO FUTURE much neater method
add_custom_target(javah DEPENDS jpoc
  COMMAND "${Java_JAVAH_EXECUTABLE}" -jni -d "${CMAKE_CURRENT_SOURCE_DIR}"
    "libalexandria.functional.RealParameterizedFunction"
    "libalexandria.functional.kernels.Kernel"
    "libalexandria.functional.wavelets.Wavelet"
    "libalexandria.reinforcement.nn.NativeWorker"
    "libalexandria.proof.POC"
  WORKING_DIRECTORY "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/CMakeFiles/jpoc.dir"
  COMMENT "Updating JNI headers..." VERBATIM
  SOURCES ${JNI_HEADER_FILES}
)

file(GLOB JNI_HEADER_FILES libalexandria_*.h)

install(
  FILES ${JNI_HEADER_FILES}
  PUBLIC_HEADER DESTINATION include/libalexandria/laJ
  COMPONENT "Java Bindings" OPTIONAL
)
