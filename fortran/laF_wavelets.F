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

C     TODO implement Haar wavelet (standard definition)
C     -------------------------------------------------------------------------
      SUBROUTINE LAF_WHAAR(T,FT)
      REAL, DIMENSION(LA_SIZEMAX) :: T, FT
C     -------------------------------------------------------------------------
      FT=T
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_WHAAR
C     -------------------------------------------------------------------------

C     TODO implement Meyer wavelet (standard definition)
C     -------------------------------------------------------------------------
      SUBROUTINE LAF_WMEYER(T,FT)
      REAL, DIMENSION(LA_SIZEMAX) :: T, FT
C     -------------------------------------------------------------------------
      FT=T
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_WMEYER
C     -------------------------------------------------------------------------

C     TODO implement Morlet wavelet (standard definition)
C     -------------------------------------------------------------------------
      SUBROUTINE LAF_WMORLET(T,FT)
      REAL, DIMENSION(LA_SIZEMAX) :: T, FT
C     -------------------------------------------------------------------------
      FT=T
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_WMORLET
C     -------------------------------------------------------------------------

C     TODO implement Ricker wavelet (standard definition)
C     -------------------------------------------------------------------------
      SUBROUTINE LAF_WRICKER(T,FT)
      REAL, DIMENSION(LA_SIZEMAX) :: T, FT
C     -------------------------------------------------------------------------
      FT=T
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_WRICKER
C     -------------------------------------------------------------------------
