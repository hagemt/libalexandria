#!/usr/bin/env bash

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

# FIXME make sure all these exist?
CC_COMMAND=$(which cc)
LD_COMMAND=$(which ld)
GIT_COMMAND=$(which git)
PYTHON_COMMAND=$(which python)
RUBY_COMMAND=$(which ruby)
SWIG_COMMAND=$(which swig)
PKG_CONFIG=$(which pkg-config)

# Compiling options (machine dependent)
CALG_CFLAGS=$($PKG_CONFIG --cflags libcalg-1.0)
CALG_LDLIBS=$($PKG_CONFIG   --libs libcalg-1.0)
python_INCLUDE_DIR='/usr/include/python2.6'
ruby_INCLUDE_DIR='/usr/include/ruby-1.9.1'
python_MODULE_PREFIX='_'
ruby_MODULE_PREFIX='swagger.'

# Experimenting with SWIG using the following:
PACKAGE_NAME='swagger'
WRAPPER_SUFFIX='wrapper.c'
LANGUAGES=( 'python' 'ruby' )
MODULES=( 'kolla' )
SOURCES_kolla="../la_glue.c ../la_helper.c ../la_log.c"

# Initialize the package directory using the main interface
for LANGUAGE in ${LANGUAGES[@]}; do
	$SWIG_COMMAND -$LANGUAGE -outdir "${PACKAGE_NAME}" -o "${LANGUAGE}_${WRAPPER_SUFFIX}" "${PACKAGE_NAME}.i"
done

# Compile and link each module's sources
for MODULE in ${MODULES[@]}; do
	echo "Building sources for '$MODULE' module..."
	SOURCES_VARIABLE="SOURCES_$MODULE"
	SOURCES="${!SOURCES_VARIABLE}"
	$CC_COMMAND -c $SOURCES -fPIC $CALG_CFLAGS -I../../include/libalexandria
	for LANGUAGE in ${LANGUAGES[@]}; do
		echo "Wrapping '$MODULE' module using '$LANGUAGE' language..."
		INCLUDE_DIR_VARIABLE="${LANGUAGE}_INCLUDE_DIR"
		INCLUDE_DIR="${!INCLUDE_DIR_VARIABLE}"
		$CC_COMMAND -c "${LANGUAGE}_${WRAPPER_SUFFIX}" -fPIC -I$INCLUDE_DIR
		MODULE_PREFIX_VARIABLE="${LANGUAGE}_MODULE_PREFIX"
		MODULE_NAME="$PACKAGE_NAME/${!MODULE_PREFIX_VARIABLE}$MODULE.so"
		$LD_COMMAND -shared la_*.o ${LANGUAGE}_*.o $CALG_LDLIBS -o "$MODULE_NAME"
	done
done

#$PYTHON_COMMAND -i ./laPy_glue.py
#$RUBY_COMMAND ./laRb_glue.rb
