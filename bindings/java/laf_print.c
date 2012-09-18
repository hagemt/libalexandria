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
#include <string.h>

/* Fortran headers */
#include "laf_print.h"

/* Java natives, proof-of-concept */
#include "libalexandria_POC.h"
/* XXX is this ^ name system dependent? */

JNIEXPORT void
JNICALL Java_libalexandria_POC_print(JNIEnv *env, jclass jc, jstring jstr)
{
	size_t len;
	const char *cstr;

	/* Fetch modified UTF-8 characters */
	cstr = (*env)->GetStringUTFChars(env, jstr, NULL);

	#ifndef NDEBUG
	fprintf(stderr, "[DEBUG] received '%s' from JNI:\n", cstr);
	#endif
	laf_print_(cstr, strnlen(cstr, LAF_MAX_LEN));

	/* Remember to release memory */
	(*env)->ReleaseStringUTFChars(env, jstr, cstr);

	#ifndef NDEBUG
	fprintf(stderr, "[DEBUG] Result of '%p' printing 'i':\n", jc);
	#endif
	laf_printi_();
}

#define LAF_TEST_STR "Hello, World!"

int
main(void)
{
	/* Initial test for simple proof-of-concept */
	laf_print_(LAF_TEST_STR, sizeof(LAF_TEST_STR));
	return (EXIT_SUCCESS);
}
