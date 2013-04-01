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

# Glob Java sources; FIXME don't know if I like this...
file(GLOB_RECURSE LAJ_SOURCES *.java)
foreach(LAJ_SOURCE ${LAJ_SOURCES})
	if(NOT LAJ_SOURCE MATCHES "weka")
		set(LAJ_CLEAN_SOURCES ${LAJ_CLEAN_SOURCES} ${LAJ_SOURCE})
	endif(NOT LAJ_SOURCE MATCHES "weka")
endforeach(LAJ_SOURCE ${LAJ_SOURCES})
#message(${LAJ_CLEAN_SOURCES})

# Create POC target
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_TARGET_VERSION "${CMAKE_PROJECT_VERSION_SO}")
add_jar(lib.alexandria ${LAJ_CLEAN_SOURCES})
# TODO how to add to components? maybe this w/ extra args
install_jar(lib.alexandria "${CMAKE_INSTALL_PREFIX}/bin/jar")
install_jni_symlink(lib.alexandria "${CMAKE_INSTALL_PREFIX}/lib")
#set_target_properties(lib.alexandria PROPERTIES
#	JAR_LOCATION "${CMAKE_JAVA_TARGET_OUTPUT_DIR}"
#)

# Make sure native interfaces are built
add_subdirectory(jni)
