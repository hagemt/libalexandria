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
package libalexandria.base;

public enum Aleph implements Ordinal<Integer> {
	NULL(0), ONE(1);
	
	private int value;
	
	private Aleph(int value) {
		this.value = value;
	}

	@Override
	public boolean isCountable() {
		return (value == 0);
	}

	@Override
	public boolean isFinite() {
		return false;
	}

	@Override
	public Integer value() {
		return value;
	}
}
