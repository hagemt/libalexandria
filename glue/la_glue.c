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

/* Note: this cannot be static, for external linkage */
HashTable *la_buffer_table = NULL;

void
la_initialize(la_UUID_t seed)
{
	assert(!la_buffer_table);
	la_buffer_table = hash_table_new(
		&__la_hash_buffer_table_key,
		&__la_compare_buffer_table_value);
	assert(la_buffer_table);
	hash_table_register_free_functions(
		la_buffer_table,
		&__la_free_buffer_table_key,
		&__la_free_buffer_table_value);
	LOGD("%lu (successfully created buffer table)", seed);
}

void
la_finalize(la_UUID_t seed)
{
	// TODO check seed, maybe dump buffer table too
	int num_entries = 0;
	if (la_buffer_table) {
		#ifndef NDEBUG
		__la_dump_table(la_buffer_table, &__la_dump_value);
		#endif
		num_entries = hash_table_num_entries(la_buffer_table);
		hash_table_free(la_buffer_table);
		la_buffer_table = NULL;
	}
	LOGD("%llu (entries removed from buffer table: %i)", seed, num_entries);
}

void
la_mark_incomplete(const char *feature)
{
	LOGI("'%s' (feature not yet implemented)", feature);
}

