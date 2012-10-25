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

#include <stdlib.h>

#include "la_helper.h"
#include "la_log.h"

void
__la_free_buffer_table_key(HashTableKey k)
{
	LOGD("%p (releasing buffer table key)", k);
	free(k);
}

void
__la_free_buffer_table_value(HashTableValue v)
{
	la_value_t *value = (la_value_t *)(v);
	LOGD("%p (releasing native array: %p)", v, value->buffer);
	free(value->buffer);
	#ifndef NDEBUG
	value->buffer = value->handle = NULL;
	#endif
	free(v);
}

void
__la_dump_value(HashTableValue v)
{
	la_value_t *value = (la_value_t *)(v);
	LOGV("{ %p, %p } (buffer table value)", value->buffer, value->handle);
}

inline void
__la_dump_table(HashTable *table, __value_handler_t func)
{
	HashTableIterator itr;
	if (func == NULL) {
		LOGW("%p (table given invalid printer)", (void *)(table));
		return;
	}
	/* Print each value in detail */
	la_hash_table_iterate(table, &itr);
	while (hash_table_iter_has_more(&itr)) {
		(*func)(hash_table_iter_next(&itr));
	}
}
