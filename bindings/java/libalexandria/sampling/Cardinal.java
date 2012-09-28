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
package libalexandria.sampling;

public final class Cardinal extends Ordinal<Integer> {
	public static final Cardinal ALEPH_NULL, ALEPH_ONE, ALEPH_TWO;
	static {
		ALEPH_NULL = new Cardinal(0, false);
		ALEPH_ONE = new Cardinal(1, false);
		ALEPH_TWO = new Cardinal(2, false);
	}
	
	private boolean finite;

	public Cardinal(Integer value) {
		this(value, true);
	}
	
	public Cardinal(Integer value, boolean finite) {
		super(value);
		this.finite = finite;
	}

	@Override
	public boolean isCountable() {
		return finite || (value == 0);
	}

	@Override
	public boolean isFinite() {
		return finite;
	}

	@Override
	public Integer value() {
		return (finite) ? value : Integer.MAX_VALUE;
	}

	@Override
	public int compareTo(Integer o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
