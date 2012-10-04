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
#include "libalexandria_functional_wavelets_Wavelet.h"

/*
 * Class:     libalexandria_functional_wavelets_Wavelet
 * Method:    benchmark
 * Signature: ()V
 */
JNIEXPORT void JNICALL
Java_libalexandria_functional_wavelets_Wavelet_benchmark(JNIEnv *env, jobject obj)
{
	laf_print_info_incomplete("Wavelet_benchmark");
} 

/*
 * Class:     libalexandria_functional_wavelets_Wavelet
 * Method:    benchmark
 * Signature: ()V
 */
JNIEXPORT jobject JNICALL
Java_libalexandria_functional_wavelets_Wavelet_call(JNIEnv *env, jobject obj)
{
	laf_print_info_incomplete("Wavelet_call");
	return obj;
}

