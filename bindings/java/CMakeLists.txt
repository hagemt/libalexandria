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

find_package(Java 1.6 COMPONENTS Development)

if(Java_Development_FOUND)
  include(UseJava)
else(Java_Development_FOUND)
  message(FATAL "Unable to find build tools for Java!")
endif(Java_Development_FOUND)

# Create POC target
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_TARGET_VERSION ${CMAKE_PROJECT_VERSION})
set(CMAKE_JAVA_JAR_ENTRY_POINT libalexandria/proof/POC)
add_jar(poc libalexandria/proof/POC.java)

# Generate JNI headers
get_target_property(NATIVE_OUTPUT_DIR poc CLASS_DIR)
set(JAVA_POC_CLASS "${NATIVE_OUTPUT_DIR}/POC.class")
#execute_process(COMMAND "${Java_JAVAH_EXECUTABLE}" -jni "${JAVA_POC_CLASS}" WORKING_DIRECTORY "${CMAKE_SOURCE_DIR}/include")

# Build native library, bound using JNI
include_directories(${JNI_INCLUDE_DIRS})
add_library(libalexandria laF_print.c laF_SVM.c)
target_link_libraries(libalexandria libalexandriaF ${JNI_LIBRARIES})
