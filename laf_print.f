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
#include "laf_constants.h"
#define COMPLEX_UNIT CMPLX(0,1)

C     -------------------------------------------------------------------------
      SUBROUTINE laf_printi
C     -------------------------------------------------------------------------
      print *,COMPLEX_UNIT
C     -------------------------------------------------------------------------
      END

C     -------------------------------------------------------------------------
      SUBROUTINE laf_print(STR)
C     -------------------------------------------------------------------------
      CHARACTER*LAF_MAX_LEN STR
      print *,'This string: "',STR,'" has length ',LEN(STR),'.'
C     -------------------------------------------------------------------------
      END
