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

#ifndef LA_HELPER_H
#define LA_HELPER_H

#include "la_types.h"

/* callbacks for calg's hashtable implementation */
unsigned long __la_hash_buffer_table_key(HashTableKey);
int __la_compare_buffer_table_keys(HashTableKey, HashTableKey);
void __la_free_buffer_table_key(HashTableKey);
void __la_free_buffer_table_value(HashTableValue);

/* hashtable utilities (mostly for debugging) */
typedef void (*__value_handler_t)(HashTableValue);
void __la_dump_table(HashTable *, __value_handler_t);
void __la_dump_value(HashTableValue);

#endif /* LA_HELPER_H */
