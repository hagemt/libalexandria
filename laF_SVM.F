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

C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KERN_HPOLY(V1,V2,D,K)
C     -------------------------------------------------------------------------
      CALL LAF_KERN_POLY(V1,V2,0,D,K)
C     -------------------------------------------------------------------------
      END

C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KERN_POLY(V1,V2,C,D,K)
C     -------------------------------------------------------------------------
      REAL, DIMENSION(3) :: V1,V2
      REAL :: D,K
C     Inhomogenous kernel is V1 dot V2 + C raised to the D power
      K=(DOT_PRODUCT(V1,V2)+C)**D
C     -------------------------------------------------------------------------
      END

C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KERN_GAUSS_SIG(V1,V2,SIG,K)
C     -------------------------------------------------------------------------
      CALL LAF_KERN_GAUSS(V1,V2,1/(2*SIG**2),K)
C     -------------------------------------------------------------------------
      END

C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KERN_GAUSS(V1,V2,GAM,K)
C     -------------------------------------------------------------------------
      REAL, DIMENSION(3) :: V1,V2,V
      REAL :: GAM,K
      V=V1-V2
C     Gaussian kernel creates infinite-dimensional Hilbert space, GAM > 0
      K=EXP(-GAM*SQRT(DOT_PRODUCT(V,V))**2)
C     -------------------------------------------------------------------------
      END

C     -------------------------------------------------------------------------
      SUBROUTINE LAF_KERN_TANH(V1,V2,KAP,C,K)
C     -------------------------------------------------------------------------
      REAL, DIMENSION(3) :: V1,V2
      REAL :: KAP,C,K
C     Hyperbolic tangent kernel, KAP > 0 and C < 0
      K=TANH(KAP*DOT_PRODUCT(V1,V2)+C);
C     -------------------------------------------------------------------------
      END
