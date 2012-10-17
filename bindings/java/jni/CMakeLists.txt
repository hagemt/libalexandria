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

# Configure NDK build script(s)
set(NATIVE_LIBRARY_SONAME jalexandria)
set(NATIVE_LIBRARY_TAG "${PROJECT_LOG_TAG}")
# TODO check GNU and set(NATIVE_LIBRARY_CFLAGS "${CMAKE_C_FLAGS}")
list(APPEND NATIVE_LIBRARY_INCLUDES "${BASE_INCLUDE_PATH}")
list(APPEND NATIVE_LIBRARY_INCLUDES "${BASE_INCLUDE_PATH}/libalexandria/laJ")
# Note: add any static libs here (they'll need to be built for arch)
set(NATIVE_LIBRARY_DEPENDS "cpufeatures") # TODO e.g. native glue
# Note: we don't need to list math, pthread, or std support libraries
set(NATIVE_LIBRARY_LDLIBS "-landroid -llog")
list(APPEND NATIVE_LIBRARY_EXPORTS "alexandria")
list(APPEND NATIVE_LIBRARY_EXPORTS "android")
list(APPEND NATIVE_LIBRARY_EXPORTS "calg")
list(APPEND NATIVE_LIBRARY_EXPORTS "log")
file(GLOB NATIVE_LIBRARY_SOURCES "laJ_*.c" "laJ_*.cxx")
configure_file(Android.mk.in "${CMAKE_SOURCE_DIR}/android/jni/Android.mk" @ONLY)
configure_file(Application.mk.in "${CMAKE_SOURCE_DIR}/android/jni/Application.mk" @ONLY)

# Check setup
if(NOT JNI_INCLUDE_DIRS OR NOT JNI_LIBRARIES)
  find_package(JNI)
  if(NOT JNI_FOUND)
    message(FATAL_ERROR "Unable to find JNI. Please locate or disable BIND_JAVA")
  endif(NOT JNI_FOUND)
endif(NOT JNI_INCLUDE_DIRS OR NOT JNI_LIBRARIES)

# Build native library, bound using JNI
include_directories(
  ${NATIVE_LIBRARY_INCLUDES}
  ${JNI_INCLUDE_DIRS}
)
add_library(${NATIVE_LIBRARY_SONAME} SHARED ${NATIVE_LIBRARY_SOURCES})
# Make sure javah was invoked (see ./include/libalexandria/laJ/CMakeLists.txt
add_dependencies(${NATIVE_LIBRARY_SONAME} javah)
target_link_libraries(${NATIVE_LIBRARY_SONAME}
  ${NATIVE_LIBRARY_EXPORTS}
  ${JNI_LIBRARIES}
)
set_target_properties(
  ${NATIVE_LIBRARY_SONAME} PROPERTIES
  SOVERSION ${CMAKE_PROJECT_VERSION_MAJOR}
  VERSION   ${CMAKE_PROJECT_VERSION_SO}
)

# Lastly, the install target
install(
  TARGETS ${NATIVE_LIBRARY_SONAME}
  LIBRARY DESTINATION lib
  COMPONENT "Java Bindings" OPTIONAL
)

