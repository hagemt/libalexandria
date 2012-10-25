#!/usr/bin/env bash
# Author: Tor E Hagemann
# Purpose: Bootstrap libalexandria build

TARGET=${1:-'stop'}

if [ "$1" == 'help' ]; then
	echo "This script takes a single argument: a target to configure (e.g. all)"
	echo "You may 'export CMAKE_GENERATOR=<generator>' for ease of use."
	echo "See 'man cmake' for available options on your platform."
	exit 0
fi

# Should always find the right directory
SCRIPT_DIR="$(cd -P $(dirname $0) && pwd)"
if [ ! -x "${SCRIPT_DIR}/$(basename $0)" ]; then
	echo "Problem running '${SCRIPT_DIR}/$(basename $0)'"
	echo "Please check that you can execute this file."
	exit 1
fi

# Some important files
CONF_FILE="${SCRIPT_DIR}/user.CMakeCache.txt"
INFO_FILE="${SCRIPT_DIR}/release-build-info.txt"

# Some important commands
COMMAND_CMAKE=$(which cmake)
COMMAND_GIT=$(which git)
COMMAND_MAKE=$(which make)

# Check for cmake, be gentle with users
if [ ! -x "${COMMAND_CMAKE}" ]; then
	echo "Sorry, you need CMake 2.8.0 or higher..."
	echo "(hint: you can download it from cmake.org)"
	exit 1
fi

# Check for git, and create info file(s) if necessary
if [ -x "${COMMAND_GIT}" ]; then
	echo "Found $(${COMMAND_GIT} --version)"
	GIT_REPO=$("${COMMAND_GIT}" rev-parse --show-toplevel)
	GIT_HASH=$("${COMMAND_GIT}" rev-parse -q HEAD)
	echo "${GIT_HASH}" > "${SCRIPT_DIR}/build-info.txt"
	if [ ! -f "${INFO_FILE}" ]; then
		cp -v "${SCRIPT_DIR}/build-info.txt" "${INFO_FILE}"
	fi
fi

# Default parameters, if absolutely necessary
SOURCE_DIR=${GIT_REPO:-"${SCRIPT_DIR}/.."}
SOURCE_TAG=${GIT_HASH:-"$(head -qn 1 \"${INFO_FILE}\")"}
BUILD_GEN=${CMAKE_GENERATOR:-"Unix Makefiles"}
BUILD_DIR="${SOURCE_DIR}/build-${SOURCE_TAG}"

# Configuration
echo "Configuring ${SOURCE_DIR} (tag: ${SOURCE_TAG})"
if [ ! -e "${BUILD_DIR}" ]; then
	mkdir -v "${BUILD_DIR}"
fi
cd "${BUILD_DIR}"
"${COMMAND_CMAKE}" -C "${CONF_FILE}" -G "${BUILD_GEN}" ..
echo "Bootstrap complete!"

# Run any extra commands requested
case "$TARGET" in
	build) "${COMMAND_CMAKE}" --build "${BUILD_DIR}";;
	stop)  echo "Your build directory: ${BUILD_DIR}";;
	*)     "${COMMAND_MAKE}" $TARGET;;
esac
