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
 * Class:     lib_alexandria_proof_POC
 * Method:    initialize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_lib_alexandria_proof_POC_initialize
	(JNIEnv *env, jclass jc, jlong seed)
{
	LOGD("%p (initializing libalexandria through JNI)", (void *)(jc));
	la_initialize(seed);
}

/*
 * Class:     lib_alexandria_proof_POC
 * Method:    finalize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL
Java_lib_alexandria_proof_POC_finalize
	(JNIEnv *env, jclass jc, jlong seed)
{
	LOGD("%p (finalizing libalexandria through JNI)", (void *)(jc));
	la_finalize(seed);
}

/*
 * Class:     lib_alexandria_proof_POC
 * Method:    println
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL
Java_lib_alexandria_proof_POC_println
	(JNIEnv *env, jclass jc, jstring jstr)
{
	/* Fetch/release modified UTF-8 characters */
	const char *cstr = (*env)->GetStringUTFChars(env, jstr, NULL);
	LOGD("%p (class received '%s' through JNI)", (void *)(jc), cstr);
	laf_print_(cstr, strnlen(cstr, LAF_MAX_LEN));
	(*env)->ReleaseStringUTFChars(env, jstr, cstr);

	/* Most basic command possible */
	LOGD("%p (class printing printing 'i')", (void *)(jc));
	laf_printi_();
}
