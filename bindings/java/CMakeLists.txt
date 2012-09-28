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
  message(FATAL_ERROR "Unable to find build tools for Java!")
endif(Java_Development_FOUND)

set(CMAKE_JAVA_TARGET_OUTPUT_DIR "${EXECUTABLE_OUTPUT_PATH}")

# Create POC target
set(CMAKE_JNI_TARGET TRUE)
set(CMAKE_JAVA_TARGET_VERSION "${CMAKE_PROJECT_VERSION_SO}")
set(CMAKE_JAVA_JAR_ENTRY_POINT libalexandria/proof/POC)
add_jar(jpoc
  libalexandria/Generate.java
  libalexandria/LabelledEntity.java
  libalexandria/LearningModel.java
  libalexandria/ModelType.java

  libalexandria/ann/Cortex.java
  libalexandria/ann/NativeWorker.java
  libalexandria/ann/NeuralNetwork.java

  libalexandria/functional/Kernel.java
  libalexandria/functional/KernelMap.java
  libalexandria/functional/Parameterized.java
  libalexandria/functional/ParameterTreeMap.java
  libalexandria/functional/Wavelet.java

  libalexandria/proof/POC.java

  libalexandria/reinforcement/MarkovDecisionProcess.java

  libalexandria/sampling/Cardinal.java
  libalexandria/sampling/ClosedInterval.java
  libalexandria/sampling/ContinuousSampleSpace.java
  libalexandria/sampling/DiscreteSampleSpace.java
  libalexandria/sampling/Feature.java
  libalexandria/sampling/MetricSpace.java
  libalexandria/sampling/Ordinal.java
  libalexandria/sampling/SampleSpace.java

  libalexandria/supervised/ClassificationAlgorithm.java
  libalexandria/supervised/KSVM.java
  libalexandria/supervised/RegressionAlgorithm.java
  libalexandria/supervised/SupportVectorMachine.java

  libalexandria/unsupervised/ClusteringAlgorithm.java
  libalexandria/unsupervised/KMeans.java
  libalexandria/unsupervised/SeparationAlgorithm.java
)
install_jar(jpoc "${CMAKE_INSTALL_PREFIX}/lib/jar")

# Generate JNI headers
add_custom_command(TARGET jpoc POST_BUILD
  COMMAND "${Java_JAVAH_EXECUTABLE}" -jni -d "${CMAKE_SOURCE_DIR}/include"
    "libalexandria.ann.NativeWorker"
    "libalexandria.functional.Kernel"
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

install(
  TARGETS jalexandria
  ARCHIVE DESTINATION lib
  LIBRARY DESTINATION lib
  COMPONENT "Java Bindings" OPTIONAL
)
