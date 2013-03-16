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

C     LA_TEST_KERNELS -- TODO write numerical accuracy tests for LAF_K*
C     -------------------------------------------------------------------------
      MODULE LA_TEST
      IMPLICIT NONE
      CONTAINS
C     -------------------------------------------------------------------------
      REAL FUNCTION CELCIUS(FAHRENHEIT)
      REAL, INTENT(IN) :: FAHRENHEIT
      CELCIUS=(FAHRENHEIT-32.0)/1.8
      END FUNCTION CELCIUS
C     -------------------------------------------------------------------------
      ENDMODULE LA_TEST
C     -------------------------------------------------------------------------

C     LA_TEST_KERNELS -- TODO write numerical accuracy tests for LAF_K*
C     -------------------------------------------------------------------------
      PROGRAM LA_TEST_K
      USE LA_TEST, ONLY: CELCIUS
      INTEGER :: D
      REAL    :: F
C     -------------------------------------------------------------------------
      WRITE (*,*) 2+3,2-3,2/3,4*3,2**3
C     Print some converted temperature values
      WRITE (*,"(2a10)") "degrees_F","degrees_C"
      DO D=-50,100,1
        F = REAL(D)
        WRITE (*,"(2f10.1)") F,CELCIUS(F)
      END DO
C     -------------------------------------------------------------------------
      END PROGRAM LA_TEST_K
C     -------------------------------------------------------------------------
