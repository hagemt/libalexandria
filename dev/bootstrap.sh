#!/usr/bin/env bash

COMMAND_CMAKE=$(which cmake)
COMMAND_GIT=$(which git)
INFO_FILE="build-info.txt"
CONF_FILE="user.CMakeCache.txt"

# Check for cmake, be gentle with users
if [ ! -x "${COMMAND_CMAKE}" ]; then
	echo "Sorry, you need cmake 2.8.0 or higher..."
	echo "(hint: you can download it from cmake.org)"
	exit 1
fi

# Check for git, and create info file(s) if necessary
if [ -x "${COMMAND_GIT}" ]; then
	echo "Found $(${COMMAND_GIT} --version)"
	GIT_REPO=$(${COMMAND_GIT} rev-parse --show-toplevel)
	GIT_HASH=$(${COMMAND_GIT} rev-parse -q HEAD)
	echo "${GIT_HASH}" > "${INFO_FILE}"
	if [ ! -f "release-${INFO_FILE}" ]; then
		cp "${INFO_FILE}" "release-${INFO_FILE}"
	fi
fi

# Default parameters, if absolutely necessary
SOURCE_DIR=${GIT_REPO:-"$(cd \"$(dirname \"$0\")/..\" && pwd)"}
SOURCE_TAG=${GIT_HASH:-"$(head -qn 1 "release-${INFO_FILE}")"}

# Configuration
BUILD_DIR="${SOURCE_DIR}/build-${SOURCE_TAG}"
BUILD_GEN=${USE_GENERATOR:-"Unix Makefiles"}
if [ -n "${BUILD_DEV}" ]; then
	if [ ! -f "../${CONF_FILE}" ]; then
		ln -v "${CONF_FILE}" ..
	fi
fi
echo "Configuring ${SOURCE_DIR} (tag: ${SOURCE_TAG})"
mkdir -v "${BUILD_DIR}"
pushd "${BUILD_DIR}"
${COMMAND_CMAKE} -G "${BUILD_GEN}" ..
popd
${COMMAND_CMAKE} --build "${BUILD_DIR}"
