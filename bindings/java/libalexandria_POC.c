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
#include <stdio.h>
#include <stdlib.h>

/* Fortran headers */
#include "libalexandria_FTH.h"

/* Java natives, proof of concept */
#include "libalexandria_POC.h"

JNIEXPORT void
JNICALL Java_libalexandria_POC_print(JNIEnv *env, jclass jc, jstring jstr)
{
	/* Fetch modified UTF-8 characters */
	const char *cstr = (*env)->GetStringUTFChars(env, jstr, NULL);
	#ifndef NDEBUG
	fprintf(stderr, "[DEBUG] from JNI: '%s'\n", cstr);
	#endif
	laf_printi_();
	/* TODO get to work with passed string */
	//size_t len = strnlen(cstr, LAF_MAX_LEN);
	//laf_print_(ctr, len);
	/* Remember to release memory */
	(*env)->ReleaseStringUTFChars(env, jstr, cstr);
	fprintf(stderr, "CLASS: %p\n", jc);
}

#define LAF_TEST_STR "Hello, World!"

int
main(void)
{
	laf_printi_();
	laf_print_(LAF_TEST_STR, sizeof(LAF_TEST_STR));
	return (EXIT_SUCCESS);
}
