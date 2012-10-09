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
#include "libalexandria_functional_RealParameterizedFunction.h"

extern HashTable *laf_buffer_table;

/*
 * Class:     libalexandria_functional_RealParameterizedFunction
 * Method:    alloc
 * Signature: ()Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL
Java_libalexandria_functional_RealParameterizedFunction_alloc
	(JNIEnv *env, jobject obj)
{
	laf_buffer_entry_t *entry;
	if (!laf_buffer_table) {
		fprintf(stderr, "[error] libalexandria needs call to initialize(jlong)\n");
		return NULL;
	}
	#ifndef NDEBUG
	fprintf(stderr, "[debug] requesting native array for %p\n", obj);
	#endif
	HashTableValue value = hash_table_lookup(laf_buffer_table, obj);
	if (value != HASH_TABLE_NULL) {
		entry = (laf_buffer_entry_t *)(value);
		#ifndef NDEBUG
		fprintf(stderr, "[debug] found previous native array for %p at %p\n", obj, entry->buffer);
		#endif
	} else {
		/* Create a new entry only if necessary */
		entry = malloc(sizeof(laf_buffer_entry_t));
		entry->buffer = malloc(LAF_MAX_LEN * sizeof(jdouble));
		entry->handle = (*env)->NewDirectByteBuffer(env, entry->buffer, LAF_MAX_LEN * sizeof(jdouble));
		#ifndef NDEBUG
		fprintf(stderr, "[debug] created new native array for %p at %p\n", obj, entry->buffer);
		#endif
		hash_table_insert(laf_buffer_table, obj, entry);
	}
	assert(entry && entry->handle);
	#ifndef NDEBUG
	fprintf(stderr, "[debug] returning native array for %p in object %p\n", obj, entry->handle);
	#endif
	return entry->handle;
}

/*
 * Class:     libalexandria_functional_RealParameterizedFunction
 * Method:    free
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_libalexandria_functional_RealParameterizedFunction_free
	(JNIEnv *env, jobject obj)
{
	if (!laf_buffer_table) {
		fprintf(stderr, "[error] libalexandria needs call to initialize(jlong)\n");
	}
	#ifndef NDEBUG
	fprintf(stderr, "[debug] can we remove %p? answer: ", obj);
	#endif
	int result = hash_table_remove(laf_buffer_table, obj);
	#ifndef NDEBUG
	fprintf(stderr, "%i\n", result);
	#endif
}
