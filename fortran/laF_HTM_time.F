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

C     Phase 1: TODO compute activeState(t) for each cell
C     -------------------------------------------------------------------------
      SUBROUTINE LAF_HTM_TIME_PHASE1(I,O)
      REAL, DIMENSION(LA_SIZEMAX) :: I,O
C     -------------------------------------------------------------------------
      O=I
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_HTM_TIME_PHASE1
C     -------------------------------------------------------------------------

C     Phase 2: TODO compute predictiveState(t) for each cell
C     -------------------------------------------------------------------------
      SUBROUTINE LAF_HTM_TIME_PHASE2(I,O)
      REAL, DIMENSION(LA_SIZEMAX) :: I,O
C     -------------------------------------------------------------------------
      O=I
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_HTM_TIME_PHASE2
C     -------------------------------------------------------------------------

C     Phase 3: TODO update synapses
C     -------------------------------------------------------------------------
      SUBROUTINE LAF_HTM_TIME_PHASE3(I,O)
      REAL, DIMENSION(LA_SIZEMAX) :: I,O
C     -------------------------------------------------------------------------
      O=I
C     -------------------------------------------------------------------------
      END SUBROUTINE LAF_HTM_TIME_PHASE3
C     -------------------------------------------------------------------------
