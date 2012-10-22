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
#include <stdarg.h>
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

#include "la_log.h"

/* Utility functions */

void la_print_info_incomplete(const char *);

#endif /* LA_GLUE_H */
