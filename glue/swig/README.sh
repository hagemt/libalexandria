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

# Experimenting with SWIG using the following:
SWIG_PACKAGE_NAME='swagger'
SWIG_PACKAGE_WRAP='wrapper.c'
SWIG_MODULES=('kolla')
SWIG_SOURCES_kolla="./py_${SWIG_PACKAGE_WRAP} ../la_glue.c ../la_helper.c ../la_log.c"

# FIXME make sure all these exist?
CC_COMMAND=$(which cc)
LD_COMMAND=$(which ld)
GIT_COMMAND=$(which git)
PYTHON_COMMAND=$(which python)
SWIG_COMMAND=$(which swig)
PKG_CONFIG=$(which pkg-config)
CALG_CFLAGS=$($PKG_CONFIG --cflags libcalg-1.0)
CALG_LDLIBS=$($PKG_CONFIG   --libs libcalg-1.0)
LA_INCLUDE_DIR="../../include/libalexandria"
PY_INCLUDE_DIR="/usr/include/python2.6"

# Initialize the package directory using the main interface
$SWIG_COMMAND -python -outdir "${SWIG_PACKAGE_NAME}" -o "py_${SWIG_PACKAGE_WRAP}" "${SWIG_PACKAGE_NAME}.i"

# Compile and link each module's sources
for MODULE in ${SWIG_MODULES[@]}; do
	echo "Building sources for '$MODULE' module..."
	VARIABLE="SWIG_SOURCES_$MODULE"
	SOURCES="${!VARIABLE}"
	$CC_COMMAND -c $SOURCES -fPIC $CALG_CFLAGS -I$LA_INCLUDE_DIR -I$PY_INCLUDE_DIR
	$LD_COMMAND -o "${SWIG_PACKAGE_NAME}/_${MODULE}.so" -shared *.o $CALG_LDLIBS
done

$PYTHON_COMMAND -i ./laPy_glue.py
