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
#ifndef LA_TYPES_H
#define LA_TYPES_H

#include <libcalg/compare-pointer.h>
#include <libcalg/hash-pointer.h>
#include <libcalg/hash-table.h>

struct la_buffer_table_value_t {
	void *buffer, *handle;
};

typedef long unsigned int la_UUID_t;

typedef la_UUID_t la_key_t;
typedef struct la_buffer_table_value_t la_value_t;

#endif /* LA_TYPES_H */
