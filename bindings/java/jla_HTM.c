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

#include "laF_HTM.h"
#include "libalexandria_ann_NativeWorker.h"

#include <assert.h>

/*
 * Class:     libalexandria_ann_Worker
 * Method:    operate
 * Signature: ([B[B)V
 */
JNIEXPORT void JNICALL
Java_libalexandria_ann_Worker_operate(JNIEnv *env, jobject obj, jbyteArray arr1, jbyteArray arr2)
{
	jint i, len1, len2;
	jbyte *a1, *a2;
	/* Fetch the arrays from java */
	len1 = (*env)->GetArrayLength(env, arr1);
	len2 = (*env)->GetArrayLength(env, arr2);
	assert(len1 == len2);
	a1 = (*env)->GetPrimitiveArrayCritical(env, arr1, 0);
	a2 = (*env)->GetPrimitiveArrayCritical(env, arr2, 0);
	assert(a1 && a2);
	/* Do the operation */
	for (i = 0; i < len1; ++i) {
		a1[i] ^= a2[i];
	}
	/* Cleanup */
	(*env)->ReleasePrimitiveArrayCritical(env, arr2, a2, 0);
	(*env)->ReleasePrimitiveArrayCritical(env, arr1, a1, 0);
	return;
}
