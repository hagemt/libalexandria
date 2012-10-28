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

/*
 * Class:     lib_alexandria_functional_kernels_Kernel
 * Method:    benchmark
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_lib_alexandria_functional_kernels_Kernel_benchmark
	(JNIEnv *env, jobject obj)
{
	/* TODO implementation
	 * Notes: We're going to pollute the namespace and add
	 * needless abstraction with a native function for each
	 * FORTRAN procedure. Can we coallese some stuff:
	 * A single kernel function, takes enum id for flavor?
	*/
	la_mark_incomplete("Kernel_benchmark");
	return;
}

/*
 * Class:     lib_alexandria_functional_kernels_Kernel
 * Method:    sync
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_lib_alexandria_functional_kernels_Kernel_sync
	(JNIEnv *env, jobject obj)
{
	la_mark_incomplete("Kernel_sync");
	return;
}
