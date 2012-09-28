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

#include "libalexandria_ann_NativeWorker.h"

/*
 * Class:     libalexandria_ann_NativeWorker
 * Method:    operate
 * Signature: ([B[B)V
 */
JNIEXPORT void JNICALL
Java_libalexandria_ann_NativeWorker_operate(JNIEnv *, jobject, jbyteArray, jbyteArray)
{
	return;
}

/*
 * Class:     libalexandria_ann_NativeWorker
 * Method:    wind
 * Signature: ([B[[B)V
 */
JNIEXPORT void JNICALL
Java_libalexandria_ann_NativeWorker_wind(JNIEnv *, jobject, jbyteArray, jobjectArray)
{
	return;
}

/*
 * Class:     libalexandria_ann_NativeWorker
 * Method:    unwind
 * Signature: ([[B[B)V
 */
JNIEXPORT void JNICALL
Java_libalexandria_ann_NativeWorker_unwind(JNIEnv *, jobject, jobjectArray, jbyteArray)
{
	return;
}
