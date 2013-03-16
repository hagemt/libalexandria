C     -------------------------------------------------------------------------
C     This file is part of libalexandria.
C
C     libalexandria is free software: you can redistribute it and/or modify
C     it under the terms of the GNU Lesser General Public License as published by
C     the Free Software Foundation, either version 3 of the License, or
C     (at your option) any later version.
C
C     libalexandria is distributed in the hope that it will be useful,
C     but WITHOUT ANY WARRANTY; without even the implied warranty of
C     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
C     GNU Lesser General Public License for more details.
C
C     You should have received a copy of the GNU Lesser General Public License
C     along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.
C     -------------------------------------------------------------------------
#include "la_constants.h"

C     Inhomogenous kernel is V1 dot V2 plus C raised to the D power
C     -------------------------------------------------------------------------
      REAL FUNCTION LAF_KPOLY(V1, V2, C, D, I)
C      USE ISO_C_BINDING
      REAL, DIMENSION(LA_SIZEMAX) :: V1, V2
      REAL :: C, D
      INTEGER :: I
C     -------------------------------------------------------------------------
      REAL :: BASE
      BASE = DOT_PRODUCT(V1, V2) + C
C     -------------------------------------------------------------------------
      IF (I .EQ. 0)
        LAF_KPOLY = BASE ** I
      ELSE
        LAF_KPOLY = BASE ** D
      ENDIF
      END FUNCTION LAF_KPOLY
C     -------------------------------------------------------------------------

C     Gaussian kernel creates infinite-dimensional Hilbert space
C     -------------------------------------------------------------------------
      REAL FUNCTION LAF_KGAUSS(V1, V2, GAM, SIG)
C      USE ISO_C_BINDING
      REAL, DIMENSION(LA_SIZEMAX) :: V1, V2
      REAL :: GAM, SIG
C     -------------------------------------------------------------------------
      REAL, DIMENSION(LA_SIZEMAX) :: V
      V = V1 - V2
C     Ensure unitary result for near-identical V1 and V2
      IF (ALL(V .LT. EPSILON(GAM))) THEN
        V = 0
      ENDIF
C     Ensure that GAM is acceptably above zero
      IF (GAM .LT. LA_MACHEPS) THEN
        GAM = 1 / (2 * SIG ** 2 + LA_MACHEPS)
      ENDIF
C     -------------------------------------------------------------------------
      LAF_KGAUSS = EXP(-GAM * DOT_PRODUCT(V, V))
      END FUNCTION LAF_KGAUSS
C     -------------------------------------------------------------------------

C     Hyperbolic tangent kernel (with positive parameters)
C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KTANH(V1, V2, KAP, C, K)
C      USE ISO_C_BINDING
      REAL, DIMENSION(LA_SIZEMAX) :: V1, V2
      REAL :: KAP, C, K
C     -------------------------------------------------------------------------
      K = TANH(KAP * DOT_PRODUCT(V1,V2) - C);
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_KTANH
C     -------------------------------------------------------------------------
