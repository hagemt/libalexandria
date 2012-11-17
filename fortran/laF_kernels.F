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

C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KPOLY(V1,V2,C,D,K)
C     -------------------------------------------------------------------------
      REAL :: C,D,K
      REAL, DIMENSION(LA_MAX_LEN) :: V1,V2
C     Inhomogenous kernel is V1 dot V2 + C raised to the D power
      K=(DOT_PRODUCT(V1,V2)+C)**D
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_KPOLY
C     -------------------------------------------------------------------------

C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KGAUSS(V1,V2,GAM,SIG,K)
C     -------------------------------------------------------------------------
      REAL :: GAM,SIG,K
      REAL, DIMENSION(LA_MAX_LEN) :: V1,V2,V
      V=V1-V2
      IF (GAM .EQ. 0 .AND. SIG .NE. 0) GAM=1/(2*SIG**2)
C     Gaussian kernel creates infinite-dimensional Hilbert space
      K=EXP(-GAM*SQRT(DOT_PRODUCT(V,V))**2)
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_KGAUSS
C     -------------------------------------------------------------------------

C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KTANH(V1,V2,KAP,C,K)
C     -------------------------------------------------------------------------
      REAL :: KAP,C,K
      REAL, DIMENSION(LA_MAX_LEN) :: V1,V2
C     Hyperbolic tangent kernel with positive parameters
      K=TANH(KAP*DOT_PRODUCT(V1,V2)-C);
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_KTANH
C     -------------------------------------------------------------------------
