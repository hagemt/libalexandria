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

#define LA_JNI
#include "libalexandria.h"

inline la_UUID_t *
extract_data(jlong arg)
{
	la_UUID_t *key = malloc(sizeof(la_UUID_t));
	memcpy(key, &arg, sizeof(la_UUID_t));
	return key;
}

/*
 * Class:     lib_alexandria_pipeline_Aqueduct
 * Method:    alloc
 * Signature: (J)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_lib_alexandria_pipeline_Aqueduct_alloc
	(JNIEnv *env, jclass cls, jlong arg)
{
	struct la_buffer_table_value_t *value;
	if (!la_buffer_table) {
		LOGE("%s (call missing)", "la_initialize(jlong)");
		return NULL;
	}

	la_UUID_t *key = extract_data(arg);
	LOGD("%p (requested native array w/ key: %llu)", (void *)(cls), (long long unsigned)(*key));
	HashTableValue v = hash_table_lookup(la_buffer_table, key);
	if (v != HASH_TABLE_NULL) {
		free(key); /* you won't be needing this anymore */
		value = (struct la_buffer_table_value_t *)(v);
		LOGD("%p (using existing native array: %p)", (void *)(cls), value->buffer);
	} else {
		/* Create a new entry only if necessary */
		value = malloc(sizeof(struct la_buffer_table_value_t));
		// FIXME is this the right size for our needs?
		value->buffer = malloc(LAF_MAX_LEN * sizeof(jdouble));
		memset(value->buffer, 0, LAF_MAX_LEN * sizeof(jdouble));
		// FIXME is there really no corresponding free for this?
		value->handle = (*env)->NewDirectByteBuffer(env, value->buffer, LAF_MAX_LEN * sizeof(jdouble));
		hash_table_insert(la_buffer_table, key, value);
		LOGD("%p (!!! allocated new native array: %p)", (void *)(cls), value->buffer);
	}

	assert(value && value->handle);
	LOGD("%p (returning native array w/ handle: %p)", (void *)(cls), value->handle);
	return value->handle;
}

/*
 * Class:     lib_alexandria_pipeline_Aqueduct
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_lib_alexandria_pipeline_Aqueduct_free
	(JNIEnv *env, jclass cls, jlong arg)
{
	if (!la_buffer_table) {
		LOGE("%s (call missing)", "la_initialize(jlong)");
		return;
	}

	la_UUID_t *key = extract_data(arg);
	int result = hash_table_remove(la_buffer_table, key);
	free(key);

	/* FIXME need to free/monitor direct buffer somehow to alert laJ? */
	LOGD("%p (!!! result of removal: %i)", (void *)(cls), result);
	assert(result == 0);
}
