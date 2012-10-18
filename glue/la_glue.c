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

#include "la_glue.h"

/* FIXME can this have static linkage? */
HashTable *la_buffer_table = NULL;

void
la_free_buffer_table_key(HashTableKey k)
{
	// TODO useless method stub?
	free(k);
}

void
la_free_buffer_table_value(HashTableValue v)
{
	struct la_buffer_table_value_t *value = (struct la_buffer_table_value_t *)(v);
	free(value->buffer);
	#ifndef NDEBUG
	fprintf(stderr, "[debug] native array %p was released\n", value->buffer);
	#endif
	value->buffer = value->handle = NULL;
	free(value);
}

void
la_initialize(la_UUID_t seed)
{
	assert(!la_buffer_table);
	la_buffer_table = hash_table_new(&pointer_hash, &pointer_equal);
	assert(la_buffer_table);
	/* TODO determine typeof(jobject) and see if we need to cache it? */
	hash_table_register_free_functions(la_buffer_table, &la_free_buffer_table_key, &la_free_buffer_table_value);
	#ifndef NDEBUG
	fprintf(stderr, "[debug %llu] successfully created (buffer table)\n", seed);
	#endif
}

void
la_finalize(la_UUID_t seed)
{
	// TODO check seed, too
	#ifndef NDEBUG
	HashTableIterator itr;
	struct la_buffer_table_value_t *value;
	#endif
	int num_entries = 0;
	if (la_buffer_table) {
		num_entries = hash_table_num_entries(la_buffer_table);
		#ifndef NDEBUG
		hash_table_iterate(la_buffer_table, &itr);
		while (hash_table_iter_has_more(&itr)) {
			value = (struct la_buffer_table_value_t *)(hash_table_iter_next(&itr));
			fprintf(stderr, "[debug] value { %p, %p } (buffer table)\n", value->buffer, value->handle);
		}
		#endif
		hash_table_free(la_buffer_table);
		la_buffer_table = NULL;
	}
	#ifndef NDEBUG
	fprintf(stderr, "[debug %llu] %i entries removed (buffer table)\n", seed, num_entries);
	#endif
}

void
la_print_info_incomplete(const char *feature)
{
	fprintf(stderr, LA_INFO_INCOMPLETE, feature);
}
