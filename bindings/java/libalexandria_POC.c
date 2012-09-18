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
	// size_t len = strnlen(cstr, LAF_MAX_LEN);
	// laf_print_(ctr, len);
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
