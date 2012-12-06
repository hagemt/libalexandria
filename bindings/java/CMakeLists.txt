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

# Glob Java sources, FIXME dunno if I like this... but GLOB_RECURSE?
file(GLOB LAJ_SOURCES               lib/alexandria/*.java)
file(GLOB LAJ_FUNCTIONAL_SOURCES    lib/alexandria/functional/*.java)
file(GLOB LAJ_KERNELS_SOURCES       lib/alexandria/functional/kernels/*.java)
file(GLOB LAJ_PARAMS_SOURCES        lib/alexandria/functional/params/*.java)
file(GLOB LAJ_SIGMOID_SOURCES       lib/alexandria/functional/sigmoid/*.java)
file(GLOB LAJ_WAVELETS_SOURCES      lib/alexandria/functional/wavelets/*.java)
file(GLOB LAJ_LOGGING_SOURCES       lib/alexandria/logging/*.java)
file(GLOB LAJ_NAMING_SOURCES        lib/alexandria/naming/*.java)
file(GLOB LAJ_PIPELINE_SOURCES      lib/alexandria/pipeline/*.java)
file(GLOB LAJ_REINFORCEMENT_SOURCES lib/alexandria/reinforcement/*.java)
file(GLOB LAJ_NEURALNETWORK_SOURCES lib/alexandria/reinforcement/nn/*.java)
file(GLOB LAJ_SAMPLING_SOURCES      lib/alexandria/sampling/*.java)
file(GLOB LAJ_SUPERVISED_SOURCES    lib/alexandria/supervised/*.java)
file(GLOB LAJ_UNSUPERVISED_SOURCES  lib/alexandria/unsupervised/*.java)

# Create POC target
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_TARGET_VERSION "${CMAKE_PROJECT_VERSION_SO}")
add_jar(lib.alexandria
  ${LAJ_SOURCES}
  ${LAJ_FUNCTIONAL_SOURCES}
  ${LAJ_KERNEL_SOURCES}
  ${LAJ_PARAMS_SOURCES}
  ${LAJ_SIGMOID_SOURCES}
  ${LAJ_WAVELETS_SOURCES}
  ${LAJ_LOGGING_SOURCES}
  ${LAJ_NAMING_SOURCES}
  ${LAJ_PIPELINE_SOURCES}
  ${LAJ_REINFORCEMENT_SOURCES}
  ${LAJ_NEURALNETWORK_SOURCES}
  ${LAJ_SAMPLING_SOURCES}
  ${LAJ_SUPERVISED_SOURCES}
  ${LAJ_UNSUPERVISED_SOURCES}
)
# TODO how to add to components? maybe this w/ extra args
install_jar(lib.alexandria "${CMAKE_INSTALL_PREFIX}/bin/jar")
install_jni_symlink(lib.alexandria "${CMAKE_INSTALL_PREFIX}/lib")
#set_target_properties(lib.alexandria PROPERTIES JAR_LOCATION "${CMAKE_JAVA_TARGET_OUTPUT_DIR}")

# Make sure native interfaces are built
add_subdirectory(jni)
