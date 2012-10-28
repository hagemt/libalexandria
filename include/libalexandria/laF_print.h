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
#ifndef LAF_PRINT_H
#define LAF_PRINT_H

/* TODO eventually replace (laF_print is currently just for proof-of-concepts */

#ifdef __cplusplus
extern "C" {
#endif /* __cpluscplus */

void laf_printi_(void);
void laf_print_(char const *, int);

#ifdef __cplusplus
} /* extern "C" */
#endif /* __cpluscplus */

#endif /* LAF_PRINT_H */
