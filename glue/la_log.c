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

#include <assert.h>
#include <stdarg.h>
#include <stdio.h>

#include "la_log.h"

void
__la_log_print(int code, const char *tag, const char *fmt, ...)
{
	const char *id;
	va_list args;
	if (code < LOG_LIMIT) {
		return;
	}
	switch (code) {
	case LA_LOG_DEBUG: id = "debug";   break;
	case LA_LOG_INFO:  id = "info";    break;
	case LA_LOG_WARN:  id = "warning"; break;
	case LA_LOG_ERROR: id = "error";   break;
	case LA_LOG_FATAL: id = "fatal";   break;
	default:           id = "verbose"; break;
	}
	fprintf(stderr, "[%s] %s: ", tag, id);
	va_start(args, fmt);
	vfprintf(stderr, fmt, args);
	va_end(args);
	/* FIXME system-independent newline? */
	fprintf(stderr, "\n");
	/* TODO needs more sophisticated level method? */
}

void
__la_log_assert(int assertion, const char *tag, const char *msg)
{
	if (!assertion) {
		fprintf(stderr, "[%s] %s (assertion failed)\n", tag, msg);
	}
}
