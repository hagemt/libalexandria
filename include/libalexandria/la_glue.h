/*
 *    This file is part of libalexandria.
 *
 *    libalexandria is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    libalexandria is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.
 */
#ifndef LA_GLUE_H
#define LA_GLUE_H

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <libcalg/compare-pointer.h>
#include <libcalg/hash-pointer.h>
#include <libcalg/hash-table.h>

/* Important data structures */

struct la_buffer_table_value_t {
	void *buffer, *handle;
};

typedef long long unsigned int la_UUID_t;

/* Important library functions */

void la_initialize(la_UUID_t);
void la_finalize(la_UUID_t);

/* Utility functions */

#ifdef USE_ANDROID
#include <android/log.h>
#define LOG(LEVEL,...) __android_log_print(LEVEL,LA_LOG_TAG,__VA_ARGS__)
#define LOGA(COND,...) __android_log_assert(COND,LA_LOG_TAG,__VA_ARGS__)
/* Aliases for different log-levels */
#define LOGV(...) LOG(ANDROID_LOG_VERBOSE,__VA_ARGS__)
#define LOGD(...) LOG(ANDROID_LOG_DEBUG,  __VA_ARGS__)
#define LOGI(...) LOG(ANDROID_LOG_INFO,   __VA_ARGS__)
#define LOGW(...) LOG(ANDROID_LOG_WARN,   __VA_ARGS__)
#define LOGE(...) LOG(ANDROID_LOG_ERROR,  __VA_ARGS__)
#define LOGF(...) LOG(ANDROID_LOG_FATAL,  __VA_ARGS__)
#endif /* USE_ANDROID */

#define LA_INFO_INCOMPLETE "[info] '%s' (feature not yet implemented)\n"
void la_print_info_incomplete(const char *);

#endif /* LA_GLUE_H */
