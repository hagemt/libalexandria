#include <string.h>

#include "laC_print.h"

void
println(const char *msg) {
	laf_print_(msg, strnlen(msg, LAF_MAX_LEN));
}
