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

/* Note: this cannot be static, external linkage */
HashTable *la_buffer_table = NULL;

void
la_free_buffer_table_key(HashTableKey k)
{
	// FIXME key should correspond with class instances
	LOGD("%p (releasing buffer table key)", (void *)(k));
}

void
la_free_buffer_table_value(HashTableValue v)
{
	struct la_buffer_table_value_t *value = (struct la_buffer_table_value_t *)(v);
	LOGD("%p (releasing native array: %p)", (void *)(v), (void *)(value->buffer));
	free(value->buffer);
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
	LOGD("%llu (successfully created buffer table)", (long long unsigned)(seed));
}

void
__la_dump_value(HashTableValue v)
{
	struct la_buffer_table_value_t *value = (struct la_buffer_table_value_t *)(v);
	LOGV("{ %p, %p } (buffer table value)", (void *)(value->buffer), (void *)(value->handle));
}

typedef void (*la_value_handler_t)(HashTableValue);

void
__la_dump_table(HashTable *table, la_value_handler_t func)
{
	HashTableIterator itr;
	if (func == NULL) {
		LOGE("%p (table given invalid printer)", (void *)(table));
		return;
	}
	/* Print each value in detail */
	hash_table_iterate(table, &itr);
	while (hash_table_iter_has_more(&itr)) {
		(*func)(hash_table_iter_next(&itr));
	}
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
la_print_info_incomplete(const char *feature)
{
	LOGI("'%s' (feature not yet implemented)", feature);
}

void
__la_log_print(int code, const char *tag, const char *fmt, ...)
{
	const char *id;
	va_list args;
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
	fprintf(stderr, "\n");
	/* TODO needs more sophisticated method? */
}
