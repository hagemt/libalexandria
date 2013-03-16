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
package lib.alexandria.data.sampling;

import lib.alexandria.data.Ordinal;

/**
 * TODO maybe this was just a bad idea?
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Cardinal extends Number implements Ordinal {
	private static final long serialVersionUID = -5795733060070247157L;
	public static final Cardinal ALEPH_NULL, ALEPH_ONE;
	static {
		ALEPH_NULL = new Cardinal(0, true);
		ALEPH_ONE = new Cardinal(1, true);
	}
	
	private final int value;
	private final boolean aleph;

	public Cardinal(int value) {
		this(value, false);
	}
	
	protected Cardinal(int value, boolean aleph) {
		this.value = value;
		this.aleph = aleph;
	}

	@Override
	public boolean isCountable() {
		return aleph ? (value == 0) : true;
	}

	@Override
	public boolean isFinite() {
		return !aleph;
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public long longValue() {
		return new Long(value);
	}

	@Override
	public float floatValue() {
		return new Float(value);
	}

	@Override
	public double doubleValue() {
		return new Double(value);
	}
}
