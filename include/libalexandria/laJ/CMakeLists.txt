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

# Check that we can use javah
if(NOT Java_JAVAH_EXECUTABLE)
  message(FATAL_ERROR "Cannot locate javah executable. Please specify or disable Java bindings")
endif(NOT Java_JAVAH_EXECUTABLE)

# Macro to generate JNI headers
macro(javah JAVA_TARGET)
add_custom_target(javah_${JAVA_TARGET} DEPENDS ${JAVA_TARGET}
  COMMAND "${Java_JAVAH_EXECUTABLE}" -jni -d "${CMAKE_CURRENT_SOURCE_DIR}" ${ARGN}
  #COMMAND "cmake -E touch ${CMAKE_CURRENT_LIST_FILE}"
  WORKING_DIRECTORY "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/CMakeFiles/${JAVA_TARGET}.dir"
  COMMENT "Updating JNI headers for ${JAVA_TARGET}..." VERBATIM
)
# TODO fix problem where this isn't properly configured
# TODO need SOURCES for generation? pass ${JNI_HEADER_FILES}
# TODO FUTURE neater method?
endmacro(javah)

# Generate headers for lib.alexandria
collect_headers("*.h" LAJ_HEADER_FILES LAJ_INCLUDES PARENT_SCOPE)
set_property(SOURCE ${LAJ_HEADER_FILES} PROPERTY GENERATED TRUE)
javah(lib.alexandria
  "lib.alexandria.functional.RealParameterizedFunction"
  "lib.alexandria.functional.kernels.Kernel"
  "lib.alexandria.functional.wavelets.Wavelet"
  "lib.alexandria.pipeline.Aqueduct"
  "lib.alexandria.reinforcement.nn.NativeWorker"
)

# Install these (if possible)
install(
  FILES ${LAJ_HEADER_FILES}
  PUBLIC_HEADER DESTINATION include/libalexandria/laJ
  COMPONENT "Java Bindings" OPTIONAL
)

# Generate headers for test code
if(BUILD_TESTING)
  javah(lib.alexandria.poc "lib.alexandria.proof.POC")
endif(BUILD_TESTING)
