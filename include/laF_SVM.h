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

#include "laF_constants.h"

/* kernel functions for non-linear SVM */
void laf_kern_gauss_;
void laf_kern_gauss_sig_;
void laf_kern_hpoly_;
void laf_kern_poly_;
void laf_kern_tanh_;

#endif /* LAF_SVM_H */
