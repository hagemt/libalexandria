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

#ifndef LAF_SVM_H
#define LAF_SVM_H

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

/* kernel functions for non-linear SVM */
extern void laf_kern_gauss_();
extern void laf_kern_poly_();
extern void laf_kern_tanh_();
/* TODO proper arguments */

#ifdef __cplusplus
} /* extern "C" */
#endif /* __cplusplus */

#endif /* LAF_SVM_H */
