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
package libalexandria.proof;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class POC {
	static {
		System.loadLibrary("alexandria");
	}

	/* Symbol for FORTRAN function */
	public static native void print(String s);

	public static void main(String... args) {
		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while ((line = reader.readLine()) != null) {
					POC.print(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
