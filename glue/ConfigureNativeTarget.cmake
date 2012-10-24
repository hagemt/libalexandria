function(configure_native_target target flags includes deps libs)
  # First set the SONAME, remember PARENT_SCOPE
  set(NATIVE_LIBRARY_SONAME ${target} PARENT_SCOPE)
  # Prepare includes (TODO does this next line work?)
  include_directories(${includes})
  foreach(LA_INCLUDE_DIR ${includes})
    file(GLOB LA_INCLUDES "${LA_INCLUDE_DIR}/*.h")
    list(APPEND NATIVE_LIBRARY_INCLUDES "${LA_INCLUDES}" PARENT_SCOPE)
  endforeach(LA_INCLUDE_DIR)
  # Prepare flags for C/C++
  if(CMAKE_COMPILER_IS_GNUCC)
    set(NATIVE_LIBRARY_CFLAGS "${CMAKE_C_FLAGS}" PARENT_SCOPE)
  else(CMAKE_COMPILER_IS_GNUCC)
    set(NATIVE_LIBRARY_CFLAGS "${flags}" PARENT_SCOPE)
  endif(CMAKE_COMPILER_IS_GNUCC)
  if(CMAKE_COMPILER_IS_GNUCXX)
    set(NATIVE_LIBRARY_CXXFLAGS "${CMAKE_CXX_FLAGS}" PARENT_SCOPE)
  else(CMAKE_COMPILER_IS_GNUCXX)
    set(NATIVE_LIBRARY_CXXFLAGS "${flags}" PARENT_SCOPE)
  endif(CMAKE_COMPILER_IS_GNUCXX)
  # Fetch the sources from the target
  get_target_property(LA_NATIVE_SOURCES ${NATIVE_LIBRARY_SONAME} SOURCES)
  set(NATIVE_LIBRARY_SOURCES "${LA_NATIVE_SOURCES}")
  # Note each dependency internally and externally
  add_dependencies(${NATIVE_LIBRARY_SONAME} ${deps})
  foreach(LA_DEPENDENCY ${deps})
    set(NATIVE_LIBRARY_IMPORTS "${NATIVE_LIBRARY_IMPORTS}
\$(call import-module,${LA_DEPENDENCY})" PARENT_SCOPE)
  endforeach(LA_DEPENDENCY)
  # Link each library internally and externally
  target_link_libraries(${NATIVE_LIBRARY_SONAME} ${libs})
  foreach(LA_LIBRARY ${libs})
    set(NATIVE_LIBRARY_LDLIBS "${NATIVE_LIBRARY_LDLIBS} -l${LA_LIBRARY}" PARENT_SCOPE)
  endforeach(LA_LIBRARY ${libs})
  # Export the name of this library? TODO something else...
  list(APPEND NATIVE_LIBRARY_EXPORTS "${NATIVE_LIBRARY_SONAME}")
endfunction(configure_native_target)
