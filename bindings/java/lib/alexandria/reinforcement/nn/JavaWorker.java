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
package lib.alexandria.reinforcement.nn;

public class JavaWorker extends Worker {
	public JavaWorker(Cortex cortex) {
		super(cortex);
	}

	@Override
	public void operate(byte[] a, byte[] b) {
		assert(a.length == b.length) : "lengths do not match";
		for (int i = 0; i < a.length; ++i) {
			a[i] ^= b[i];
		}
	}
}
