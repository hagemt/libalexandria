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
package libalexandria.functional.wavelets;

import java.io.DataInput;

import libalexandria.ModelConstants;

import libalexandria.functional.RealParameterizedFunction;

/**
 * Describes an orthogonal function (group)
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class Wavelet extends RealParameterizedFunction<Double> implements ModelConstants.For<WaveletType> {
	private final WaveletType type;
	
	protected Wavelet(WaveletType type) {
		this(type.toString(), type);
	}

	public Wavelet(String label, WaveletType type) {
		super(label);
		this.type = type;
	}

	@Override
	public WaveletType getType() {
		return type;
	}

	@Override
	public int getArity() {
		return 1;
	}

	@Override
	protected native void sync(DataInput stream);

	@Override
	public native void benchmark();
}
