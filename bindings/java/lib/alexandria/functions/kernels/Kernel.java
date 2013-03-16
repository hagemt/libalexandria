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
package lib.alexandria.functions.kernels;

import lib.alexandria.functions.RealParameterizedFunction;

/**
 * Does not encapsulate computation of a kernel function, instead, provides metadata concerning parameters and such.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class Kernel extends RealParameterizedFunction<Double> {
	/**
	 * Describes this kernel's family (category)
	 */
	private final KernelType type;
	
	protected Kernel(KernelType type) {
		this(type.toString(), type);
	}
	
	public Kernel(String label, KernelType type) {
		super(label);
		this.type = type;
	}

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	protected native void sync();

	@Override
	public native void benchmark();

	@Override
	public <E extends Enum<E>> E getType(Class<E> category) throws ClassCastException {
		return category.cast(type);
	}
}