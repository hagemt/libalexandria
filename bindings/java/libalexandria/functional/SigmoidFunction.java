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
package libalexandria.functional;

import libalexandria.ModelConstants;

/**
 * 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class SigmoidFunction extends RealParameterizedFunction<Double> implements ModelConstants.For<SigmoidType> {
	private SigmoidType type;
	
	protected SigmoidFunction(String label, SigmoidType type) {
		super(label);
		this.type = type;
	}

	@Override
	public SigmoidType getType() {
		return type;
	}

	@Override
	public int getArity() {
		return 1;
	}

	@Override
	protected native void sync();

	@Override
	public native void benchmark();
}
