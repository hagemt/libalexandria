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
#include "libalexandria_functional_RealParameterizedFunction.h"

static jdouble funky_buffer[LAF_MAX_LEN];

/*
 * Class:     libalexandria_functional_RealParameterizedFunction
 * Method:    alloc
 * Signature: ()Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL
Java_libalexandria_functional_RealParameterizedFunction_alloc
	(JNIEnv *env, jobject obj)
{
	obj = (*env)->NewDirectByteBuffer(env, funky_buffer, LAF_MAX_LEN * sizeof(jdouble));
	assert(obj);
	return obj;
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
	laf_print_info_incomplete("free");
}
