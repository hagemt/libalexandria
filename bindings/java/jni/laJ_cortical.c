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

#include "libalexandria.h"

/*
 * Class:     lib_alexandria_reinforcement_nn_NativeWorker
 * Method:    operate
 * Signature: ([B[B)V
 */
JNIEXPORT void JNICALL
Java_lib_alexandria_reinforcement_nn_NativeWorker_operate
	(JNIEnv *env, jobject obj, jbyteArray arr1, jbyteArray arr2)
{
	register jint i;
	jint len1, len2;
	jbyte *a1, *a2;
	la_mark_incomplete("NativeWorker_operate");
	/* Fetch the arrays from java */
	len1 = (*env)->GetArrayLength(env, arr1);
	len2 = (*env)->GetArrayLength(env, arr2);
	assert(len1 == len2);
	a1 = (*env)->GetPrimitiveArrayCritical(env, arr1, 0);
	a2 = (*env)->GetPrimitiveArrayCritical(env, arr2, 0);
	assert(a1 && a2);
	/* Do an operation */
	for (i = 0; i < len1; ++i) {
		a1[i] ^= a2[i];
	}
	/* Release all resources */
	(*env)->ReleasePrimitiveArrayCritical(env, arr2, a2, 0);
	(*env)->ReleasePrimitiveArrayCritical(env, arr1, a1, 0);
}
