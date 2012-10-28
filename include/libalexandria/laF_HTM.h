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

#ifndef LAF_HTM_H
#define LAF_HTM_H

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

/**
 * Function prototypes for HIERARCHICAL TEMPORAL MEMORY
 * ala Numenta Cortical Learning Algorithms
 * (online high-performance ANN for generic inputs)
 */

extern void laf_htm_codec_encode_();
extern void laf_htm_codec_decode_();

extern void laf_htm_space_phase1_();
extern void laf_htm_space_phase2_();
extern void laf_htm_space_phase3_();

extern void laf_htm_time_phase1_();
extern void laf_htm_time_phase2_();
extern void laf_htm_time_phase3_();

#ifdef __cplusplus
} /* extern "C" */
#endif /* __cplusplus */

#endif /* LAF_HTM_H */
