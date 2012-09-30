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

import libalexandria.functional.ParameterizedFunction;

/**
 * Describes an orthogonal function (group)
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class Wavelet extends ParameterizedFunction<Double> {
	/**
	 * Indicates whether or not a wavelet's form is continuous
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 * @since libalexandria v0.1
	 */
	public static enum WaveletType {
		CONTINUOUS, DISCRETE;
	}
	
	private WaveletType type;

	public Wavelet(String label, WaveletType type) {
		super(label);
		this.type = type;
	}
	
	@Override
	public WaveletType getType() {
		return type;
	}

	@Override
	public int arity() {
		return 1;
	}

	@Override
	public Class<Double> returnType() {
		return Double.class;
	}

	@Override
	public native void benchmark();
}
