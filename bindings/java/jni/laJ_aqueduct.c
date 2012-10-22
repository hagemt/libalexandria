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

/*
 * Class:     lib_alexandria_pipeline_Aqueduct
 * Method:    alloc
 * Signature: (J)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_lib_alexandria_pipeline_Aqueduct_alloc
	(JNIEnv *env, jclass cls, jlong key)
{
	struct la_buffer_table_value_t *value;
	if (!la_buffer_table) {
		LOGE("%s (call missing)", "la_initialize(jlong)");
		return NULL;
	}

	LOGD("%p (requested native array: %li)", (void *)(cls), (long)(key));
	HashTableValue v = hash_table_lookup(la_buffer_table, key);
	if (v != HASH_TABLE_NULL) {
		value = (struct la_buffer_table_value_t *)(v);
		LOGD("%p (using existing native array: %p)", (void *)(cls), (void *)(value->buffer));
	} else {
		/* Create a new entry only if necessary */
		value = malloc(sizeof(struct la_buffer_table_value_t));
		value->buffer = malloc(LAF_MAX_LEN * sizeof(jdouble));
		memset(value->buffer, 0, LAF_MAX_LEN * sizeof(jdouble));
		value->handle = (*env)->NewDirectByteBuffer(env, value->buffer, LAF_MAX_LEN * sizeof(jdouble));
		hash_table_insert(la_buffer_table, cls, value);
		LOGD("%p (!!! allocated new native array: %p)", (void *)(cls), (void *)(value->buffer));
	}
	// FIXME using jlong like key pointer?

	assert(value && value->handle);
	LOGD("%p (returning native array: %p)", (void *)(cls), (void *)(value->handle));
	return value->handle;
}

/*
 * Class:     lib_alexandria_pipeline_Aqueduct
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_lib_alexandria_pipeline_Aqueduct_free
	(JNIEnv *env, jclass cls, jlong key)
{
	if (!la_buffer_table) {
		LOGE("%s (call missing)", "la_initialize(jlong)");
		return;
	}

	int result = hash_table_remove(la_buffer_table, key);
	/* FIXME need to free/monitor direct buffer somehow to alert laJ? */
	LOGD("%p (!!! result of removal: %i)", (void *)(cls), result);
	assert(result == 0);
}
