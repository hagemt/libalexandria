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

#include "libalexandria.h"

HashTable *laf_buffer_table = NULL;

void
laf_free_buffer_entry(HashTableValue v) {
	struct entry_t *entry = (struct entry_t *)(v);
	free(entry->buffer);
	#ifndef NDEBUG
	fprintf(stderr, "[debug] native array %p was released\n", entry->buffer);
	#endif
	entry->buffer = entry->handle = NULL;
	free(entry);
}

void
laf_initialize(uint64_t seed)
{
	assert(!laf_buffer_table);
	laf_buffer_table = hash_table_new(&pointer_hash, &pointer_equal);
	assert(laf_buffer_table);
	hash_table_register_free_functions(laf_buffer_table, NULL, &laf_free_buffer_entry);
	#ifndef NDEBUG
	fprintf(stderr, "[debug %llu] successfully created (buffer table)\n", seed);
	#endif
}

void
laf_finalize(uint64_t seed)
{
	// TODO check seed, too
	#ifndef NDEBUG
	HashTableIterator itr;
	struct entry_t *entry;
	#endif
	int num_entries = 0;
	if (laf_buffer_table) {
		num_entries = hash_table_num_entries(laf_buffer_table);
		#ifndef NDEBUG
		hash_table_iterate(laf_buffer_table, &itr);
		while (hash_table_iter_has_more(&itr)) {
			entry = (struct entry_t *)(hash_table_iter_next(&itr));
			fprintf(stderr, "[debug] entry { %p, %p } (buffer table)\n", entry->buffer, entry->handle);
		}
		#endif
		hash_table_free(laf_buffer_table);
		laf_buffer_table = NULL;
	}
	#ifndef NDEBUG
	fprintf(stderr, "[debug %llu] %i entries removed (buffer table)\n", seed, num_entries);
	#endif
}

void
laf_print_info_incomplete(const char *feature)
{
	fprintf(stderr, LAF_INFO_INCOMPLETE, feature);
}
