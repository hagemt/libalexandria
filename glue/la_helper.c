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
#include <stdlib.h>

#include "la_helper.h"
#include "la_log.h"

unsigned long
__la_hash_buffer_table_key(HashTableKey k)
{
	la_key_t key;
	assert(k);
	key = *((la_key_t *)(k));
	return (unsigned long)(key);
}

int
__la_compare_buffer_table_keys(HashTableKey k1, HashTableKey k2)
{
	la_key_t key1, key2;
	assert(k1 && k2);
	key1 = *((la_key_t *)(k1));
	key2 = *((la_key_t *)(k2));
	return key1 == key2;
}

void
__la_free_buffer_table_key(HashTableKey k)
{
	LOGV("%p (hash table: releasing key)", k);
	assert(k);
	free(k);
}

void
__la_free_buffer_table_value(HashTableValue v)
{
	la_value_t *value = (la_value_t *)(v);
	LOGV("%p (hash table: releasing value)", v);
	assert(value);
	LOGV("%p (hash table: releasing native array %p)", v, value->buffer);
	assert(value->buffer);
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
	assert(value);
	LOGV("{ %p, %p } (hash table: dump value)", value->buffer, value->handle);
}

void
__la_dump_table(HashTable *table, __value_handler_t func)
{
	HashTableIterator itr;
	LOGV("%p (hash table: dump table)", table);
	if (func == NULL) {
		LOGW("%p (dump hash table: given no handler)", table);
		return;
	}
	/* Print each value in detail */
	hash_table_iterate(table, &itr);
	while (hash_table_iter_has_more(&itr)) {
		(*func)(hash_table_iter_next(&itr));
	}
}
